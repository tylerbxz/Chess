package Pieces;
import Engine.Game;
import Engine.Pair;

import java.util.ArrayList;
import java.util.Iterator;

public class King extends Piece{

    public King(int x, int y, boolean white, String name, Game game) {
        super(x,y,white,name, game);
        this.hasMoved = false;
    }

    @Override
    public void updateMoves() {

        this.moveList.clear();
        singleMoves();
        castlingMoves();
        removeDiscoveredChecks();

    }

    @Override
    public void updateEnemyMoves() {

        this.moveList.clear();
        singleMoves();
        castlingMoves();

    }

    private void singleMoves() {
        int[] dX = {1, 1, 1, -1, -1, -1, 0, 0};
        int[] dY = {1, -1, 0, 1, -1, 0, 1, -1};

        for (int i = 0; i < 8; i++) {
            int newX = this.getX() + dX[i];
            int newY = this.getY() + dY[i];
            Piece p = game.getPiece(newX, newY);

            if (newX > -1 && newX < 8 && newY > -1 && newY < 8 ) {
                if (p == null || p.isWhite() != this.isWhite()) {
                    Pair<Integer, Integer> pos = new Pair<>(newX, newY);
                    this.moveList.add(pos);
                }
            }
        }
    }
    @Override
    public void removeDiscoveredChecks () {
        int x = this.getX();
        int y = this.getY();
        Iterator<Pair<Integer, Integer>> it = this.moveList.iterator();

        while (it.hasNext()) {
            Pair<Integer, Integer> move = it.next();
            Piece enemy = game.getPiece(move.getL(), move.getR());

            // if king is taking an enemy piece remove it before checking for discovered check
            if (enemy != null && enemy.isWhite() != this.isWhite()) {
                if (enemy.isWhite()) {
                    game.whitePieces.remove(enemy);
                } else {
                    game.blackPieces.remove(enemy);
                }
            }

            this.setX(move.getL());
            this.setY(move.getR());
            ArrayList<Piece> attackingPieces = new ArrayList<>();

            attackingPieces = game.discoveredCheck(attackingPieces);
            for (Piece p : attackingPieces) {
                if (p != null && !(p.getX() == move.getL() && p.getY() == move.getR())) {
                    it.remove();
                    break;
                }
            }
            game.reAdd(attackingPieces);
            
            // add back the piece if it was removed
            if (enemy != null && enemy.isWhite() != this.isWhite()) {
                if (enemy.isWhite()) {
                    game.whitePieces.add(enemy);
                } else {
                    game.blackPieces.add(enemy);
                }
            }
        }
        this.setX(x);
        this.setY(y);
    }

    private void castlingMoves() {
        if (!hasMoved ) {
            //get corner pieces on back rank
            Piece left = game.getPiece(0, this.getY());
            Piece right = game.getPiece(7, this.getY());

            // if left rook hasn't moved
            if (left != null && !left.hasMoved()) {
                //if path is empty and does not go through check
                if (game.getPiece(3, this.getY()) == null && game.getPiece(2, this.getY()) == null
                        && game.isNotThroughCheck(-1)){
                    Pair<Integer, Integer> pos = new Pair<>(2, this.getY());
                    this.moveList.add(pos);
                }
            }

            if (right != null && !right.hasMoved()) {
                if (game.getPiece(5, this.getY()) == null && game.getPiece(6, this.getY()) == null
                        && game.isNotThroughCheck(1)){
                    Pair<Integer, Integer> pos = new Pair<>(6, this.getY());
                    this.moveList.add(pos);
                }
            }
        }
    }
}