package ga.matthewtgm.simplehud.gui;

import ga.matthewtgm.lib.gui.GuiTransButton;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.utils.GuiScreenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;

public class GuiConfigurationCategories {
    public static class GuiConfigurationPvP extends GuiScreen {
        private final GuiScreen parent;

        public GuiConfigurationPvP(GuiScreen parent) {
            this.parent = parent;
        }

        public GuiScreen getParent() {
            return parent;
        }

        @Override
        public void initGui() {
            this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, this.parent == null ? "Save and close" : "Save and go back"));
            this.setupElementButtons("init", null);
        }

        @Override
        protected void actionPerformed(GuiButton button) throws IOException {
            if (button.id == 0) Minecraft.getMinecraft().displayGuiScreen(this.parent);
            this.setupElementButtons("action", button);
        }

        @Override
        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            super.drawDefaultBackground();
            GuiScreenUtils.getInstance().slideGuiTitleIntoScreen(this, EnumChatFormatting.LIGHT_PURPLE + "Simple" + EnumChatFormatting.DARK_PURPLE + "HUD " + EnumChatFormatting.WHITE + "- " + EnumChatFormatting.RED + "PvP");
            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        private void setupElementButtons(String type, GuiButton button) {
            int offset = this.height / 2 - 50;
            int offsetX = this.width / 2 - 50;
            for (Element element : SimpleHUD.getInstance().getElementManager().getElements()) {
                if (element.category.equals("PvP")) {
                    if (type.equalsIgnoreCase("init")) {
                        this.buttonList.add(new GuiTransButton(SimpleHUD.getInstance().getElementManager().getElements().indexOf(element) + 1, offsetX, this.height - offset, 100, 20, element.getName()));
                        offset += 25;
                        if (offset > ((this.height / 2) / SimpleHUD.getInstance().getElementManager().getElements().size() * 20)) {
                            offsetX = this.width / 2 + 5;
                            offset = this.height / 2 - 50;
                        }
                    } else if (type.equalsIgnoreCase("action")) {
                        if (button.id == SimpleHUD.getInstance().getElementManager().getElements().indexOf(element) + 1) {
                            Minecraft.getMinecraft().displayGuiScreen(element.elementScreen);
                        }
                    }
                }
            }
        }
    }

    public static class GuiConfigurationGeneral extends GuiScreen {
        private final GuiScreen parent;

        public GuiConfigurationGeneral(GuiScreen parent) {
            this.parent = parent;
        }

        public GuiScreen getParent() {
            return parent;
        }

        @Override
        public void initGui() {
            this.buttonList.add(new GuiTransButton(0, this.width / 2 - 50, this.height - 20, 100, 20, this.parent == null ? "Save and close" : "Save and go back"));
            this.setupElementButtons("init", null);
        }

        @Override
        protected void actionPerformed(GuiButton button) throws IOException {
            if (button.id == 0) Minecraft.getMinecraft().displayGuiScreen(this.parent);
            this.setupElementButtons("action", button);
        }

        @Override
        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            super.drawDefaultBackground();
            GuiScreenUtils.getInstance().slideGuiTitleIntoScreen(this, EnumChatFormatting.LIGHT_PURPLE + "Simple" + EnumChatFormatting.DARK_PURPLE + "HUD " + EnumChatFormatting.WHITE + "- " + EnumChatFormatting.AQUA + "General");
            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        private void setupElementButtons(String type, GuiButton button) {
            int offset = this.height / 2 - 50;
            int offsetX = this.width / 2 - 105;
            for (Element element : SimpleHUD.getInstance().getElementManager().getElements()) {
                if (element.category.equals("General")) {
                    if (type.equalsIgnoreCase("init")) {
                        this.buttonList.add(new GuiTransButton(SimpleHUD.getInstance().getElementManager().getElements().indexOf(element) + 1, offsetX, this.height - offset, 100, 20, element.getName()));
                        offset += 25;
                        if (offset > ((this.height / 2) / SimpleHUD.getInstance().getElementManager().getElements().size() * 20)) {
                            offsetX = this.width / 2 + 5;
                            offset = this.height / 2 - 50;
                        }
                        if (offset > ((this.height / 2) / SimpleHUD.getInstance().getElementManager().getElements().size() * 20) && offsetX == this.width / 2 + 5) {
                            offsetX = this.width / 2 + 105;
                            offset = this.height / 2 - 50;
                        }
                    } else if (type.equalsIgnoreCase("action")) {
                        if (button.id == SimpleHUD.getInstance().getElementManager().getElements().indexOf(element) + 1) {
                            Minecraft.getMinecraft().displayGuiScreen(element.elementScreen);
                        }
                    }
                }
            }
        }
    }
}