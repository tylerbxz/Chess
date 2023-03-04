package Pieces;

import Engine.Game;
import Engine.Pair;



public class Rook extends Piece{
    public Rook(int x, int y, boolean white, String name, Game game) {
        super(x,y,white,name, game);
        this.hasMoved = false;
    }


    @Override
    public void updateMoves() {
        this.moveList.clear();
        checkXPath(true);
        checkXPath(false);
        checkYPath(true);
        checkYPath(false);
        removeDiscoveredChecks();

    }

    public void updateEnemyMoves() {
        this.moveList.clear();
        checkXPath(true);
        checkXPath(false);
        checkYPath(true);
        checkYPath(false);


    }

    private void checkXPath(boolean positive) {
        int x = this.getX();
        for (int i = 1; i < 8; i++) {
            if (positive) {
                x = x + 1;
            } else {
                x = x - 1;
            }
            if (x > -1 && x < 8) {
                Piece p = game.getPiece(x, this.getY());
                if (p == null) {
                    Pair<Integer, Integer> pos = new Pair<>(x, this.getY());
                    this.moveList.add(pos);
                } else if (p.isWhite() != this.isWhite()) {
                    Pair<Integer, Integer> pos = new Pair<>(x, this.getY());
                    this.moveList.add(pos);
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }

    // takes boolean for checking positive or negative direction
    private void checkYPath(boolean positive) {
        int y = this.getY();
        for (int i = 1; i < 8; i++) {
            if (positive) {
                y = y + 1;
            } else {
                y = y - 1;
            }
            if (y > -1 && y < 8) {
                Piece p = game.getPiece(this.getX(), y);
                if (p == null) {
                    // Add move if no piece
                    Pair<Integer, Integer> pos = new Pair<>(this.getX(), y);
                    this.moveList.add(pos);
                } else if (p.isWhite() != this.isWhite()) {
                    //if enemy piece add move then stop
                    Pair<Integer, Integer> pos = new Pair<>(this.getX(), y);
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
