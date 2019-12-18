package logic;

import java.util.ArrayList;
import java.util.Random;

public class Ship {
    // переменный состояния корабля
    public final static int SHIP_WELL = 1;      // целый
    public final static int SHIP_INJURED = 2;   // раненый
    public final static int SHIP_KILLED = 3;    // убитый

    // переменный "головы" корабля
    public int x;
    public int y;
    // переменный "направления" корабля
    // (кстати их не хватало в ТЗ лабораторной, то есть не было продумано размещение кораблей)
    public int dx;
    public int dy;
    private int size;   // размер
    private int health; // сколько еще осталось "неподбитых" клеток
    private int state;  // состояние корабля (цел, ранен, убит)
    private Field field;// поле, которому принадлежит корабль
    private ArrayList<Cell> listCells;  //список клеток, которые занимает корабль
    private ArrayList<Cell> listBorders;    // списко пограничных клеток (вокруг корабля)

    public ArrayList<Cell> getListCells() {
        return listCells;
    }

    public ArrayList<Cell> getListBorders() {
        return listBorders;
    }

    public Ship(Field field, int size) {
        this.size = size;
        this.health = size;
        this.field = field;
        this.state = Ship.SHIP_WELL;

        do {
            this.getPlace();
        } while (! checkPlace() );

        this.listCells = new ArrayList<Cell>();
        this.listBorders = new ArrayList<Cell>();
        this.setShip();

        getField().setNumLiveShips(getField().getNumLiveShips() + 1);
    }

    private void getPlace() {
        Random rand = new Random();
        this.x = rand.nextInt(getField().getWidth());
        this.y = rand.nextInt(getField().getHeight());
        this.dx = 0;
        this.dy = 0;
        if (rand.nextInt(2) == 1) {
            this.dx = 1;
        } else {
            this.dy = 1;
        }
    }

    private boolean byPass(PlaceShip tp) {
        int i, m, n;

        for(i = 0; i < size; i++) {
            // êîðàáëü
            m = y + i * dy;
            n = x + i * dx;
            if (! tp.setShip(m, n) ) {
                return false;
            }

            // ïëîùàäêà ñâåðõó è ñíèçó êîðàáëÿ
            m = y + i * dy - dx;
            n = x + i * dx - dy;
            if (! tp.setBorder(m, n) ) {
                return false;
            }
            m = y + i * dy + dx;
            n = x + i * dx + dy;
            if (! tp.setBorder(m, n) ) {
                return false;
            }
        }
        // ïëîùàäêà ñëåâà è ñïðàâà êîðàáëÿ
        for(i = -1; i < 2; i++) {
            m = y + i * dx - dy;
            n = x + i * dy - dx;
            if (! tp.setBorder(m, n) ) {
                return false;
            }
            m = y + i * dx + (dy * size);
            n = x + i * dy + (dx * size);
            if (! tp.setBorder(m, n) ) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPlace() {
        return byPass(new PlaceShipCheck(this));
    }

    private void setShip() {
        byPass(new PlaceShipSet(this));
    }

    public int doShot() {
        if (health != 0) {
            health--;
            if (health == 0) {
                getField().setNumLiveShips(getField().getNumLiveShips() - 1);
                state = Ship.SHIP_KILLED;
                for(Cell e : listCells) {
                    e.setState(Cell.CELL_KILLED);
                }
                for(Cell e : listBorders) {
                    e.setState(Cell.CELL_MISSED);
                    e.setMark(true);
                }
                return Field.SHUT_KILLED;
            } else {
                state = SHIP_INJURED;
            }
        }
        return Field.SHUT_INJURED;
    }

    public int getSize() {
        return size;
    }

    public int getState() {
        return state;
    }

    public Field getField() {
        return field;
    }

}