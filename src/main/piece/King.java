package main.piece;

import main.Board;
import main.GamePanel;
import main.Move;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class King extends Piece {
    public King(Board board, GamePanel gp, int col, int row, boolean isWhite) {
        super(board, gp);
        this.col = col;
        this.row = row;
        this.x = col * board.tileSize;
        this.y = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "king";

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
        return (Math.abs(newRow - row) <= 1 && Math.abs(newCol - col) <= 1) || canCastle(newCol, newRow);
    }

    @Override
    public boolean blockedByOtherPiece(int newCol, int newRow) {
        return false;
    }

    private boolean canCastle(int newCol, int newRow) {
        if(!isFirstMove)
            return false;

        if(row == newRow) {
            if(newCol == 6) {
                Piece rook = gp.getPiece(7, row);

                return rook != null && rook.isFirstMove &&
                       !gp.checkManager.gotChecked(new Move(gp, col, row)) &&
                       gp.getPiece(5, row) == null &&
                       gp.getPiece(6, row) == null;
            }

            if(newCol == 2) {
                Piece rook = gp.getPiece(0, row);

                return rook != null && rook.isFirstMove &&
                       !gp.checkManager.gotChecked(new Move(gp, col, row)) &&
                       gp.getPiece(2, row) == null &&
                       gp.getPiece(3, row) == null &&
                       gp.getPiece(1, row) == null;
            }
        }

        return false;
    }
}
