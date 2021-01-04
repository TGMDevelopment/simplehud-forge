package ga.matthewtgm.simplehud.elements.impl;

import ga.matthewtgm.simplehud.elements.Element;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ElementCPS extends Element {

    private final List<Long> leftClicks = new ArrayList<>();
    private final List<Long> rightClicks = new ArrayList<>();

    private boolean leftWasPressed = false;
    private long leftLastPressed;

    private boolean rightWasPressed = false;
    private long rightLastPressed;

    public ElementCPS() {
        super("CPS");
        this.height = 10;
        this.elementScreen = new ElementGUI(this);
    }

    @Override
    public String getRenderedString() {
        StringBuilder sb = new StringBuilder();
        if(this.shouldRenderBrackets()) sb.append("[");
        if(this.prefix != null) {
            sb.append(this.prefix);
            sb.append(": ");
        }
        sb.append(this.getRenderedValue());
        if(this.shouldRenderBrackets()) sb.append("]");
        return sb.toString();
    }

    @Override
    public void onRendered() {
        this.setRenderedValue(this.getCPS());
        this.mc.fontRendererObj.drawString(this.getRenderedString(), this.getPosition().getX(), this.getPosition().getY(), this.colour.getHex());
        this.width = this.mc.fontRendererObj.getStringWidth(this.getRenderedString());
        super.onRendered();
    }

    public String getCPS() {
        return this.getPlayerLeftCPS() + " | " + this.getPlayerRightCPS();
    }

    private int getPlayerLeftCPS() {

        final boolean pressed = Mouse.isButtonDown(0);

        if(pressed != leftWasPressed) {
            leftWasPressed = pressed;
            leftLastPressed = System.currentTimeMillis();
            if(pressed) {
                this.leftClicks.add(leftLastPressed);
            }
        }

        this.leftClicks.removeIf(looong -> looong + 1000 < System.currentTimeMillis());
        return this.leftClicks.size();
    }

    private int getPlayerRightCPS() {
        final boolean pressed = Mouse.isButtonDown(1);

        if(pressed != rightWasPressed) {
            rightWasPressed = pressed;
            rightLastPressed = System.currentTimeMillis();
            if(pressed) {
                this.rightClicks.add(rightLastPressed);
            }
        }

        this.rightClicks.removeIf(looong -> looong + 1000 < System.currentTimeMillis());
        return this.rightClicks.size();
    }

}