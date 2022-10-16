import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import java.awt.image.ImagingOpException;
import java.util.Date;

public class Case extends JPanel implements MouseListener{
    private int posX;
    private int posY;
    private GUI gui;
    private boolean click = false;
    private boolean rightclick;
    private boolean release = false;
    private boolean enter = false;
    private boolean exit = false;
    private boolean press = false;
    private boolean addflag = false;
    private boolean hasfilled;
    private final static int DIM = 20 ;
    private ImageIcon flag = new ImageIcon(new ImageIcon("C:\\Users\\chedl\\OneDrive\\Bureau\\EMSE\\3A\\Java\\TPdeb\\src\\flag.png").getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH));
    private ImageIcon bomb = new ImageIcon(new ImageIcon("C:\\Users\\chedl\\OneDrive\\Bureau\\EMSE\\3A\\Java\\TPdeb\\src\\bombe.png").getImage().getScaledInstance(DIM, DIM, Image.SCALE_DEFAULT));
    public Case(int x, int y, GUI gui) {
        this.gui = gui;
        setPreferredSize(new Dimension(DIM, DIM));
        addMouseListener(this);
        this.posX = x;
        this.posY = y;
    }
    public boolean getclick(){ return click;}
    public void setClick(){ click = true;}
    public void setHasFilled() { hasfilled = true;}
    public boolean getHasFilled(){ return hasfilled;}
    public boolean getRightClick(){
        return rightclick;
    }

@Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc); // appel méthode mère (efface le dessin précedent)
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        gc.setColor(Color.lightGray);
        gc.fillRect(0, 0, getWidth(), getHeight()); // dessin du texte à la position i,j
        if(exit && !click) {
            super.paintComponent(gc); // appel méthode mère (efface le dessin précedent)
            gc.setColor(Color.lightGray);
            gc.fillRect(0, 0, getWidth(), getHeight()); // dessin du texte à la position i,j
            exit = false;
        }
        else if(release && !click) {
            super.paintComponent(gc); // appel méthode mère (efface le dessin précedent)
            gc.setColor(Color.lightGray);
            gc.fillRect(0, 0, getWidth(), getHeight()); // dessin du texte à la position i,j
            release = false;
        }
        else if(click){
            super.paintComponent(gc);
            if(gui.getClient().getChamp().xOuR(posX, posY).equals("x")){
                gc.drawImage(bomb.getImage(), posX, posY, DIM, DIM, this);
            }
            else {
                if (gui.getClient().getChamp().xOuR(posX, posY).equals("1")) {
                    gc.setColor(Color.BLUE);
                } else if (gui.getClient().getChamp().xOuR(posX, posY).equals("2")) {
                    gc.setColor(Color.GREEN);
                } else if (gui.getClient().getChamp().xOuR(posX, posY).equals("3")) {
                    gc.setColor(Color.RED);
                } else if (gui.getClient().getChamp().xOuR(posX, posY).equals("4")) {
                    gc.setColor(Color.DARK_GRAY);
                }
                gc.drawString(gui.getClient().getChamp().xOuR(posX, posY), DIM / 2, DIM / 2);
            }
        }
        else if(addflag) {
                super.paintComponent(gc);
                gc.drawImage(flag.getImage(), posX, posY, DIM, DIM, this);
        }
        else if(enter){
            super.paintComponent(gc); // appel méthode mère (efface le dessin précedent)
            gc.setColor(Color.darkGray);
            gc.fillRect(0, 0, getWidth(), getHeight()); // dessin du texte à la position i,j
        }
        else if(press){
            super.paintComponent(gc); // appel méthode mère (efface le dessin précedent)
            gc.setColor(Color.white);
            gc.fillRect(0, 0, getWidth(), getHeight()); // dessin du texte à la position i,j
        }
    }

    public void mousePressed(MouseEvent e){
        press = true;
        repaint();
    }

    public int getPosX(){ return posX;}
    public int getPosY(){ return posY;}

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            click = true;
            gui.setIsClicked();
            gui.increasenbrCasesOuvertes();
            gui.updateClick(posX, posY);
            boolean loss = gui.lostOrNot(posX, posY);
            gui.zeroACote(posX, posY);
            gui.updateScore(posX, posY);
            repaint();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (!rightclick) {
                addflag = true;
                rightclick = true;
                gui.decreasenbmines();
                gui.updateScore(posX, posY);
                repaint();
            } else {
                addflag = false;
                rightclick = false;
                gui.increasenbmines();
                repaint();
            }
        }
    }
    public void mouseReleased (MouseEvent e) {
        release = true;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        enter = true;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        exit = true;
        repaint();
    }
}
