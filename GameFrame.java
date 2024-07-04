import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
// Game Frame is basically a frame which will have maximise, minimise and close options.
public class GameFrame extends JFrame{
    GamePanel panel;
    GameFrame(){
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("PongGame");
        this.setResizable(false);
        this.setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
