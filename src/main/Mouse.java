package main;

import main.piece.Piece;

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

        Piece piece = gp.getPiece(col, row);
        if(piece != null && piece.isWhite == gp.turn) {
            if(gp.selectedPiece == null) {
                gp.selectedPiece = piece;
            }
            else if(gp.selectedPiece == piece) {
                releasedCurrentPiece = true;
            }
            else {
                gp.selectedPiece = piece;
            }
        }
        else {
            gp.selectedPiece = null;
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
                    gp.selectedPiece.col = col;
                    gp.selectedPiece.row = row;
                    gp.selectedPiece.x = gp.selectedPiece.col * board.tileSize;
                    gp.selectedPiece.y = gp.selectedPiece.row * board.tileSize;
                    gp.selectedPiece = null;

    //                gp.turn = !gp.turn;
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
}
