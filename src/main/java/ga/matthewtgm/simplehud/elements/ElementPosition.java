package ga.matthewtgm.simplehud.elements;

public class ElementPosition {

<<<<<<< Updated upstream
    public ElementPosition(int x, int y, int scale) {
        this.setPosition(x, y, scale);
    }
    public int x, y, scale;
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getScale() {
=======
    public int x, y;
    public float scale;
    public ElementPosition(int x, int y, float scale) {
        this.setPosition(x, y, scale);
    }

    public float getScale() {
>>>>>>> Stashed changes
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setPosition(int x, int y) {
        this.setX(x);
        this.setY(y);
    }
<<<<<<< Updated upstream
    public void setPosition(int x, int y, int scale) {
=======

    public void setPosition(int x, int y, float scale) {
>>>>>>> Stashed changes
        this.setX(x);
        this.setY(y);
        this.setScale(scale);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
<<<<<<< Updated upstream
    public void setScale(int scale) {
        this.scale = scale;
=======

    public void setY(int y) {
        this.y = y;
>>>>>>> Stashed changes
    }
}