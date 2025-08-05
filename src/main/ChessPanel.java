package main;

import javax.swing.*;
import java.awt.*;

public class ChessPanel extends JPanel implements Runnable {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;
    private final int FPS = 60;
    private Thread gameThread;
    public ChessBoard chessBoard;

    public ChessPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        chessBoard = new ChessBoard();
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        chessBoard.draw(g2);
    }

    @Override
    public void run() {
        // Game Loop
        double drawInterval = 1e9 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }
}
