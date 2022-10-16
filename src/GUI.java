import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.*;
import javax.swing.Timer;

/**
 * Graphical User Interface
 */
public class GUI extends JPanel implements Serializable, ActionListener {
    private Client client;
    private JLabel[][] tabMines;
    private Case[][] cases;
    private int nbmines;
    private int compteur;
    private int score;
    private JLabel nbrMines;
    private JLabel Score;
    private JLabel time = new JLabel();
    private boolean quit;
    private boolean isclicked;
    private boolean startCo;
    private int clicX;
    private int clicY;
    private Timer timer;
    private int elapsedTime;
    private int nbrCasesOuvertes;
    private int sec;
    private int min;
    private boolean fieldChanged;
    private boolean gameOver;
    private int dimension1;
    private int dimension2;

    JPanel panelcenter = new JPanel();
    JPanel panelcase = new JPanel();
    JPanel panelnorth = new JPanel();
    JPanel panelTime = new JPanel();
    ImageIcon restart = new ImageIcon(new ImageIcon("C:\\Users\\chedl\\OneDrive\\Bureau\\EMSE\\3A\\Java\\TPdeb\\src\\restart.png").getImage().getScaledInstance(60, 50, Image.SCALE_DEFAULT));
    ImageIcon stopGame = new ImageIcon(new ImageIcon("C:\\Users\\chedl\\OneDrive\\Bureau\\EMSE\\3A\\Java\\TPdeb\\src\\quit.png").getImage().getScaledInstance(50, 40, Image.SCALE_DEFAULT));

    JButton bouton = new JButton(restart);
    JButton quitter = new JButton(stopGame);
    private Case lastTileClicked;
    GUI(Client client) {
        setLayout(new BorderLayout());
        this.client = client;
        int dim1 = client.getChamp().getDim1();
        int dim2 = client.getChamp().getDim2();
        this.tabMines = new JLabel[dim1][dim2];
        cases = new Case[dim1][dim2];
        setPanel(dim1, dim2);
        setMenu();
        fillPanels(dim1, dim2);
        timer();
        add(panelcenter, BorderLayout.CENTER);
        add(panelcase, BorderLayout.CENTER);
        add(panelnorth, BorderLayout.NORTH);
        panelnorth.add(panelTime, BorderLayout.WEST);
    }
    public void setFieldChanged(){ fieldChanged = !fieldChanged; }
    public boolean getFieldChanged(){ return fieldChanged;}
    public Client getClient(){
        return client;
    }
    public boolean getQuit() { return quit;}
    public synchronized Case getcase(int i, int j) { return cases[i][j];};
    public JPanel getPanelcase(){ return panelcase;}
    public void setPanel(int dim1, int dim2){
        panelcenter.setLayout(new GridLayout(dim1, dim2, 2, 2));
        panelcenter.setBorder( new MatteBorder(2, 2, 2, 2, Color.BLACK) );
        panelcase.setLayout(new GridLayout(dim1, dim2));
        bouton.setPreferredSize(new Dimension(50,40));
        quitter.setPreferredSize(new Dimension(50,40));
        panelnorth.add(bouton, BorderLayout.CENTER);
        bouton.addActionListener(this);
        quitter.addActionListener(this);
        panelnorth.add(quitter, BorderLayout.CENTER);
    }
    public void setMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuPartie = new JMenu("Partie");
        menuBar.add(menuPartie);
        JMenuItem mQuitter = new JMenuItem("Quitter", KeyEvent.VK_Q);
        JMenu mLevel = new JMenu("Choisir le niveau");
        JMenuItem easy = new JMenuItem("Easy");
        JMenuItem medium = new JMenuItem("Medium");
        JMenuItem hard = new JMenuItem("Hard");
        JMenuItem menuReseau = new JMenuItem("Se connecter");
        mLevel.add(easy);
        mLevel.add(medium);
        mLevel.add(hard);
        menuPartie.add(mLevel);
        menuPartie.add(mQuitter);
        menuPartie.add(menuReseau);
        mQuitter.addActionListener(this);
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        menuReseau.addActionListener(this);
        client.setJMenuBar(menuBar);
    }
    public void fillPanels(int dim1, int dim2){
        nbmines = client.getChamp().getNbMines();
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                String str = client.getChamp().xOuR(i, j);
                JLabel lab = new JLabel(str);
                panelcenter.add(lab);
                lab.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                tabMines[i][j] = lab;
            }
        }
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                cases[i][j] = new Case(i,j, this);
                panelcase.add(cases[i][j]);
            }
        }
        nbrMines = new JLabel(String.valueOf(nbmines));
        String str = "Score: " + score;
        Score = new JLabel(str);
        panelnorth.add(nbrMines);
        panelnorth.add(Score);
        //Score.setHorizontalAlignment(SwingConstants.LEFT);
    }
    public void timer(){
        ActionListener task = e -> {
            elapsedTime++;
            sec = elapsedTime;
            if(sec % 60 == 0){
                min++;
                elapsedTime = 0;
                sec = 0;
            }
            if(sec>=10) {
                time.setText("Temps écoulé: " + "0" + min + " : " + sec);
            }
            else if(min>=10){
                time.setText("Temps écoulé: " + "" + min + " : 0" + sec);
            }
            else{
                time.setText("Temps écoulé: " + "0" + min + " : 0" + sec);
            }
        panelTime.add(time);
        };
        timer = new Timer(1000, task);
        timer.start();
    }
    public void decreasenbmines(){
        nbmines --;
        nbrMines.setText(String.valueOf(nbmines));
    }
    public void increasenbmines(){
        nbmines ++;
        nbrMines.setText(String.valueOf(nbmines));
    }
    public int[] setCustom(){
        dimension1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Longueur" ));
        dimension2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Largeur" ));
        return new int[]{dimension1, dimension2};
    }
    public void increasenbrCasesOuvertes(){
        nbrCasesOuvertes++;
    }
    public void setIsClicked(){
        isclicked = true;
    }
    public boolean getIsClicked(){ return isclicked;}
    public boolean lostOrNot(int i, int j){
        boolean loss = false;
        if(tabMines[i][j].getText() == "x"){
            JOptionPane.showMessageDialog(null, "You lost" ); // blabla
            loss = true;
            gameOver = true;
        }
        return loss;
    }
    public boolean wonorNot(){
        boolean win = false;
        int nbr = client.getChamp().getNbMines();
        int dim1 = client.getChamp().getDim1();
        int dim2 = client.getChamp().getDim2();
        if( dim1*dim2 - nbrCasesOuvertes == nbr){
            JOptionPane.showMessageDialog(null, "GG WP" );
            win = true;
            gameOver = true;
        }
        return win;
    }
    public boolean getGameOver(){
        return gameOver;
    }
    public SortedSet<Integer> sortHashMap(HashMap<Integer, Integer> hash){
        SortedSet<Integer> sorted = new TreeSet<>(hash.values());
        return sorted;
    }
    public void showGameOver(){
        if(gameOver) {
            String str = "";
            HashMap<Integer, Integer> hash = client.getListeScores();
            SortedSet<Integer> sortedHash = sortHashMap(hash);
            JOptionPane.showMessageDialog(null, Collections.singletonList(hash));
            System.out.println(Collections.singletonList(hash));
        }
    }
    public void zeroACote(int x, int y) {
        if (!tabMines[x][y].getText().equals("x") && !tabMines[x][y].getText().equals("0")) {
            return;
        } else {
            try {
                if (tabMines[x][y].getText().equals("0")) {
                    cases[x][y].setHasFilled();
                    int borneinfx = x == 0 ? 0 : x - 1;
                    int borneinfy = y == 0 ? 0 : y - 1;
                    int bornesupx = x == client.getChamp().getDim1() - 1 ? client.getChamp().getDim1() - 1 : x + 1;
                    int bornesupy = y == client.getChamp().getDim2() - 1 ? client.getChamp().getDim2() - 1 : y + 1;
                    for (int i = borneinfx; i <= bornesupx; i++) {
                        for (int j = borneinfy; j <= bornesupy; j++) {
                            if (i != x || j != y) {
                                if (!tabMines[i][j].getText().equals("x") && !cases[i][j].getclick()) {
                                    cases[i][j].setClick();
                                    nbrCasesOuvertes++;
                                }
                                if (tabMines[i][j].getText().equals("0") && !cases[i][j].getHasFilled()) {
                                    zeroACote(i, j);
                                }
                            }
                            //else
                        }
                    }
                }
            } catch (StackOverflowError stackOverflowError) {
                System.out.println(" OMG Please Stop");
            }
        }
    }
    public void updateScore(int x, int y){
        if(tabMines[x][y].getText().equals("x")){
            score +=10;
            client.setScore(score);
        }
        else if(!tabMines[x][y].getText().equals("x") && cases[x][y].getRightClick()){
            score --;
            client.setScore(score);
        }
        else{
            score++;
            client.setScore(score);
        }
        Score.setText("Score: " + score);
        panelnorth.add(Score);
        panelnorth.repaint();
    }
    public void updateClick(int i, int j){
        clicX = i;
        clicY = j;
    }
    public int getclicX(){ return clicX;}
    public int getclicY(){ return clicY;}
    public synchronized void setClic(int x, int y){
        cases[x][y].setClick();
        zeroACote(x, y);
    }
    public String showText(){
        String txt = JOptionPane.showInputDialog(null, "What's ya name bruh?" );
        return txt;
    }
    private void updatePanel(int dim1, int dim2, JPanel panel){
        panel.removeAll();
        panel.setLayout(new GridLayout(dim1, dim2));
    }
    /**
     * nouveau champ pour recommencer une partie au même niveau
     */
    public void newChamp() {
        int dim1 = client.getChamp().getDim1();
        int dim2 = client.getChamp().getDim2();
        updatePanel(dim1, dim2, panelcase);
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                String str = client.getChamp().xOuR(i,j);
                tabMines[i][j].setText(str);
            }
        }
        nbmines = client.getChamp().getNbMines();
        nbrMines.setText(String.valueOf(nbmines));
        score = 0;
        Score.setText("Score: " + score);
        panelnorth.add(Score);
        panelnorth.add(nbrMines);
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                cases[i][j] = new Case(i,j, this);
                panelcase.add(cases[i][j]);
            }
        }
    }

    /**
     * nouveau champ pour recommencer une partie dans un niveau différent
     */
    public boolean getStartCo(){
        return startCo;
    }
    public void newChampLevel(){
        int dim1 = client.getChamp().getDim1();
        int dim2 = client.getChamp().getDim2();
        tabMines = new JLabel[dim1][dim2];
        cases = new Case[dim1][dim2];
        updatePanel(dim1, dim2, panelcenter);
        updatePanel(dim1, dim2, panelcase);
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                String str = client.getChamp().xOuR(i,j);
                JLabel lab = new JLabel(str);
                panelcenter.add(lab);
                tabMines[i][j] = lab;
            }
        }
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                cases[i][j] = new Case(i,j, this);
                panelcase.add(cases[i][j]);
            }
        }
        nbmines = client.getChamp().getNbMines();
        nbrMines.setText(String.valueOf(nbmines));
        panelnorth.add(nbrMines);
        score = 0;
        Score.setText("Score: " + score);
        panelnorth.add(Score);
        panelnorth.repaint();
        add(panelcenter, BorderLayout.CENTER);
        add(panelcase, BorderLayout.CENTER);
        add(panelnorth, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quitter) {
            client.quit();
            quit = true;
        } else if (e.getSource() == bouton) {
            client.getChamp().newGameChamp();
            newChamp();
        }
        if (e.getSource() instanceof JMenuItem) {
            String ChoixOption = e.getActionCommand();
            if (ChoixOption.equals("Quitter")) {
                client.quit();
            }
            if (ChoixOption.equals("Easy")) {
                client.getChamp().easy();
                newChampLevel();
                setFieldChanged();
            }
            if (ChoixOption.equals("Medium")) {
                client.getChamp().medium();
                newChampLevel();
                setFieldChanged();
            }
            if (ChoixOption.equals("Hard")) {
                client.getChamp().hard();
                newChampLevel();
                setFieldChanged();
            }
            if(ChoixOption.equals("Se connecter")){
                startCo = true;
                client.initClientServ();
            }
        }
    }
}

