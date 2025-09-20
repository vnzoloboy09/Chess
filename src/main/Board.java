package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import main.piece.*;

public class Board extends JPanel {
    public final int tileSize = 64;
    private final int m_Rows = 8;
    private final int m_Cols = 8;
    private final boolean WHITE = true;
    private final boolean BLACK = false;

    private ArrayList<Piece> pieces = new ArrayList<>();

    public Board() {
        this.setPreferredSize(new Dimension(m_Cols * tileSize, m_Rows * tileSize));

        addPiece();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for(int r = 0; r < m_Rows; r++) {
            for(int c = 0; c < m_Cols; c++) {
                g2d.setColor(((c + r) % 2) == 1? new Color(85, 150, 242) : new Color(242, 246, 250));
                g2d.fillRect(r * tileSize, c * tileSize, tileSize, tileSize);
            }
        }

        for(Piece piece : pieces) {
            piece.draw(g2d);
        }
    }

    private void addPiece() {
        for(int i = 0; i < m_Cols; i++) {
            pieces.add(new Pawn(this, i, 1, BLACK));
        }
        pieces.add(new Rook    (this, 0, 0, BLACK));
        pieces.add(new Knight  (this, 1, 0, BLACK));
        pieces.add(new Bishop  (this, 2, 0, BLACK));
        pieces.add(new Queen   (this, 3, 0, BLACK));
        pieces.add(new King    (this, 4, 0, BLACK));
        pieces.add(new Bishop  (this, 5, 0, BLACK));
        pieces.add(new Knight  (this, 6, 0, BLACK));
        pieces.add(new Rook    (this, 7, 0, BLACK));

        for(int i = 0; i < m_Cols; i++) {
            pieces.add(new Pawn(this, i, 6, WHITE));
        }
        pieces.add(new Rook    (this, 0, 7, WHITE));
        pieces.add(new Knight  (this, 1, 7, WHITE));
        pieces.add(new Bishop  (this, 2, 7, WHITE));
        pieces.add(new Queen   (this, 3, 7, WHITE));
        pieces.add(new King    (this, 4, 7, WHITE));
        pieces.add(new Bishop  (this, 5, 7, WHITE));
        pieces.add(new Knight  (this, 6, 7, WHITE));
        pieces.add(new Rook    (this, 7, 7, WHITE));
    }
}