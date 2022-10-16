import javax.swing.*;
public class Main extends JFrame{
    private Champ champ;
    private GUI gui;
    private Client client;
    public Champ getChamp(){
        return champ;
    }
    public GUI getGUI(){
        return this.gui;
    }
    public void quit() {
        System.out.println("Bye-Bye ");
        System.exit(0) ;
    }
    Main(){
        champ = new Champ(Level.EASY);
        champ.placeMines();
        champ.affText();
        setTitle("LOL");
        gui = new GUI(client);
        setContentPane(gui);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        new Main();
    }
}