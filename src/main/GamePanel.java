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
    public class MoveInfo {
        public int oldCol = -1, oldRow = -1;
        public int newCol = -1, newRow = -1;
    }
    MoveInfo preMove = new MoveInfo();
    public int enPassantTileCol = -1;
    public int enPassantTileRow = -1;
    public boolean isPromoting = false;

    public CheckManager checkManager = new CheckManager(this);

    ArrayList<Piece> pieces = new ArrayList<Piece>();
    Piece selectedPiece = null;

    SoundPlayer soundPlayer = new SoundPlayer();

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
        if(move.piece.isWhite != turn)
            return false;

        if(sameTeam(move.piece, move.capturePiece))
            return false;

        if(!move.piece.isValidMovement(move.newCol, move.newRow))
            return false;

        if(move.piece.blockedByOtherPiece(move.newCol, move.newRow))
            return false;

        if(checkManager.gotChecked(move))
            return false;

        return true;
    }

    public void makeMove(Move move) {
        if(move.capturePiece == null)
            soundPlayer.playMoveSound();
        else
            soundPlayer.playCaptureSound();

        if(move.piece.name.equals("pawn")) {
            makeMoveForPawn(move);
        }
        else if(move.piece.name.equals("king")) {
            makeMoveForKing(move);
        }

        preMove.oldCol = move.oldCol;
        preMove.oldRow = move.oldRow;
        preMove.newCol = move.newCol;
        preMove.newRow = move.newRow;

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.x = move.newCol * board.tileSize;
        move.piece.y = move.newRow * board.tileSize;

        move.piece.isFirstMove = false;
        if(!isPromoting) {
            turn = !turn;
            selectedPiece = null;
        }

        pieces.remove(move.capturePiece);
    }

    private void makeMoveForPawn(Move move) {
        int direction = (move.piece.isWhite? -1 : 1);

        if(move.newCol == enPassantTileCol && move.newRow == enPassantTileRow) {
            move.capturePiece = getPiece(move.newCol, move.newRow - direction);
        }

        if(Math.abs(move.newRow - move.piece.row) == 2) {
            enPassantTileCol = move.newCol;
            enPassantTileRow = move.newRow - direction;
        }
        else {
            enPassantTileCol = -1;
            enPassantTileRow = -1;
        }

        int promotionRow = (move.piece.isWhite? 0 : 7);
        if(move.newRow == promotionRow) {
            promotePawn(move);
        }
    }

    public void promotePawn(Move move) {
        pieces.remove(move.piece);
        isPromoting = true;
    }

    public boolean sameTeam(Piece p1, Piece p2) {
        if(p1 == null || p2 == null)
            return false;
        return p1.isWhite == p2.isWhite;
    }

    public Piece getKing(boolean side) {
        for(Piece p : pieces) {
            if(p.isWhite != side) {
                continue;
            }
            if(p.name.equals("king"))
                return p;
        }
        return null;
    }

    private void makeMoveForKing(Move move) {
        if(Math.abs(move.newCol - move.piece.col) == 2) {
            Piece rook;
            if(move.piece.col < move.newCol) {
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            }
            else {
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }
            rook.x = rook.col * board.tileSize;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        board.draw(g2d);

        g2d.setColor(new Color(155, 235, 237, 190));
        g2d.fillRect(preMove.oldCol * board.tileSize, preMove.oldRow * board.tileSize,
                board.tileSize, board.tileSize);
        g2d.fillRect(preMove.newCol * board.tileSize, preMove.newRow * board.tileSize,
                board.tileSize, board.tileSize);

        for(Piece p : pieces) {
            if(p == selectedPiece)
                continue;
            p.draw(g2d);
        }

        if(selectedPiece != null) {
            g2d.setColor(new Color(155, 235, 237, 190));
            g2d.fillRect(selectedPiece.col * board.tileSize, selectedPiece.row * board.tileSize,
                    board.tileSize, board.tileSize);
            for(int r = 0; r < board.rowSize; r++) {
                for (int c = 0; c < board.colSize; c++) {
                    if (isValidMove(new Move(this, c, r))) {
                        g2d.setColor(new Color(178, 186, 178, 186));
                        g2d.fillOval(c * board.tileSize + board.tileSize / 2 - 15,
                                r * board.tileSize + board.tileSize / 2 - 15, 30, 30);
                    }
                }
            }
            selectedPiece.draw(g2d); // make the selected piece always on top
        }

        if(isPromoting) {
            drawPromoteMenu(g2d);
        }
    }

    public void drawPromoteMenu(Graphics2D g2d) {
        int direction = selectedPiece.isWhite? -1 : 1;

        String[] option = {"Queen", "Rook", "Bishop", "Knight"};

        for(int i = 0; i < 4; i++) {
            g2d.setColor(new Color(151, 204, 143));
            g2d.fillRect(selectedPiece.col * board.tileSize, (selectedPiece.row - i * direction) * board.tileSize,
                    board.tileSize, board.tileSize);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.setColor(Color.BLACK);
            g2d.drawString(option[i], selectedPiece.col * board.tileSize + 5,
                    (selectedPiece.row - i * direction + 1) * board.tileSize - board.tileSize / 2);
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
