package ga.matthewtgm.simplehud.elements;

public class ElementPosition {

    public ElementPosition(int x, int y) {
        this.setPosition(x, y);
    }

    public int x, y;
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setPosition(int x, int y) {
        this.setX(x);
        this.setY(y);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

}