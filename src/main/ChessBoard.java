package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ChessBoard {
    public static final int NUM_COLS = 8;
    public static final int NUM_ROWS = 8;
    public static final int SQUARE_SIZE = 60;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;

    private Color lightSquareColor;
    private Color darkSquareColor;

    private BufferedImage blackBishop, blackKing, blackKnight, blackPawn, blackQueen, blackRook
            ,whiteBishop, whiteKing, whiteKnight, whitePawn, whiteQueen, whiteRook;

    public ChessBoard() {
        Random random = new Random();
        int colorChoice = random.nextInt(0, 3);

        switch (colorChoice) {
            case 0:
                lightSquareColor = new Color(218, 218, 218);
                darkSquareColor = new Color(58, 58, 58);
                break;
            case 1:
                lightSquareColor = new Color(245, 230, 232);
                darkSquareColor = new Color(91, 26, 24);
                break;
            case 2:
                lightSquareColor = new Color(253, 246, 236);
                darkSquareColor = new Color(167, 199, 231);
                break;
        }
        loadImages();
    }

    public void draw(Graphics g2) {

        boolean isOddRow = false;
        boolean isOddCol = false;

        for (int r = 0; r < NUM_ROWS; r++) {
            if (r % 2 == 0) {
                isOddRow = true;
            } else {
                isOddRow = false;
            }

            for (int c = 0; c < NUM_COLS; c++) {
                if (c % 2 == 0) {
                    isOddCol = true;
                } else {
                    isOddCol = false;
                }
                if ((isOddRow && isOddCol) || (!isOddRow && !isOddCol)) {
                    g2.setColor(lightSquareColor);
                } else {
                    g2.setColor(darkSquareColor);
                }
                // x, y, width, height
                g2.fillRect(c * SQUARE_SIZE, r * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

                BufferedImage pieceImage = null;
                if (r == 0) {
                    if (c == 0 || c == NUM_COLS - 1) {
                        pieceImage = blackRook;
                    } else if (c == 1 || c == NUM_COLS - 2) {
                        pieceImage = blackKnight;
                    } else if (c == 2 || c == NUM_COLS - 3) {
                        pieceImage = blackBishop;
                    } else if (c == 3) {
                        pieceImage = blackQueen;
                    } else if (c == 4) {
                        pieceImage = blackKing;
                    }
                    g2.drawImage(pieceImage, c * SQUARE_SIZE, r, null);
                } else {
                    if (r == 1) {
                        pieceImage = blackPawn;
                    } else if (r == NUM_ROWS - 2) {
                        pieceImage = whitePawn;
                    } else if (r == NUM_ROWS - 1) {
                        if (c == 0 || c == NUM_COLS - 1) {
                            pieceImage = whiteRook;
                        } else if (c == 1 || c == NUM_COLS - 2) {
                            pieceImage = whiteKnight;
                        } else if (c == 2 || c == NUM_COLS - 3) {
                            pieceImage = whiteBishop;
                        } else if (c == 3) {
                            pieceImage = whiteQueen;
                        } else if (c == 4) {
                            pieceImage = whiteKing;
                        }
                    }
                    g2.drawImage(pieceImage, c * SQUARE_SIZE, r * SQUARE_SIZE, null);
                }
            }
        }
    }

    private void loadImages() {
        try {
            blackBishop = ImageIO.read(getClass().getResourceAsStream("/pieces/blackBishop.png"));
            blackKing = ImageIO.read(getClass().getResourceAsStream("/pieces/blackKing.png"));
            blackKnight = ImageIO.read(getClass().getResourceAsStream("/pieces/blackKnight.png"));
            blackPawn = ImageIO.read(getClass().getResourceAsStream("/pieces/blackPawn.png"));
            blackQueen = ImageIO.read(getClass().getResourceAsStream("/pieces/blackQueen.png"));
            blackRook = ImageIO.read(getClass().getResourceAsStream("/pieces/blackRook.png"));
            whiteBishop = ImageIO.read(getClass().getResourceAsStream("/pieces/whiteBishop.png"));
            whiteKing = ImageIO.read(getClass().getResourceAsStream("/pieces/whiteKing.png"));
            whiteKnight = ImageIO.read(getClass().getResourceAsStream("/pieces/whiteKnight.png"));
            whitePawn = ImageIO.read(getClass().getResourceAsStream("/pieces/whitePawn.png"));
            whiteQueen = ImageIO.read(getClass().getResourceAsStream("/pieces/whiteQueen.png"));
            whiteRook = ImageIO.read(getClass().getResourceAsStream("/pieces/whiteRook.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
