package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import ga.matthewtgm.simplehud.files.FileHandler;
import ga.matthewtgm.simplehud.gui.GuiConfiguration;
import ga.matthewtgm.simplehud.gui.GuiElement;
import ga.matthewtgm.simplehud.utils.ColourUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class ElementArmourHUD extends Element {

    private RenderType type = RenderType.DURABILITY;

    public ElementArmourHUD() {
        super("ArmourHUD");
        this.width = 64;
        this.height = 64;

        boolean isConfigFileNull = SimpleHUD.getFileHandler().load(this.getName(), SimpleHUD.getFileHandler().elementDir) == null;
        final FileHandler handler = SimpleHUD.getFileHandler();

        if (isConfigFileNull) this.setType(RenderType.DURABILITY);
        else
            this.setType(RenderType.valueOf(String.valueOf(handler.load(this.getName(), handler.elementDir).get("type"))));
        this.onSave(new JSONObject());

        this.elementScreen = new GuiElement(new GuiConfiguration(SimpleHUD.getInstance().configGui), this) {

            @Override
            public void initGui() {
                super.initGui();
                this.buttonList.remove(this.showBrackets);
                this.buttonList.remove(this.showPrefix);
                this.buttonList.remove(this.chromaToggle);
                this.buttonList.add(chromaToggle = new GuiTransButton(14, this.width / 2 - 50, this.height / 2 + 80, 100, 20, "Chroma: " + (this.element.isChroma() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")));
                this.buttonList.add(new GuiTransButton(100, this.width / 2 + 5, this.height / 2 - 100, 100, 20, "Text Type: " + ((ElementArmourHUD) this.element).getType().name().toLowerCase()));
            }

            @Override
            protected void actionPerformed(GuiButton button) throws IOException {
                super.actionPerformed(button);
                if (button.id == 100) {
                    ((ElementArmourHUD) this.element).setType(((ElementArmourHUD) this.element).getType().getNextType(((ElementArmourHUD) this.element).getType()));
                    Minecraft.getMinecraft().displayGuiScreen(this);
                }
            }

        };
    }

    public RenderType getType() {
        return type;
    }

    public void setType(RenderType type) {
        this.type = type;
    }

    @Override
    public void onSetup() {
        boolean isConfigFileNull = SimpleHUD.getFileHandler().load(this.getName(), SimpleHUD.getFileHandler().elementDir) == null;
        final FileHandler handler = SimpleHUD.getFileHandler();

        if (isConfigFileNull) this.setType(RenderType.DURABILITY);
        else
            this.setType(RenderType.valueOf(String.valueOf(handler.load(this.getName(), handler.elementDir).get("type"))));
        this.onSave(new JSONObject());

        super.onSetup();
    }

    @Override
    public void onRendered(ElementPosition position) {
        if (this.background && this.backgroundColor != null)
            Gui.drawRect(position.getX() - 2, position.getY() - 2, position.getX() + this.width, position.getY() + this.height, this.backgroundColor.getRGBA());
        GlStateManager.pushMatrix();
        GlStateManager.scale(position.getScale(), position.getScale(), 1);
        for (int item = 0; item < mc.thePlayer.inventory.armorInventory.length; item++) {
            renderItemStack(position, item, mc.thePlayer.inventory.armorInventory[item]);
        }
        if (this.getType() == RenderType.NONE || this.getType() == RenderType.BARS)
            this.width = (int) (17 * position.getScale());
        else this.width = Math.round(64 * position.getScale());
        this.height = Math.round(64 * position.getScale());
        GlStateManager.popMatrix();
    }

    @Override
    public void onSave(JSONObject obj) {
        obj.put("type", this.getType().name());
        super.onSave(obj);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        final JSONObject obj = SimpleHUD.getFileHandler().load(this.getName(), SimpleHUD.getFileHandler().elementDir);
        this.setType(RenderType.valueOf(String.valueOf(obj.get("type"))));
    }

    public void renderItemStack(ElementPosition position, int index, ItemStack item) {
        if (item == null) return;
        GL11.glPushMatrix();
        int offset = (-16 * index) + 48;
        if (item.getItem().isDamageable() && this.getType() != RenderType.NONE) {
            switch (this.getType()) {
                case PERCENTAGE:
                    double currentDmg = (item.getMaxDamage() - item.getItemDamage()) / (double) item.getMaxDamage() * 100;
                    this.mc.fontRendererObj.drawString(String.format("%.2f%%", currentDmg), (position.getX() / position.getScale()) + 20, (position.getY() / position.getScale()) + 5 + offset, this.isChroma() ? ColourUtils.getInstance().getChroma() : this.colour.getRGB(), this.getTextShadow());
                    break;
                case DURABILITY:
                    this.mc.fontRendererObj.drawString(item.getMaxDamage() - item.getItemDamage() + "/" + item.getMaxDamage(), (position.getX() / position.getScale()) + 20, (position.getY() / position.getScale()) + 5 + offset, this.isChroma() ? ColourUtils.getInstance().getChroma() : this.colour.getRGB(), this.getTextShadow());
            }
        }
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(item, Math.round(position.getX() / position.getScale()), Math.round(position.getY() / position.getScale() + offset));
        if (this.getType() == RenderType.BARS) {
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableBlend();
            this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRendererObj, item, Math.round(position.getX() / position.getScale()), Math.round(position.getY() / position.getScale() + offset), "" + (item.stackSize > 1 ? Integer.valueOf(item.stackSize) : ""));
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
    }

    private enum RenderType {

        DURABILITY,
        PERCENTAGE,
        BARS,
        NONE;

        public RenderType getNextType(RenderType type) {
            return values()[(type.ordinal() + 1) % values().length];
        }

    }

}