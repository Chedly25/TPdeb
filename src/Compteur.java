import javax.swing.*;

class Compteur implements Runnable {
    private Thread processScores ; // thread associé a la classe
    private long totalElapsed;
    private long start;
    private long previousTop;
    private Timer timer;
    private boolean reset;
    private boolean running;
    /** création thread + son lancement */
    Compteur() {
        processScores = new Thread(this) ; // creation du thread
        processScores.start() ; // lancement du thread
    }
    /** surcharge de Runnable : comportement du processus */
    public void run () {
        while (processScores != null) // mettre condition de sortie
            try {
                processScores.sleep(1000); // dodo 1s
                start = System.currentTimeMillis();
                previousTop = start;
                timer.start();
                reset = false;
                running = true;
            }catch(InterruptedException e){e.printStackTrace();}
    }
    public long getTime() {
        return totalElapsed;
    }
}

