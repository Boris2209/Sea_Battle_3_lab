package swing;

import javax.swing.UIManager;

public class FrameGameLauncher {

    public static void main(String[] args) {
        try {
            // устанавливаю внешний вид окна приложения
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // теперь непосредственно игровое окно
        GameModel model = new GameModel(10, 10, 4);
        GameView view = new GameView(model);
        view.setVisible(true);
    }

}
