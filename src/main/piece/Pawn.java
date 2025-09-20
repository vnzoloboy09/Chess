package main.piece;

import main.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pawn extends Piece {
    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board);
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
}
