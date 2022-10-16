import javax.swing.*;
import java.util.HashMap;
import java.util.Scanner;
import java.net.* ;
import java.io.* ;
public class Client extends JFrame {
    private Champ champ;
    private GUI gui;
    private int score;
    private HashMap<Integer, Integer> listeScores = new HashMap<>();

    public static void main(String [] args) {
        new Client();
    }
    Client() {
        champ = new Champ(Level.EASY);
        champ.placeMines();
        champ.affText();
        setTitle("LOL");
        gui = new GUI(this);
        setContentPane(gui);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void initClientServ() {
        Scanner scanner = new Scanner(System.in);
        try {// ouverture de la socket et des streams
            Socket sock = new Socket("192.168.56.1", 10000);
            System.out.println("Connecting");
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(sock.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(sock.getInputStream());
            // Réception du numéro du joueur
            int numJoueur = in.readInt();
            System.out.println("Joueur n°:" + numJoueur);
            // Réception du champ initial du jeu
            champ = (Champ) objectInputStream.readObject();
            //Création du GUI
            gui = new GUI(this);
            champ.affText();
            setTitle("Joueur " + numJoueur);
            setContentPane(gui);
            pack();
            setVisible(true);
            // Fenêtre de discussion pour prendre le nom du joueur
            String name = gui.showText();
            // Envoi du nom au serveur
            out.writeUTF(name);
            // Instanciation des classes de threads de lecture et écriture avec le serveur
            ReadFromServer rfs = new ReadFromServer(in, objectInputStream);
            WriteToServer wts = new WriteToServer(out, numJoueur);
            Thread thRead = new Thread(rfs);
            Thread thWrite = new Thread(wts);
            thRead.start();
            thWrite.start();
        } catch (UnknownHostException e) {
            System.out.println("Host inconnu");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Champ getChamp(){
        return champ;
    }

    public GUI getGUI(){
        return this.gui;
    }
    public Case getCaseClicked(int i, int j){
        return gui.getcase(i,j);
    }
    public void quit() {
        System.out.println("Bye-Bye ");
        System.exit(0) ;
    }
    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){ return score;}

    public HashMap<Integer, Integer> getListeScores() {
        return listeScores;
    }

    private class ReadFromServer implements Runnable{
        private DataInputStream entree;
        private ObjectInputStream ois;
        public ReadFromServer(DataInputStream entree, ObjectInputStream ois){
            this.entree = entree;
            this.ois = ois;
        }
        public void run(){
            // Le thread tourne tout au long de la partie
            while(true){
                try {
                    //Réception de la case cliquée par l'autre joueur
                    int x = entree.readInt();
                    int y = entree.readInt();
                    if((x==0 && y==0) && !gui.getcase(0, 0).getclick()) {
                        System.out.println("yo");
                    }
                    else{
                        gui.setClic(x, y);
                    }
                    listeScores = (HashMap<Integer, Integer>) ois.readObject();

                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try{
                    //Mise en pause du thread
                    Thread.sleep(25);
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private class WriteToServer implements Runnable{
        private DataOutputStream sortie;
        private int numJoueur;
        public WriteToServer(DataOutputStream sortie, int numJoueur){
            this.sortie = sortie;
            this.numJoueur = numJoueur;
        }
        public void run(){
            while(true){
                try {
                    sortie.writeInt(gui.getclicX());
                    sortie.writeInt(gui.getclicY());
                    sortie.writeInt(numJoueur);
                    sortie.writeInt(score);
                    //sortie.writeBoolean(gui.getGameOver());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try{
                    Thread.sleep(25);
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}