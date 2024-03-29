package swing;

import java.util.ArrayList;
import java.util.Iterator;

import ai.AI;
import logic.*;


public class GameModel {
    // будет список слушателей (заполняется в зависимости от настроек игрового поля)
    private ArrayList<ISubscriber> listeners = new ArrayList<ISubscriber>();
    // инициализирую два поля: игрок и соперник
    public Field playerFieldPlayer;
    public Field playerFieldOpponent;
    // инициализирую соперника
    public AI ai;
    // текущий игрок
    public int currentPlayer;
    // выстрел
    private boolean enableShot;

    public GameModel(int dx, int dy, int numShip) {
        playerFieldPlayer = new Field(dx, dy, numShip);
        playerFieldOpponent = new Field(dx, dy, numShip);
        ai = new AI(playerFieldPlayer);
        setDimention(dx, dy, numShip);
    }

    public void setDimention(int dx, int dy, int numShip) {
        playerFieldOpponent.setWidth(dx);
        playerFieldOpponent.setHeight(dy);
        playerFieldOpponent.setMaxShip(numShip);

        playerFieldPlayer.setWidth(dx);
        playerFieldPlayer.setHeight(dy);
        playerFieldPlayer.setMaxShip(numShip);
        enableShot = true;
        newGame();
        updateSubscribers();
    }


    public void newGame() {
        playerFieldPlayer.setShip();
        playerFieldOpponent.setShip();
        enableShot = true;
        currentPlayer = 0;
        updateSubscribers();
    }


    public void doShotByOpponent(int x, int y) {
        if (!enableShot) {
            return;
        }

        if (currentPlayer == 0) {
            if (playerFieldOpponent.getCell(x, y).isMark()) {
                return;
            }
            if (playerFieldOpponent.doShot(x, y) == Field.SHUT_MISSED) {
                currentPlayer = 1;
            }
        }

        if (currentPlayer ==1) {
            while (ai.doShot() != Field.SHUT_MISSED);
            currentPlayer = 0;
        }
        updateSubscribers();

        if ( (playerFieldPlayer.getNumLiveShips() == 0) || (playerFieldOpponent.getNumLiveShips() == 0) ) {
            enableShot = false;
        }
    }

    public void register(ISubscriber o) {
        listeners.add(o);
        o.update();
    }

    public void unRegister(ISubscriber o) {
        listeners.remove(o);
    }

    public void updateSubscribers() {
        Iterator<ISubscriber> i = listeners.iterator();
        while(i.hasNext()) {
            ISubscriber o = (ISubscriber)i.next();
            o.update();
        }
    }

}