package main.piece;

import main.Board;
import main.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pawn extends Piece {
    public Pawn(Board board, GamePanel gp, int col, int row, boolean isWhite) {
        super(board, gp);
        this.col = col;
        this.row = row;
        this.x = col * board.tileSize;
        this.y = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "pawn";

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
        int direction = (isWhite ? -1 : +1);
        if(newCol == col && newRow == row + direction && gp.getPiece(newCol, newRow) == null) {
            return true;
        }

        if(isFirstMove && newCol == col && newRow == row + direction * 2 &&
            gp.getPiece(newCol, newRow) == null && gp.getPiece(newCol, newRow - direction) == null)
        {
            return true;
        }

        if(Math.abs(newCol - col) == 1 && newRow == row + direction && gp.getPiece(newCol, newRow) != null) {
            return true;
        }

        if(gp.enPassantTileCol == newCol && gp.enPassantTileRow == newRow &&
            Math.abs(newCol - col) == 1 && newRow == row + direction &&
                gp.getPiece(newCol, newRow - direction) != null)
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean blockedByOtherPiece(int newCol, int newRow) {
        return false;
    }
}
