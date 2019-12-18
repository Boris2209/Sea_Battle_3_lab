package logic;

public class PlaceShipSet extends PlaceShip {

    private Ship ship;

    public PlaceShipSet(Ship ship) {
        super(ship);
        this.ship = ship;
    }

    // переопределяю методы абстрактоно класса. Проверка можно ли занимать эти клетки
    @Override
    public boolean setShip(int x, int y) {
        getField().getCell(x, y).setState(Cell.CELL_WELL);
        ship.getListCells().add(getField().getCell(x, y));
        getField().getCell(x, y).setShip(ship);
        return true;
    }

    @Override
    public boolean setBorder(int x, int y) {
        if ( getField().isBound(x, y) ) {
            getField().getCell(x, y).setState(Cell.CELL_BORDER);
            ship.getListBorders().add(getField().getCell(x, y));
        }
        return true;
    }
}