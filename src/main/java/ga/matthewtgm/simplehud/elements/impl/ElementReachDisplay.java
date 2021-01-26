package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
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

public class ElementReachDisplay extends Element {

    private Double reach = null;
    private long lastHit = 0L;

    public ElementReachDisplay() {
        super("Reach Display", "PvP");
        if (this.prefix == null) prefix = "Reach Display";
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (event.entity instanceof EntityPlayerSP) {
            reach = getReachDistanceFromEntity(event.target);
            lastHit = System.currentTimeMillis();
        }
    }

    @Override
    public void onRendered(ElementPosition position) {
        if (System.currentTimeMillis() - lastHit > 3000)
            reach = null;
        this.setRenderedValue(reach == null ? "none" : getReachFormatted() + " Blocks");
        this.height = Math.round(10 * this.getPosition().getScale());
        super.onRendered(position);
    }

    private String getReachFormatted() {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(reach);
    }

    private double getReachDistanceFromEntity(Entity entity) {
        double maxSize = 20D;
        AxisAlignedBB otherBB = entity.getEntityBoundingBox();
        float collisionBorderSize = entity.getCollisionBorderSize();
        AxisAlignedBB otherHitbox = otherBB.expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
        Vec3 eyePos = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 lookPos = mc.thePlayer.getLook(1.0F);
        Vec3 adjustedPos = eyePos.addVector(lookPos.xCoord * maxSize, lookPos.yCoord * maxSize, lookPos.zCoord * maxSize);
        MovingObjectPosition movingObjectPosition = otherHitbox.calculateIntercept(eyePos, adjustedPos);
        Vec3 otherEntityVec;
        if (movingObjectPosition == null)
            return -1;
        otherEntityVec = movingObjectPosition.hitVec;
        return eyePos.distanceTo(otherEntityVec);
    }

}