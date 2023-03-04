package Pieces;
import Engine.Game;

public class Queen extends Piece{

    public Queen(int x, int y, boolean white, String name, Game game) {
        super(x,y,white,name, game);
    }


    @Override
    public void updateMoves() {
        this.moveList.clear();
        Piece rook  = new Rook(this.getX(), this.getY(), this.isWhite(), "Rook", this.getGame());
        Piece bishop  = new Bishop(this.getX(), this.getY(), this.isWhite(), "Bishop", this.getGame());
        rook.updateEnemyMoves();
        bishop.updateEnemyMoves();
        this.moveList.addAll(rook.getMoves());
        this.moveList.addAll(bishop.getMoves());
        removeDiscoveredChecks();
    }

    @Override
    public void updateEnemyMoves() {
        this.moveList.clear();
        Piece rook  = new Rook(this.getX(), this.getY(), this.isWhite(), "Rook", this.getGame());
        Piece bishop  = new Bishop(this.getX(), this.getY(), this.isWhite(), "Bishop", this.getGame());
        rook.updateEnemyMoves();
        bishop.updateEnemyMoves();
        this.moveList.addAll(rook.getMoves());
        this.moveList.addAll(bishop.getMoves());
    }


}