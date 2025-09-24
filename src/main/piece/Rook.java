package main.piece;

import main.Board;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Rook extends Piece {
    public Rook(Board board, GamePanel gp, int col, int row, boolean isWhite) {
        super(board, gp);
        this.col = col;
        this.row = row;
        this.x = col * board.tileSize;
        this.y = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "rook";

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
        return newRow == row || newCol == col;
    }

    @Override
    public boolean blockedByOtherPiece(int newCol, int newRow) {
        if (newCol < col) {
            for(int c = col - 1; c > newCol; c--) {
                if(gp.getPiece(c, row) != null)
                    return true;
            }
        }

        if (newCol > col) {
            for(int c = col + 1; c < newCol; c++) {
                if(gp.getPiece(c, row) != null)
                    return true;
            }
        }

        if(newRow < row) {
            for(int r = row - 1; r > newRow; r--) {
                if(gp.getPiece(col, r) != null)
                    return true;
            }
        }

        if(newRow > row) {
            for(int r = row + 1; r < newRow; r++) {
                if(gp.getPiece(col, r) != null)
                    return true;
            }
        }

        return false;
    }
}
