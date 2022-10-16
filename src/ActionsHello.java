import java.awt.event.*;

public class ActionsHello implements ActionListener {
    public static final int QUIT = 0;
    public static final int RESET = 1;
    public static final int MENUQ = 2;
    public static final int EASY = 3;
    public static final int MEDIUM = 4;
    public static final int HARD = 5;
    private Main main;
    private int type;

    public ActionsHello(int type, Main main){
        this.main = main;
        this.type = type;
    }

    public void actionPerformed(ActionEvent evt){
        if(type == QUIT){
            main.quit();
        }
        else if(type == RESET){
            main.getChamp().newGameChamp();
        }
        else if(type == MENUQ){
            main.quit();
        }
        else if(type == EASY){
            main.getChamp().easy();
        }
        else if(type == MEDIUM){
            main.getChamp().medium();
        }
        else if(type == HARD){
            main.getChamp().hard();
        }
    }
}
