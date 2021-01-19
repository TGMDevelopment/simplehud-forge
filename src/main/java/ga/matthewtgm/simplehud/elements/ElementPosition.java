package ga.matthewtgm.simplehud.elements;

public class ElementPosition {

    public ElementPosition(int x, int y, float scale) {
        this.setPosition(x, y, scale);
    }
    public int x, y;
    public float scale;
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public float getScale() {
        return scale;
    }
    public void setPosition(int x, int y) {
        this.setX(x);
        this.setY(y);
    }
    public void setPosition(int x, int y, float scale) {
        this.setX(x);
        this.setY(y);
        this.setScale(scale);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }
}