package Pieces;
import Engine.Game;
import Engine.Pair;



public class Knight extends Piece{

    public Knight(int x, int y, boolean white, String name, Game game) {
        super(x,y,white,name, game);
    }


    @Override
    public void updateMoves() {
        this.moveList.clear();
        allMoves();
        removeDiscoveredChecks();
    }

    @Override
    public void updateEnemyMoves() {
        this.moveList.clear();
        allMoves();
    }

    private void allMoves() {

        int[] dX = {2, 1, -1, -2, 2, 1, -1, -2};
        int[] dY = {1, 2, 2, 1, -1, -2, -2, -1};

        for (int i = 0; i < 8; i++) {
            int newX = this.getX() + dX[i];
            int newY = this.getY() + dY[i];
            Piece p = game.getPiece(newX, newY);

            if (newX > -1 && newX < 8 && newY > -1 && newY < 8 && (p == null || p.isWhite() != this.isWhite() )) {
                Pair<Integer, Integer> pos = new Pair<>(newX, newY);
                this.moveList.add(pos);
            }
        }
    }
}