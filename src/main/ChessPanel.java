package main;

import piece.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChessPanel extends JPanel implements Runnable {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;
    private final int FPS = 60;
    private Thread gameThread;
    public ChessBoard chessBoard;

    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public int currentPlayerColor;

    public ArrayList<Piece> pieces;
    public ArrayList<Piece> simPieces;

    private MouseHandler mouseHandler;
    private Piece activePiece;

    public ChessPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        chessBoard = new ChessBoard();
        currentPlayerColor = WHITE;

        pieces = new ArrayList<>();
        simPieces = new ArrayList<>();

        setPieces();
        copyPieces(pieces, simPieces);

        mouseHandler = new MouseHandler();
        addMouseMotionListener(mouseHandler);
        addMouseListener(mouseHandler);
    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();
        for (int i = 0; i < source.size(); i++) {
            target.add(source.get(i));
        }
    }

    private void setPieces() {
        pieces.add(new Pawn(BLACK, 0, 1));
        pieces.add(new Pawn(BLACK, 1, 1));
        pieces.add(new Pawn(BLACK, 2, 1));
        pieces.add(new Pawn(BLACK, 3, 1));
        pieces.add(new Pawn(BLACK, 4, 1));
        pieces.add(new Pawn(BLACK, 5, 1));
        pieces.add(new Pawn(BLACK, 6, 1));
        pieces.add(new Pawn(BLACK, 7, 1));
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Knight(BLACK, 1, 0));
        pieces.add(new Bishop(BLACK, 2, 0));
        pieces.add(new Queen(BLACK, 3, 0));
        pieces.add(new King(BLACK, 4, 0));
        pieces.add(new Bishop(BLACK, 5, 0));
        pieces.add(new Knight(BLACK, 6, 0));
        pieces.add(new Rook(BLACK, 7, 0));

        pieces.add(new Pawn(WHITE, 0, ChessBoard.NUM_ROWS - 2));
        pieces.add(new Pawn(WHITE, 1, ChessBoard.NUM_ROWS - 2));
        pieces.add(new Pawn(WHITE, 2, ChessBoard.NUM_ROWS - 2));
        pieces.add(new Pawn(WHITE, 3, ChessBoard.NUM_ROWS - 2));
        pieces.add(new Pawn(WHITE, 4, ChessBoard.NUM_ROWS - 2));
        pieces.add(new Pawn(WHITE, 5, ChessBoard.NUM_ROWS - 2));
        pieces.add(new Pawn(WHITE, 6, ChessBoard.NUM_ROWS - 2));
        pieces.add(new Pawn(WHITE, 7, ChessBoard.NUM_ROWS - 2));
        pieces.add(new Rook(WHITE, 0, ChessBoard.NUM_ROWS - 1));
        pieces.add(new Knight(WHITE, 1, ChessBoard.NUM_ROWS - 1));
        pieces.add(new Bishop(WHITE, 2, ChessBoard.NUM_ROWS - 1));
        pieces.add(new Queen(WHITE, 3, ChessBoard.NUM_ROWS - 1));
        pieces.add(new King(WHITE, 4, ChessBoard.NUM_ROWS - 1));
        pieces.add(new Bishop(WHITE, 5, ChessBoard.NUM_ROWS - 1));
        pieces.add(new Knight(WHITE, 6, ChessBoard.NUM_ROWS - 1));
        pieces.add(new Rook(WHITE, 7, ChessBoard.NUM_ROWS - 1));
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        if (mouseHandler.isMousePressed) {
            // player is not holding a piece
            if (activePiece == null) {
                for (Piece piece: simPieces) {
                    if (piece.color == currentPlayerColor &&
                            piece.currentCol == mouseHandler.x / ChessBoard.SQUARE_SIZE &&
                            piece.currentRow == mouseHandler.y / ChessBoard.SQUARE_SIZE) {
                        activePiece = piece;
                    }
                }
            }
            // player is already holding a piece
            else {
                simulate();
            }
        }
    }

    private void simulate() {
        activePiece.x = mouseHandler.x - ChessBoard.HALF_SQUARE_SIZE;
        activePiece.y = mouseHandler.y - ChessBoard.HALF_SQUARE_SIZE;
        activePiece.currentRow = activePiece.getCurrentRow(activePiece.y);
        activePiece.currentCol = activePiece.getCurrentCol(activePiece.x);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        chessBoard.draw(g2);

        for (Piece p: simPieces) {
            p.draw(g2);
        }

        if (activePiece != null) {
            g2.setColor(Color.white);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2.fillRect(activePiece.currentCol * ChessBoard.SQUARE_SIZE, activePiece.currentRow * ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            activePiece.draw(g2);
        }
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
