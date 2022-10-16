import java.util.*;
import java.io.Serializable;
public class Champ implements Serializable{

    private final int[] TAILLE = {10, 20, 30};
    private final int[] NBMINES = {10,40,60};
    private int nbmines;
    private boolean[][] champ;
    /**public Champ(){
        this.champ = new boolean[TAILLE][TAILLE];
        for(int i = 0; i<TAILLE;i++){
            for(int j = 0; j<TAILLE;j++){
                this.champ[i][j] = false;
            }
        }
    }*/
    public Champ(Level level){
        this.champ = new boolean[TAILLE[level.ordinal()]][TAILLE[level.ordinal()]];
        this.nbmines = NBMINES[level.ordinal()];
    }
    public int getNbMines(){ return nbmines;}
    public void placeMines(){
        int i = 1;
        Random gene = new Random();
        while(i<=nbmines){
            int case0 = gene.nextInt(champ[0].length);
            int case1 = gene.nextInt(champ[1].length);
            if(!this.champ[case0][case1]){
                this.champ[case0][case1] = true;
                i++;
            }
        }
    }

    public int getDim1(){
        return  this.champ[0].length;
    }
    public int getDim2(){ return this.champ[1].length;}
    public int nbrMines(int x, int y){
        int nb = 0;
        int borneinfx = x==0 ? 0: x-1;
        int borneinfy = y==0 ? 0: y-1;
        int bornesupx = x== champ.length-1 ? champ.length-1 : x+1;
        int bornesupy = y== champ[0].length-1 ? champ[0].length-1 : y+1;
        for(int i= borneinfx; i<=bornesupx; i++){
            for(int j = borneinfy; j<=bornesupy; j++){
                if(champ[i][j]){
                    nb++;
                }
            }
        }
        return nb;
    }
    public void affText(){
        for(int i = 0; i<champ.length; i++){
            for(int j = 0; j<champ[0].length; j++){
                if(this.champ[i][j]){
                    System.out.print("x");
                }
                else{
                    int n = nbrMines(i,j);
                    System.out.print(n);
                }

            }
            System.out.println();
        }
    }

    /**
     *
     * @param x
     * @param y
     * retourne la valeur d'une case (x,y)
     */
    public String xOuR(int x, int y){
        if(!champ[x][y]){
            return String.valueOf(nbrMines(x,y));
        }
        else{
            return "x";
        }
    }

    /**
     * reset le champ pour recommencer une partie avec le même niveau
     */
    public void resetChamp(){
        for(int i = 0; i<champ.length; i++){
            for(int j = 0; j<champ[0].length; j++) {
                this.champ[i][j] = false;
            }
        }
    }
    public void easy(){
        champ = new boolean[TAILLE[Level.EASY.ordinal()]][TAILLE[Level.EASY.ordinal()]];
        nbmines = NBMINES[Level.EASY.ordinal()];
        placeMines();
        affText();
    }
    public void medium(){
        champ = new boolean[TAILLE[Level.MEDIUM.ordinal()]][TAILLE[Level.MEDIUM.ordinal()]];
        nbmines = NBMINES[Level.MEDIUM.ordinal()];
        placeMines();
        affText();
    }
    public void hard(){
        champ = new boolean[TAILLE[Level.HARD.ordinal()]][TAILLE[Level.HARD.ordinal()]];
        nbmines = NBMINES[Level.HARD.ordinal()];
        placeMines();
        affText();
    }
    public void custom(int dim1, int dim2){
        champ = new boolean[dim1][dim2];
        nbmines = dim1*dim2/10;
        placeMines();
        affText();
    }
    public void newGameChamp(){
        resetChamp();
        placeMines();
        affText();
    }
}
