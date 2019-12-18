package logic;

public class Cell {
    // для удобства ввожу констатны состояния клеток
    public final static int CELL_WATER = 1;     // клетка воды (ничего нет)
    public final static int CELL_BORDER = 2;    // клетка окружения корабля
    public final static int CELL_WELL = 3;      // клетка целого корабля
    public final static int CELL_INJURED = 4;   // клетка раненного корабля
    public final static int CELL_KILLED = 5;    //клетка убитого корабля
    public final static int CELL_MISSED = 6;    // клетка с попавшим выстрелом

    //координаты клетки на поле
    public int x;
    public int y;
    // состояние клетки
    private int state;
    // принадлежность
    private Ship ship;
    // отметка
    private boolean mark;
    // конструктор
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = CELL_WATER;
        this.mark = false;
    }

    // следующие методлы интуитивно понятны
    public void setState(int state) {
        this.state = state;
    }


    public int getState() {
        return state;
    }


    public boolean isMark() {
        return mark;
    }


    public void setMark(boolean mark) {
        this.mark = mark;
    }


    public Ship getShip() {
        return ship;
    }


    public void setShip(Ship ship) {
        this.ship = ship;
    }

    //изменение состояния в случае попадания по клетке
    public int doShot() {
        setMark(true);
        if (state == CELL_WELL) {
            setState(CELL_INJURED);
            return getShip().doShot();
        } else {
            if ( (state == CELL_BORDER) || (state == CELL_WATER)) {
                setState(CELL_MISSED);
            }
        }
        return Field.SHUT_MISSED;
    }

}
