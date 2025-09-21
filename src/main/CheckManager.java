package main;

import main.piece.*;

public class CheckManager {
    GamePanel gp;

    public CheckManager(GamePanel gp) {
        this.gp = gp;
    }

    public boolean gotChecked(Move move) {
        Piece king = gp.getKing(gp.turn);

        int kingCol = king.col;
        int kingRow = king.row;
        if(gp.selectedPiece != null && gp.selectedPiece.name.equals("king")) {
            kingCol = move.newCol;
            kingRow = move.newRow;
        }

        return checkedByRook   (king, move.newCol, move.newRow, kingCol, kingRow) ||
               checkedByBishop (king, move.newCol, move.newRow, kingCol, kingRow) ||
               checkedByKnight (king, move.newCol, move.newRow, kingCol, kingRow) ||
               checkByPawn     (king, move.newCol, move.newRow, kingCol, kingRow);
    }

    public boolean checkedByRook(Piece king, int newCol, int newRow, int kingCol, int kingRow) {
        return
           findRookAtDirection( 0,  1, king, newCol, newRow, kingCol, kingRow) ||
           findRookAtDirection( 1,  0, king, newCol, newRow, kingCol, kingRow) ||
           findRookAtDirection( 0, -1, king, newCol, newRow, kingCol, kingRow) ||
           findRookAtDirection(-1,  0, king, newCol, newRow, kingCol, kingRow);

    }

    public boolean checkedByBishop(Piece king, int newCol, int newRow, int kingCol, int kingRow) {
        return
           findBishopAtDirection( 1,  1, king, newCol, newRow, kingCol, kingRow) ||
           findBishopAtDirection(-1, -1, king, newCol, newRow, kingCol, kingRow) ||
           findBishopAtDirection( 1, -1, king, newCol, newRow, kingCol, kingRow) ||
           findBishopAtDirection(-1,  1, king, newCol, newRow, kingCol, kingRow);
    }

    public boolean checkedByKnight(Piece king, int newCol, int newRow, int kingCol, int kingRow) {
        return
           findKnightAt(kingCol - 2, kingRow - 1, king, newCol, newRow) ||
           findKnightAt(kingCol - 1, kingRow - 2, king, newCol, newRow) ||
           findKnightAt(kingCol - 2, kingRow + 1, king, newCol, newRow) ||
           findKnightAt(kingCol - 1, kingRow + 2, king, newCol, newRow) ||
           findKnightAt(kingCol + 2, kingRow - 1, king, newCol, newRow) ||
           findKnightAt(kingCol + 1, kingRow - 2, king, newCol, newRow) ||
           findKnightAt(kingCol + 2, kingRow + 1, king, newCol, newRow) ||
           findKnightAt(kingCol + 1, kingRow + 2, king, newCol, newRow);
    }

    public boolean checkByPawn(Piece king, int newCol, int newRow, int kingCol, int kingRow) {
        int direction = king.isWhite? -1 : 1;
        return findPawnAt(kingCol + 1, kingRow + direction, king, newCol, newRow) ||
               findPawnAt(kingCol - 1, kingRow + direction, king, newCol, newRow);
    }

    public boolean findPawnAt(int col, int row, Piece king, int newCol, int newRow) {
        Piece piece = gp.getPiece(col, row);
        return piece != null && !gp.sameTeam(piece, king) && piece.name.equals("pawn")
                && !(piece.col == newCol && piece.row == newRow);

    }

    private boolean findRookAtDirection(int dirX, int dirY, Piece king, int newCol,
        int newRow, int kingCol, int kingRow)
    {
        for(int i = 1;i < 8; i++) {
            if(kingCol + (i * dirX) == newCol && kingRow + (i * dirY) == newRow) {
                break;
            }

            Piece piece = gp.getPiece(kingCol + (i * dirX), kingRow + (i * dirY));
            if(piece != null && piece != gp.selectedPiece) {
                if(!gp.sameTeam(piece, king) && (piece.name.equals("rook") || piece.name.equals("queen"))) {
                    return true;
                }
                else
                    break;
            }
        }
        return false;
    }

    private boolean findBishopAtDirection(int dirX, int dirY, Piece king, int newCol,
        int newRow, int kingCol, int kingRow)
    {
        for(int i = 1;i < 8; i++) {
            if(kingCol + (i * dirX) == newCol && kingRow + (i * dirY) == newRow) {
                break;
            }

            Piece piece = gp.getPiece(kingCol + (i * dirX), kingRow + (i * dirY));
            if(piece != null && piece != gp.selectedPiece) {
                if(!gp.sameTeam(piece, king) && (piece.name.equals("bishop") || piece.name.equals("queen"))) {
                    return true;
                }
                else
                    break;
            }
        }
        return false;
    }

    public boolean findKnightAt(int col, int row, Piece king, int newCol, int newRow) {
        Piece piece = gp.getPiece(col, row);
        return piece != null && !gp.sameTeam(piece, king) && piece.name.equals("knight") &&
                    !(piece.col == newCol && piece.row == newRow);
    }
}
