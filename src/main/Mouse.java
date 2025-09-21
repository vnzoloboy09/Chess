package main;

import main.piece.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    Board board;
    GamePanel gp;
    boolean releasedCurrentPiece = false;

    public Mouse(Board board, GamePanel gp) {
        this.board = board;
        this.gp = gp;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        if(gp.isPromoting) {
            selectPromotion(col, row);
            return;
        }

        Piece piece = gp.getPiece(col, row);
        if(piece != null) {
            Move move = new Move(gp, col, row);
            if(gp.selectedPiece != null && gp.isValidMove(move)) {
                gp.makeMove(move);
                return;
            }
            if(gp.selectedPiece == null) {
                gp.selectedPiece = piece;
            }
            else if(gp.selectedPiece == piece) {
                releasedCurrentPiece = true;
            }
            else {
                gp.selectedPiece = piece;
            }
            return;
        }

        if(gp.selectedPiece != null) {
            Move move = new Move(gp, col, row);
            if(gp.isValidMove(move)) {
                gp.makeMove(move);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        if(gp.selectedPiece != null) {
            if(gp.selectedPiece.col != col || gp.selectedPiece.row != row) {
                Move move = new Move(gp, col, row);
                if(gp.isValidMove(move)) {
                    gp.makeMove(move);
                }
                else {
                    gp.selectedPiece.x = gp.selectedPiece.col * board.tileSize;
                    gp.selectedPiece.y = gp.selectedPiece.row * board.tileSize;
                    releasedCurrentPiece = false;
                }
            }
            else {
                gp.selectedPiece.x = gp.selectedPiece.col * board.tileSize;
                gp.selectedPiece.y = gp.selectedPiece.row * board.tileSize;
            }

            if(releasedCurrentPiece) {
                gp.selectedPiece = null;
                releasedCurrentPiece = false;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(gp.selectedPiece != null) {
            gp.selectedPiece.x = e.getX() - board.tileSize / 2;
            gp.selectedPiece.y = e.getY() - board.tileSize / 2;
        }
    }

    public void selectPromotion(int col, int row) {
        int diretion = gp.selectedPiece.isWhite? -1 : 1;
        if(row == gp.selectedPiece.row) {
            gp.pieces.add(new Queen(board, gp, gp.selectedPiece.col,
                    gp.selectedPiece.row, gp.selectedPiece.isWhite));
            gp.isPromoting = false;
            gp.selectedPiece = null;
            gp.turn = !gp.turn;
            return;
        }

        if(row == gp.selectedPiece.row - diretion) {
            gp.pieces.add(new Rook(board, gp, gp.selectedPiece.col,
                    gp.selectedPiece.row, gp.selectedPiece.isWhite));
            gp.isPromoting = false;
            gp.selectedPiece = null;
            gp.turn = !gp.turn;
            return;
        }

        if(row == gp.selectedPiece.row - diretion * 2) {
            gp.pieces.add(new Bishop(board, gp, gp.selectedPiece.col,
                    gp.selectedPiece.row, gp.selectedPiece.isWhite));
            gp.isPromoting = false;
            gp.selectedPiece = null;
            gp.turn = !gp.turn;
            return;
        }

        if(row == gp.selectedPiece.row - diretion * 3) {
            gp.pieces.add(new Knight(board, gp, gp.selectedPiece.col,
                    gp.selectedPiece.row, gp.selectedPiece.isWhite));
            gp.isPromoting = false;
            gp.selectedPiece = null;
            gp.turn = !gp.turn;
            return;
        }
    }
}
