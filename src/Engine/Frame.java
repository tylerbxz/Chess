package Engine;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame(" Chess");
        frame.setSize(700, 700);
        frame.getContentPane().add(new Game());
        JPanel panel = new JPanel();
        JButton jb = new JButton("piece");
        jb.setSize(100,100);
        jb.setLocation(250,500);
//        frame.add(jb);
        panel.add(jb);
        panel.setVisible(true);
        //frame.getContentPane().add(panel);

        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void button() {
        add(new JButton("button1"), BorderLayout.SOUTH);
    }
}