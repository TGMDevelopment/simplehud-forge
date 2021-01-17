package ga.matthewtgm.simplehud.elements;

import java.awt.*;

public class ElementColour {

    public ElementColour(int r, int g, int b) {
        this.setRGB(r, g, b);
    }
    public ElementColour(int r, int g, int b, int a) {
        this.setRGBA(r, g, b, a);
    }
    private int r, g, b, a;
    public int getR() {
        return r;
    }
    public int getG() {
        return g;
    }
    public int getB() {
        return b;
    }
    public int getA() {
        return a;
    }
    public int getRGB() {
        return new Color(this.r, this.g, this.b).getRGB();
    }
    public int getRGBA() {
        return new Color(this.r, this.g, this.b, this.a).getRGB();
    }

    public void setR(int r) {
        this.r = r;
    }
    public void setG(int g) {
        this.g = g;
    }
    public void setB(int b) {
        this.b = b;
    }
    public void setA(int a) {
        this.a = a;
    }
    public void setRGB(int r, int g, int b) {
        this.setR(r);
        this.setG(g);
        this.setB(b);
    }
    public void setRGBA(int r, int g, int b, int a) {
        this.setR(r);
        this.setG(g);
        this.setB(b);
        this.setA(a);
    }
}