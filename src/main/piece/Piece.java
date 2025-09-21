package main.piece;

import main.Board;
import main.GamePanel;

import java.awt.*;

public abstract class Piece {
    public Image sprite;
    public int col, row;
    public int x, y;
    public int preCol, preRow;
    public boolean isFirstMove = true;
    public boolean isWhite;
    int value;
    Board board;
    GamePanel gp;
    public String name;

    public Piece(Board board, GamePanel gamePanel) {
        this.gp = gamePanel;
        this.board = board;
    }

    public void updatePosition() {
        x = col * board.tileSize;
        y = row * board.tileSize;
    }

    public abstract boolean isValidMovement(int newCol, int newRow);
    public abstract boolean blockedByOtherPiece(int newCol, int newRow);

    public void draw(Graphics2D g2d) {
        g2d.drawImage(sprite, x, y, null);
    }
}
