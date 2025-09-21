package main;

import main.piece.Piece;

public class Move {
    int oldCol, oldRow;
    int newCol, newRow;

    Piece piece;
    Piece capturePiece;

    public Move(GamePanel gp, int newCol, int newRow) {
        this.piece = gp.selectedPiece;
        this.capturePiece = gp.getPiece(newCol, newRow);

        this.newCol = newCol;
        this.newRow = newRow;
        if(this.piece != null) {
            this.oldCol = piece.col;
            this.oldRow = piece.row;
        }

    }
}
