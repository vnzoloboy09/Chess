package main.piece;

import main.Board;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Knight extends Piece {
    public Knight(Board board, GamePanel gp, int col, int row, boolean isWhite) {
        super(board, gp);
        this.col = col;
        this.row = row;
        this.x = col * board.tileSize;
        this.y = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "knight";

        String spritePath = "res/" + (isWhite ? "white_" : "black_") + name + ".png";
        BufferedImage buffer;
        try {
            buffer = ImageIO.read(ClassLoader.getSystemResourceAsStream(spritePath));
            this.sprite = buffer.getScaledInstance(board.tileSize, board.tileSize, Image.SCALE_DEFAULT);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValidMovement(int newCol, int newRow) {
        return Math.abs(newRow - row) * Math.abs(newCol - col) == 2;
    }

    @Override
    public boolean blockedByOtherPiece(int newCol, int newRow) {
        return false;
    }
}
