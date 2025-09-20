package main.piece;

import main.Board;
import java.awt.*;

public abstract class Piece {
    Image sprite;
    public int xPos, yPos;
    int col, row;
    boolean isFirstMove = true;
    boolean isWhite;
    int value;
    Board board;
    String name;

    public Piece(Board board) {
        this.board = board;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}
