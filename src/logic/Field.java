package logic;

import java.util.ArrayList;

public class Field {
    // переменные состояния на подобии переменных из классов Cell и Ship (еще раз не буду описывать)
    public final static int SHUT_MISSED = 1;
    public final static int SHUT_INJURED = 2;
    public final static int SHUT_KILLED = 3;
    public final static int SHUT_WON = 4;
    //игровое поле (чего так не хваатло в предложенных UML)
    private Cell[][] cells;
    // списко кораблей
    private ArrayList<Ship> ships;
    // параметры игрового поля
    private int width;
    private int height;
    private int maxShip;
    private int numLiveShips;

    public Field(int x, int y, int ship) {
        setDimention(x, y, ship);
        setShip();
    }

    // вынес отдельно этот метод, что бы можно было изменять размер игрового поля
    // (реализую это в версии игры с графическим интерфейсом)
    public void setDimention(int x, int y, int ship) {
        setWidth(x);
        setHeight(y);
        setMaxShip(ship);
    }

    // добавляет новый корабль на игровое поле
    public void setShip() {
        setNumLiveShips(0);
        // на поле (массив клеток)
        cells = new Cell[getWidth()][getHeight()];
        for(int j = 0; j < getHeight(); j++) {
            for(int i = 0; i < getWidth(); i++) {
                cells[i][j] = new Cell(i, j);
            }
        }
        // в спсиок кораблей
        ships = new ArrayList<Ship>();
        for(int i = getMaxShip(); i > 0; i--) {
            for(int j = (getMaxShip() - i +1 ); j > 0; j--) {
                Ship ship=new Ship(this,i);
                ships.add(ship);
            }
        }

        // изменяет параметры клеток на необходимые (переменные состояния)
        for(int j = 0; j < getHeight(); j++) {
            for(int i = 0; i < getWidth(); i++) {
                Cell cell = cells[i][j];
                if (cell.getState() == Cell.CELL_BORDER) {
                    cell.setState(Cell.CELL_WATER);
                }
            }
        }
    }

    // если был выстрел
    public int doShot(int x, int y) {
        int shot = getCell(x, y).doShot();
        return shot;
    }

    // далее методы так же просты и понятны
    public boolean isBound(int x, int y) {
        return !( (x < 0) || (x > (getWidth() - 1)) || (y < 0) || (y > (getHeight() - 1) ) );
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMaxShip() {
        return maxShip;
    }

    public void setMaxShip(int maxShip) {
        this.maxShip = maxShip;
    }

    public int getNumLiveShips() {
        return numLiveShips;
    }

    public void setNumLiveShips(int numLiveShips) {
        this.numLiveShips = numLiveShips;
    }

}