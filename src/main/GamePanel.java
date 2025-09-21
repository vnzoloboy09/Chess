package main;

import main.piece.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    final int WIDTH = 1100;
    final int HEIGHT = 800;
    final int FPS = 60;
    public final boolean WHITE = true;
    public final boolean BLACK = false;
    public boolean turn = WHITE;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse(board, this);

    ArrayList<Piece> pieces = new ArrayList<Piece>();
    Piece selectedPiece = null;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        setPieces();
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {

    }

    public Piece getPiece(int col, int row) {
        for(Piece p : pieces) {
            if(p.col == col && p.row == row) {
                return p;
            }
        }
        return null;
    }

    public boolean isValidMove(Move move) {
        if(move.capturePiece != null && move.capturePiece.isWhite == move.piece.isWhite) {
            return false;
        }

        if(!move.piece.isValidMovement(move.newCol, move.newRow)) {
            return false;
        }

        if(move.piece.blockedByOtherPiece(move.newCol, move.newRow)) {
            return false;
        }

        return true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        board.draw(g2d);

        if(selectedPiece != null) {
            g2d.setColor(new Color(155, 235, 237, 190));
            g2d.fillRect(selectedPiece.col * board.tileSize, selectedPiece.row * board.tileSize,
                    board.tileSize, board.tileSize);
        }

        for(Piece p : pieces) {
            p.draw(g2d);
        }
    }

    public void setPieces() {
        for(int i = 0; i < board.colSize; i++) {
            pieces.add(new Pawn(board, this, i, 1, BLACK));
        }
        pieces.add(new Rook    (board, this, 0, 0, BLACK));
        pieces.add(new Knight  (board, this, 1, 0, BLACK));
        pieces.add(new Bishop  (board, this, 2, 0, BLACK));
        pieces.add(new Queen   (board, this, 3, 0, BLACK));
        pieces.add(new King    (board, this, 4, 0, BLACK));
        pieces.add(new Bishop  (board, this, 5, 0, BLACK));
        pieces.add(new Knight  (board, this, 6, 0, BLACK));
        pieces.add(new Rook    (board, this, 7, 0, BLACK));

        for(int i = 0; i < board.rowSize; i++) {
            pieces.add(new Pawn(board, this, i, 6, WHITE));
        }
        pieces.add(new Rook    (board, this, 0, 7, WHITE));
        pieces.add(new Knight  (board, this, 1, 7, WHITE));
        pieces.add(new Bishop  (board, this, 2, 7, WHITE));
        pieces.add(new Queen   (board, this, 3, 7, WHITE));
        pieces.add(new King    (board, this, 4, 7, WHITE));
        pieces.add(new Bishop  (board, this, 5, 7, WHITE));
        pieces.add(new Knight  (board, this, 6, 7, WHITE));
        pieces.add(new Rook    (board, this, 7, 7, WHITE));
    }
}
