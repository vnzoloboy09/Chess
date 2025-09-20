package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static final String TITLE = "Chess";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        JFrame frame = new JFrame(TITLE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        Board board = new Board();
        frame.add(board);
        frame.pack();

        frame.setVisible(true);
    }
}