package ga.matthewtgm.simplehud.gui;

import ga.matthewtgm.simplehud.Constants;
import ga.matthewtgm.simplehud.SimpleHUD;
import ga.matthewtgm.simplehud.elements.Element;
import ga.matthewtgm.simplehud.elements.Element.ElementGUI;
import ga.matthewtgm.simplehud.elements.ElementPosition;
import ga.matthewtgm.simplehud.gui.guielements.GuiSimpleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

public class GuiConfiguration extends GuiScreen {

    private GuiScreen parent;

    private final Logger logger = LogManager.getLogger(Constants.NAME + " (" + this.getClass().getSimpleName() + ")");

    private final Map<Element, ElementPosition> elements = new HashMap<>();
    private Optional<Element> selected = Optional.empty();

    private int prevX, prevY;

    private List<GuiButton> buttons;
    private void setButtons() {
        this.buttons = Arrays.asList(
                new GuiSimpleButton(0, 200, this.height - 20, this.parent == null ? "Close" : "Back")
        );
        this.setupElementButtons("init", null);
    }

    public GuiConfiguration(GuiScreen parent) {
        this.parent = parent;
        SimpleHUD.getInstance().getElementManager().getElements().forEach(e -> {
            elements.put(e, e.getPosition());
        });
    }

    @Override
    public void initGui() {
        this.setButtons();
        this.buttonList.addAll(buttons);
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0) Minecraft.getMinecraft().displayGuiScreen(this.parent);
        this.setupElementButtons("action", button);
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        this.elements.forEach((e, pos) -> {
            if(e.isToggled()) e.onRendered();
        });
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        for(Element element : SimpleHUD.getInstance().getElementManager().getElements()) {
            element.onSave();
            element.onLoad();
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(selected.isPresent()) {
            Element element = selected.get();
            ElementPosition position = element.getPosition();
            position.setPosition(position.getX() + mouseX - this.prevX, position.getY() + mouseY - this.prevY);
        }
        this.prevX = mouseX;
        this.prevY = mouseY;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.prevX = mouseX;
        this.prevY = mouseY;
        this.selected = this.elements.keySet().stream().filter(new MouseHoveringElement(mouseX, mouseY)).findFirst();
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void setupElementButtons(String type, GuiButton button) {
        int offset = 20;
        for(Element element : SimpleHUD.getInstance().getElementManager().getElements()) {
            if(type.equalsIgnoreCase("init")) {
                this.buttonList.add(new GuiSimpleButton(SimpleHUD.getInstance().getElementManager().getElements().indexOf(element) + 1, 0, this.height - offset, element.getName()));
                offset = offset + 20;
                element.elementScreen = new ElementGUI(this, element);
                if(offset > 120) offset = 0;
            } else if(type.equalsIgnoreCase("action")) {
                if(button.id == SimpleHUD.getInstance().getElementManager().getElements().indexOf(element) + 1) {
                    element.elementScreen = new ElementGUI(this, element);
                    Minecraft.getMinecraft().displayGuiScreen(element.elementScreen);
                }
            }
        }
    }

    private static class MouseHoveringElement implements Predicate<Element> {

        private final int x, y;

        public MouseHoveringElement(int x, int y) {
            this.x = x;
            this.y = y;
        }


        @Override
        public boolean test(Element element) {
            ElementPosition position = element.getPosition();
            int posX = position.x;
            int posY = position.y;
            if(x >= posX && x <= posX + element.width) {
                if(y >= posY && y <= posY + element.height) {
                    return true;
                }
            }
            return false;
        }
    }

}