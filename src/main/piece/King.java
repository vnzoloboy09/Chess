package main.piece;

import main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class King extends Piece {
    public King(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "king";

        String spritePath = "res/" + (isWhite ? "white_" : "black_") + name + ".png";
        BufferedImage buffer;
        try {
            buffer = ImageIO.read(ClassLoader.getSystemResourceAsStream(spritePath));
            this.sprite = buffer.getScaledInstance(64, 64, Image.SCALE_DEFAULT);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
