package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Simple Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        ChessPanel chessPanel = new ChessPanel();
        window.add(chessPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        chessPanel.launchGame();
    }
}
