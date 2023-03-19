package Engine;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame(" Chess");
        frame.setSize(700, 700);
        frame.getContentPane().add(new Game());
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}