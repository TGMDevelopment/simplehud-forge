package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ElementReach extends Element {

    private Double reach = null;
    private long lastHit = 0L;

    public ElementReach() {
        super("Reach");
        this.height = 10;
        this.elementScreen = new ElementGUI(this);
        if (this.prefix == null) prefix = "Reach";
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (event.entity instanceof EntityPlayerSP) {
            reach = getReachDistanceFromEntity(event.target);
            lastHit = System.currentTimeMillis();
        }
        mc.thePlayer.swingItem();
    }

    @Override
    public String getRenderedString() {
        StringBuilder sb = new StringBuilder();
        if (shouldRenderBrackets()) sb.append("[");
        if (this.prefix != null)
            sb.append(this.prefix).append(": ");
        sb.append(getRenderedValue());
        if (shouldRenderBrackets()) sb.append("]");
        return sb.toString();
    }

    @Override
    public void onRendered() {
        if (System.currentTimeMillis() - lastHit > 3000)
            reach = null;
        this.setRenderedValue(reach == null ? "~" : getReachFormatted());
        String text = this.getRenderedString();
        this.mc.fontRendererObj.drawString(text, this.getPosition().getX(), this.getPosition().getY(), this.colour.getHex());
        this.width = this.mc.fontRendererObj.getStringWidth(text);
        super.onRendered();
    }

    private String getReachFormatted() {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(reach);
    }

    private double getReachDistanceFromEntity(Entity entity) {
        mc.mcProfiler.startSection("Calculate Reach Dist");

        // How far will ray travel before ending
        double maxSize = 20D;
        // Bounding box of entity
        AxisAlignedBB otherBB = entity.getEntityBoundingBox();
        // This is where people found out that F3+B is not accurate for hitboxes,
        // it makes hitboxes bigger by certain amount
        float collisionBorderSize = entity.getCollisionBorderSize();
        AxisAlignedBB otherHitbox = otherBB.expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
        // Not quite sure what the difference is between these two vectors
        // In actual code where this is taken from, partialTicks is always 1.0
        // So this won't decrease accuracy
        Vec3 eyePos = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 lookPos = mc.thePlayer.getLook(1.0F);
        // Get vector for raycast
        Vec3 adjustedPos = eyePos.addVector(lookPos.xCoord * maxSize, lookPos.yCoord * maxSize, lookPos.zCoord * maxSize);
        MovingObjectPosition movingObjectPosition = otherHitbox.calculateIntercept(eyePos, adjustedPos);
        Vec3 otherEntityVec;
        // This will trigger if hit distance is more than maxSize
        if (movingObjectPosition == null)
            return -1;
        otherEntityVec = movingObjectPosition.hitVec;
        // finally calculate distance between both vectors
        double dist = eyePos.distanceTo(otherEntityVec);

        mc.mcProfiler.endSection();
        return dist;
    }

}
