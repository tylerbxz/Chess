package Pieces;
import  Engine.Game;
import  Engine.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public abstract class Piece {
    public int x;
    public int y;
    private final boolean white;
    public boolean hasMoved;
    public boolean enPassant;
    private final String name;

    public final Game game;

    public ArrayList<Pair<Integer, Integer>> moveList;


    public Piece(int x, int y, boolean white, String name, Game game) {
        this.white = white;
        this.x = x;
        this.y = y;
        this.name = name;
        this.game = game;
        this.hasMoved = false;
        this.enPassant = false;
        this.moveList = new ArrayList<>();
    }

    public ArrayList<Pair<Integer, Integer>> getMoves() {
        return moveList;
    }

    public void updateMoves() {

    }

    public void updateEnemyMoves() {

    }


    //Iterate through list of possible moves and remove ones that create check
    public void removeDiscoveredChecks () {
        int x = this.getX();
        int y = this.getY();
        Iterator<Pair<Integer, Integer>> it = this.moveList.iterator();

        while (it.hasNext()) {
            Pair<Integer, Integer> move = it.next();

            this.setX(move.getL());
            this.setY(move.getR());
            ArrayList<Piece> attackingPieces = new ArrayList<>();

            attackingPieces = game.discoveredCheck(attackingPieces);
            for (Piece p : attackingPieces) {
                if (!(p.getX() == move.getL() && p.getY() == move.getR())) {

                    it.remove();
                    break;
                }
            }
            game.reAdd(attackingPieces);
        }
        this.setX(x);
        this.setY(y);
    }


    public boolean containsPair(Pair<Integer, Integer> pair) {
        for (Pair<Integer, Integer> move : this.moveList) {
            if (Objects.equals(move.getL(), pair.getL()) && Objects.equals(move.getR(), pair.getR())) {
                return true;
            }
        }
        return false;
    }


    public Game getGame() {
        return this.game;
    }

    public int getX() {

        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return this.name;
    }

    public boolean isWhite() {
        return white;
    }
    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved() {
        hasMoved = true;
    }

    public boolean hasEnPassant() {
        return enPassant;
    }

    public void enPassantTrue() {
        enPassant = true;
    }
    public void enPassantFalse() {
        enPassant = false;
    }

}


