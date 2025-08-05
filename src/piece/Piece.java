package piece;

import main.ChessBoard;
import main.ChessPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {
    public BufferedImage image;
    public int x, y;
    public int currentCol, currentRow, previousCol, previousRow;
    public int color;
    public String name;

    public Piece (int color, int currentCol, int currentRow, String name) {
        this.color = color;
        this.currentCol = currentCol;
        this.currentRow = currentRow;

        this.x = getX(currentCol);
        this.y = getY(currentRow);

        this.previousCol = currentCol;
        this.previousRow = currentRow;

        this.name = name;
        getImage();
    }

    public int getX (int col) {
        return col * ChessBoard.SQUARE_SIZE;
    }

    public int getY (int row) {
        return row * ChessBoard.SQUARE_SIZE;
    }

    public void getImage() {
        BufferedImage pieceImage = null;
        String imagePath = "/pieces/";
        if (color == ChessPanel.WHITE) {
            imagePath += "White";
        } else {
            imagePath += "Black";
        }

        imagePath += (name + ".png");
        try {
            pieceImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            System.err.printf("IO Exception. Couldn't find the image for %s at %s.\n", name, imagePath);
        } catch (IllegalArgumentException e) {
            System.err.printf("Illegal Argument Exception. Couldn't find the image for %s at %s.\n", name, imagePath);
        }
        this.image = pieceImage;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, null);
    }

    public int getCurrentCol(int x) {
        return (x + ChessBoard.HALF_SQUARE_SIZE) / ChessBoard.SQUARE_SIZE;
    }
    public int getCurrentRow(int y) {
        return (y + ChessBoard.HALF_SQUARE_SIZE) / ChessBoard.SQUARE_SIZE;
    }
}
