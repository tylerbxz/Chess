package Pieces;

import Engine.Game;
import Engine.Pair;


public class Bishop extends Piece{
    public Bishop(int x, int y, boolean white, String name, Game game) {
        super(x, y, white, name, game);
    }

    @Override
    public void updateMoves() {
        this.moveList.clear();
        checkNWPath(true);
        checkNWPath(false);
        checkSWPath(true);
        checkSWPath(false);
        removeDiscoveredChecks();

    }

    @Override
    public void updateEnemyMoves() {
        this.moveList.clear();
        checkNWPath(true);
        checkNWPath(false);
        checkSWPath(true);
        checkSWPath(false);

    }

    // checks diagonal from bottom left to top right
    // takes boolean for checking both directions
    private void checkNWPath(boolean positive) {
        int x = this.getX();
        int y = this.getY();
        for (int i = 1; i < 8; i++) {
            if (positive) {
                x = x + 1;
                y = y - 1;
            } else {
                x = x - 1;
                y= y + 1;
            }
            if (x > -1 && x < 8 && y > -1 && y < 8) {
                Piece p = game.getPiece(x, y);
                if (p == null) {
                    // Add move if no piece
                    Pair<Integer, Integer> pos = new Pair<>(x, y);
                    this.moveList.add(pos);
                } else if (p.isWhite() != this.isWhite()) {
                    //if enemy piece add move then stop
                    Pair<Integer, Integer> pos = new Pair<>(x, y);
                    this.moveList.add(pos);
                    break;
                } else {
                    // if friendly piece then break

                    break;
                }
            } else {
                //if square not on board then break
                break;
            }
        }
    }

    // checks diagonal from top left to bottom right
    // takes boolean for checking both directions
    private void checkSWPath(boolean positive) {
        int x = this.getX();
        int y = this.getY();
        for (int i = 1; i < 8; i++) {
            if (positive) {
                x = x + 1;
                y = y + 1;
            } else {
                x = x - 1;
                y= y - 1;
            }
            if (x > -1 && x < 8 && y > -1 && y < 8) {
                Piece p = game.getPiece(x, y);
                if (p == null) {
                    Pair<Integer, Integer> pos = new Pair<>(x, y);
                    this.moveList.add(pos);
                } else if (p.isWhite() != this.isWhite()) {
                    Pair<Integer, Integer> pos = new Pair<>(x, y);
                    this.moveList.add(pos);
                    break;
                } else {
                    // if friendly piece then break
                    break;
                }
            } else {
                //if square not on board then break
                break;
            }
        }
    }

}
