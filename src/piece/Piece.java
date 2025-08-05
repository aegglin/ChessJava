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

        this.x = getX();
        this.y = getY();

        this.previousCol = currentCol;
        this.previousRow = currentRow;

        this.name = name;
        getImage();
    }

    public int getX () {
        return currentCol * ChessBoard.SQUARE_SIZE;
    }

    public int getY () {
        return currentRow * ChessBoard.SQUARE_SIZE;
    }

    public void getImage() {
        BufferedImage pieceImage = null;
        String imagePath = "/res/pieces/";
        if (color == ChessPanel.WHITE) {
            imagePath += "White";
        } else {
            imagePath += "Black";
        }

        imagePath += (name + ".png");
        try {
            pieceImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            System.err.printf("Couldn't find the image for %s at %s.\n", name, imagePath);
//            e.printStackTrace();
        }
        this.image = pieceImage;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, getX(), getY(), null);
    }
}
