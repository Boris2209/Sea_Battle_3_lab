package logic;

public abstract class PlaceShip {
    // сдедал абстрактный класс располоджения корабля на поле (прсото что бы наследовать от него 2 других класса)
    public Field field;

    public PlaceShip(Ship ship) {
        this.field = ship.getField();
    }

    protected Field getField() {
        return field;
    }

    abstract public boolean setShip(int x, int y);
    abstract public boolean setBorder(int x, int y);
}
