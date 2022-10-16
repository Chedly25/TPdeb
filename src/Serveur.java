import java.net.* ; // Sockets
import java.io.* ; // Streams
import java.util.HashMap;

public class Serveur implements Runnable{
    private ServerSocket gestSock;
    private Champ champ;
    private int nbrClients;
    private int clicX = 0;
    private int clicY = 0;
    private boolean gameOver;
    private HashMap<Integer, Integer> listeScores;
    Serveur() {
        System.out.println("Démarrage du serveur");
        System.out.println(nbrClients);
        // Création d'un nouveau champ par défaut (EASY)
        champ = new Champ(Level.EASY);
        champ.placeMines();
        champ.affText();
        listeScores = new HashMap<Integer, Integer>();
        try {
            // gestionnaire de socket, port 10000
            gestSock = new ServerSocket(10000);
            nbrClients = 0;

        }  catch (IOException e) {
            e.printStackTrace();
        }
            try {
                //Création du thread de gestion de la partie
                Thread th = new Thread(this);
                th.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection Error");
            }
    }
    public void run(){
        try{
            //Acceptation de clients sans limite
            Socket socket = gestSock.accept();
            nbrClients ++;
            System.out.println(nbrClients);
            //Start du nouveau thread pour le nouveau client
            Thread th = new Thread(this);
            th.start();
            // ouverture des streams
            DataInputStream entree = new DataInputStream(socket.getInputStream());
            DataOutputStream sortie = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // Envoi du numéro du joueur
            sortie.writeInt(nbrClients);
            // Envoi du champ par défaut
            objectOutputStream.writeObject(champ);
            //Réception du nom du joueur
            String nomJoueur = entree.readUTF();
            System.out.println(nomJoueur + " connected");
            listeScores.put(nbrClients, 0);
            //Instanciation des threads de lecture et écriture
            ReadFromClient rfc = new ReadFromClient(entree);
            WriteToClient wtc = new WriteToClient(sortie);
            //WriteToClient wtc = new WriteToClient(sortie, objectOutputStream);
            Thread thRead = new Thread(rfc);
            Thread thWrite = new Thread(wtc);
            thRead.start();
            thWrite.start();
        } catch (IOException e) {
                e.printStackTrace();
            }
    }
    private class ReadFromClient implements Runnable{
        private DataInputStream entree;
        public ReadFromClient(DataInputStream entree){
            this.entree = entree;
        }
        public void run(){
            while(true){
                try {
                    clicX = entree.readInt();
                    clicY = entree.readInt();
                    int numJ = entree.readInt();
                    int score = entree.readInt();
                    listeScores.put(numJ, score);
                    //gameOver = entree.readBoolean();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private class WriteToClient implements Runnable{
        private DataOutputStream sortie;
        //private ObjectOutputStream oos;
        public WriteToClient(DataOutputStream sortie){

            this.sortie = sortie;
            //this.oos = oos;
        }
        public void run(){
            while(true){
                try {
                    sortie.writeInt(clicX);
                    sortie.writeInt(clicY);
                    sortie.flush();
                    /**if(gameOver){
                        oos.writeObject(listeScores);
                    }*/
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static void main(String [] args) {
        new Serveur();
    }
}