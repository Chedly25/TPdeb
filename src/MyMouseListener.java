import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyMouseListener extends JPanel implements MouseListener {
    private Main main;
    private Case cases;
    private final static int DIM=20 ;

    public void mousePressed(MouseEvent e){
        Graphics gc = getGraphics();
        gc.setColor(Color.white);
        gc.fillRect(DIM, DIM, DIM,DIM); // dessin du texte à la position i,j
    }

    public void mouseClicked(MouseEvent e){
        Component[] componentList = main.getGUI().panelcase.getComponents();
        for(Component c : componentList) {
            if (c == e.getComponent()) {
                main.getGUI().panelcase.remove(c);
            }
        }
    }
    public void mouseReleased (MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
