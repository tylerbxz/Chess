package Pieces;
import Engine.Game;
import Engine.Pair;


public class Pawn extends Piece{

    public Pawn(int x, int y, boolean white, String name, Game game) {
        super(x,y,white,name, game);
    }

    @Override
    public void updateMoves() {

        this.moveList.clear();
        baseMoves();
        attackMoves();
        enPassantMoves();
        removeDiscoveredChecks();

    }

    public void updateEnemyMoves() {

        this.moveList.clear();
        baseMoves();
        attackMoves();
        enPassantMoves();

    }

    private void baseMoves() {
        if (this.isWhite()) {
            // if square in front is empty then add the move
            if (game.getPiece(this.getX(), this.getY() - 1) == null ) {
                Pair<Integer, Integer> pos = new Pair<>(this.getX(), this.getY() - 1);
                this.moveList.add(pos);

                // Pawn hasn't moved and both squares in front are empty then add double move
                if (!hasMoved && game.getPiece(this.getX(), this.getY() - 2) == null) {
                    pos = new Pair<>(this.getX(), this.getY() - 2);
                    this.moveList.add(pos);
                }
            }

        } else {
            if (game.getPiece(this.getX(), this.getY() + 1) == null ) {
                Pair<Integer, Integer> pos = new Pair<>(this.getX(), this.getY() + 1);
                this.moveList.add(pos);

                if (!hasMoved && game.getPiece(this.getX(), this.getY() + 2) == null) {
                    pos = new Pair<>(this.getX(), this.getY() + 2);
                    this.moveList.add(pos);
                }
            }
        }
    }


    private void attackMoves() {
        Pair<Integer, Integer> pos;
        if (this.isWhite()) {
            // get both front diagonal squares
            Piece left = game.getPiece(this.getX() - 1, this.getY() - 1);
            Piece right = game.getPiece(this.getX() + 1, this.getY() - 1);

            if (left != null && left.isWhite() != this.isWhite()) {
                //if there is enemy piece then move is valid and added
                pos = new Pair<>(this.getX() - 1, this.getY() - 1);
                this.moveList.add(pos);
            }
            if (right != null && right.isWhite() != this.isWhite()) {
                pos = new Pair<>(this.getX() + 1, this.getY() - 1);
                this.moveList.add(pos);
            }
        } else {
            Piece left = game.getPiece(this.getX() - 1, this.getY() + 1);
            Piece right = game.getPiece(this.getX() + 1, this.getY() + 1);
            if (left != null && left.isWhite() != this.isWhite()) {
                pos = new Pair<>(this.getX() - 1, this.getY() + 1);
                this.moveList.add(pos);
            }
            if (right != null && right.isWhite() != this.isWhite()) {
                pos = new Pair<>(this.getX() + 1, this.getY() + 1);
                this.moveList.add(pos);
            }
        }
    }

    private void enPassantMoves() {
        Pair<Integer, Integer> pos;
        //get pieces directly next to this pawn
        Piece left = game.getPiece(this.getX() - 1, this.getY());
        Piece right = game.getPiece(this.getX() + 1, this.getY());


        if (this.isWhite()) {

            if (left != null && left.isWhite() != this.isWhite() && left.hasEnPassant()) {
                // if there is an enemy piece that just performed double move
                pos = new Pair<>(this.getX() - 1, this.getY() - 1);
                this.moveList.add(pos);
            }
            if (right != null && right.isWhite() != this.isWhite() && right.hasEnPassant()) {
                pos = new Pair<>(this.getX() + 1, this.getY() - 1);
                this.moveList.add(pos);
            }

        } else {

            if (left != null && left.isWhite() != this.isWhite() && left.hasEnPassant()) {
                pos = new Pair<>(this.getX() - 1, this.getY() + 1);
                this.moveList.add(pos);
            }
            if (right != null && right.isWhite() != this.isWhite() && right.hasEnPassant()) {
                pos = new Pair<>(this.getX() + 1, this.getY() + 1);
                this.moveList.add(pos);
            }

        }
    }


}
