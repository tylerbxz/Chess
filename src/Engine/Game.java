package Engine;
import Pieces.*;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

import java.awt.event.*;


public class Game extends JComponent {
    public final ArrayList<Piece> whitePieces;
    public final ArrayList<Piece> blackPieces;
    // piece that is currently being moved
    private Piece selectedPiece;
    private int startX;
    private int startY;
    private boolean isWhiteTurn;
    //keep track of kings for potential checks
    private final Piece blackKing;
    private final Piece whiteKing;
    private boolean check;
    private boolean checkMate;
    //private ArrayList<Piece> attackingPieces;



    public Game() {
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        //attackingPieces = new ArrayList<>();
        selectedPiece = null;
        startX = 0;
        startY = 0;
        isWhiteTurn = true;
        blackKing = new King(4, 0, false, "King", this);
        whiteKing = new King(4, 7, true, "King", this);
        check = false;
        checkMate = false;

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
        this.setVisible(true);
        this.requestFocus();

        // Create white pieces
        whitePieces.add(whiteKing);
        whitePieces.add(new Rook(0, 7, true, "Rook", this));
        whitePieces.add(new Rook(7, 7, true, "Rook", this));
        whitePieces.add(new Bishop(5, 7, true, "Bishop", this));
        whitePieces.add(new Bishop(2, 7, true, "Bishop", this));
        whitePieces.add(new Knight(1, 7, true, "Knight", this));
        whitePieces.add(new Knight(6, 7, true, "Knight", this));
        whitePieces.add(new Queen(3, 7, true, "Queen", this));
        for (int i = 0; i < 8; i++) {
            whitePieces.add(new Pawn(i, 6, true, "Pawn", this));
        }

        //create black pieces
        blackPieces.add(blackKing);
        blackPieces.add(new Rook(0, 0, false, "Rook", this));
//        blackPieces.add(new Rook(7, 0, false, "Rook", this));
//        blackPieces.add(new Bishop(5, 0, false, "Bishop", this));
//        blackPieces.add(new Bishop(2, 0, false, "Bishop", this));
//        blackPieces.add(new Knight(1, 0, false, "Knight", this));
//        blackPieces.add(new Knight(6, 0, false, "Knight", this));
//        blackPieces.add(new Queen(3, 0, false, "Queen", this));
        for (int i = 0; i < 8; i++) {
            //blackPieces.add(new Pawn(i, 1, false, "Pawn", this));
        }

        // loads piece and board images
        Images.loadAll();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.gray);
        g.drawImage(Images.getBoard(), 100, 100, this);

        // flags if this piece is the selected piece and moving over a different piece
        boolean flag = false;

        for (Piece p : whitePieces) {
            if (p == selectedPiece && getNonSelectedPiece(p.getX(), p.getY()) != null) {
                 flag = true;
            } else {
                g.drawImage(Images.getImage(p), p.getX() * 60 + 100, p.getY() * 60 + 100, this);
            }
        }
        for (Piece p : blackPieces) {
            if (p == selectedPiece && getNonSelectedPiece(p.getX(), p.getY()) != null) {
                 flag = true;
            } else {
                g.drawImage(Images.getImage(p), p.getX() * 60 + 100, p.getY() * 60 + 100, this);
            }
        }

        if (flag) {
            //offsets active piece from other piece
            g.drawImage(Images.getImage(selectedPiece), selectedPiece.getX() * 60 + 105, selectedPiece.getY() * 60 + 105, this);
        }


        if (check) {
            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 30));
            if (checkMate) {
                this.removeMouseListener(mouseAdapter);
                this.removeMouseMotionListener(mouseAdapter);
                g.drawString("CheckMate!", 250, 50);
            } else {
                g.drawString("Check!", 290, 50);
            }
        }
    }

    final MouseAdapter mouseAdapter = new MouseAdapter() {


        @Override
        public void mousePressed(MouseEvent e) {
            int d_X = e.getX() - 100;
            int d_Y = e.getY() - 100;
            int row = d_Y / 60;
            int col = d_X / 60;

            if (d_X < 0 || d_Y < 0 || d_X > 480 || d_Y > 480) {
                return;
            }

            selectedPiece = getPiece(col, row);
            if (selectedPiece != null) {
                if (selectedPiece.isWhite() != isWhiteTurn){
                    selectedPiece = null;
                } else {
                    startX = col;
                    startY = row;
                    selectedPiece.updateMoves();
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selectedPiece == null) {
                return;
            }
            int d_X = e.getX() - 100;
            int d_Y = e.getY() - 100;
            int row = d_Y / 60;
            int col = d_X / 60;


            if (d_X < 0 || d_Y < 0 || d_X > 479 || d_Y > 479){
                return;
            }

            selectedPiece.setX(col);
            selectedPiece.setY(row);
            selectedPiece.getGame().repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // do nothing if no piece is selected
            if (selectedPiece == null) {
                return;
            }

            int dX = e.getX() - 100;
            int dY = e.getY() - 100;
            int y = dY / 60;
            int x = dX / 60;

            if (dX < 0 || dY < 0 || dX > 479 || dY > 479){
                selectedPiece.setX(startX);
                selectedPiece.setY(startY);
                selectedPiece.getGame().repaint();
                selectedPiece = null;
                return;
            }

            //Piece on square the moving piece attempts to move to
            Piece piece = getNonSelectedPiece(x, y);

            if (selectedPiece.containsPair(new Pair<>(x, y)) ) {
                //move is legal and does not threaten king
                //noinspection StatementWithEmptyBody
                if (piece == null ) {
                    //do nothing if moving into empty square
                } else if (piece.isWhite()) {
                    whitePieces.remove(piece);
                } else {
                    blackPieces.remove(piece);
                }

                //If piece hasn't moved yet then set hasMoved to true
                if (!selectedPiece.hasMoved() ) {
                    selectedPiece.setMoved();
                }

                castling();
                enPassant(piece);
                removeEnPassants();
                promotion();
                isWhiteTurn = !isWhiteTurn;
                isCheck();

            } else {
                //return to original square
                selectedPiece.setX(startX);
                selectedPiece.setY(startY);
            }

            selectedPiece.getGame().repaint();
            selectedPiece = null;
        }

    };




    private void castling() {
        if (selectedPiece.getName().equals("King") && Math.abs(selectedPiece.getX() - startX) == 2) {
            if (selectedPiece.getX() - startX > 0) {
                getPiece(7, selectedPiece.getY()).setX(5);
            } else if (selectedPiece.getX() - startX < 0){
                getPiece(0, selectedPiece.getY()).setX(3);
            }
        }

    }

    // Checks start square and neighboring square for check while castling
    public boolean isNotThroughCheck(int dX) {

        if (isWhiteTurn) {
            //starting pos of king
            Pair<Integer, Integer> pos1 = new Pair<>(whiteKing.getX(), whiteKing.getY());
            //left or right neighboring pos of king
            Pair<Integer, Integer> pos2 = new Pair<>(whiteKing.getX() + dX, whiteKing.getY());

            for (Piece p : blackPieces) {
                if(!p.getName().equals("King") || p.hasMoved()) {
                    p.updateEnemyMoves();
                    if (p.containsPair(pos1) || p.containsPair(pos2)) {
                        return false;
                    }
                }
            }
        } else {
            Pair<Integer, Integer> pos1 = new Pair<>(blackKing.getX(), blackKing.getY());
            Pair<Integer, Integer> pos2 = new Pair<>(blackKing.getX() + dX, blackKing.getY());
            for (Piece p : whitePieces) {
                if(!p.getName().equals("King") || p.hasMoved()) {
                    p.updateEnemyMoves();
                    if (p.containsPair(pos1) || p.containsPair(pos2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void enPassant(Piece p) {

        if (selectedPiece.getName().equals("Pawn")) {
            if (Math.abs(selectedPiece.getY() - startY) == 2) {
                //if pawn just moved 2 then it enables en Passant on this pawn
                selectedPiece.enPassantTrue();
            } else if (selectedPiece.getX() != startX && p == null) {
                // if pawn made diagonal move onto an empty square then perform en passant
                if (isWhiteTurn) {
                    if (selectedPiece.getX() - startX > 0) {
                        blackPieces.remove(getPiece(startX + 1, startY));
                    } else {
                        blackPieces.remove(getPiece(startX - 1, startY));
                    }
                } else {
                    if (selectedPiece.getX() - startX > 0) {
                        whitePieces.remove(getPiece(startX + 1, startY));
                    } else {
                        whitePieces.remove(getPiece(startX - 1, startY));
                    }
                }
            }
        }

    }

    public void promotion() {
        if (selectedPiece.getName().equals("Pawn") && (selectedPiece.getY() == 7 || selectedPiece.getY() == 0)) {
            if (isWhiteTurn) {
                whitePieces.remove(selectedPiece);
                selectedPiece = new Queen(selectedPiece.getX(),  selectedPiece.getY(), true, "Queen", selectedPiece.game);
                whitePieces.add(selectedPiece);
            } else {
                blackPieces.remove(selectedPiece);
                selectedPiece = new Queen(selectedPiece.getX(),  selectedPiece.getY(), false, "Queen", selectedPiece.game);
                blackPieces.add(selectedPiece);
            }
        }
    }

    private void removeEnPassants() {
        if (isWhiteTurn) {
            for (Piece p : blackPieces) {
                if (p.getName().equals("Pawn") && p.hasEnPassant()) {
                    p.enPassantFalse();
                }
            }
        } else {
            for (Piece p : whitePieces) {
                if (p.getName().equals("Pawn")) {
                    p.enPassantFalse();
                }
            }
        }
    }

    // returns piece on given square
    public Piece getPiece(int x, int y) {
        for (Piece p : whitePieces) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        for (Piece p : blackPieces) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        return null;
    }
    //the selected piece can temporarily share a square with another piece before move is checked
    //This returns the non-selected piece on given square
    public Piece getNonSelectedPiece(int x, int y) {

        for (Piece p : whitePieces) {
            if (p.getX() == x && p.getY() == y && p != selectedPiece) {
                return p;
            }
        }
        for (Piece p : blackPieces) {
            if (p.getX() == x && p.getY() == y && p != selectedPiece) {
                return p;
            }
        }
        return null;
    }

    private void isCheck() {
        check = false;
        if (isWhiteTurn) {
            Pair<Integer, Integer> pos = new Pair<>(whiteKing.getX(), whiteKing.getY());
            for (Piece p : blackPieces) {
                p.updateEnemyMoves();
                if (p.containsPair(pos)) {
                    check = true;
                    isCheckMate();
                    break;
                }
            }
        } else {
            Pair<Integer, Integer> pos = new Pair<>(blackKing.getX(), blackKing.getY());
            for (Piece p : whitePieces) {
                p.updateEnemyMoves();
                if (p.containsPair(pos)) {
                    check = true;
                    isCheckMate();
                    break;
                }
            }
        }
    }

    private void isCheckMate() {
        checkMate = true;
        if (isWhiteTurn) {
            for (Piece p : whitePieces) {
                p.updateMoves();
                if (!p.getMoves().isEmpty()) {
                    checkMate = false;
                }
            }
        } else {
            for (Piece p : blackPieces) {
                p.updateMoves();
                if (!p.getMoves().isEmpty()) {
                    checkMate = false;
                }
            }
        }
    }

    public ArrayList<Piece> discoveredCheck(ArrayList<Piece> attackingPieces) {
        if (isWhiteTurn) {
            Pair<Integer, Integer> pos = new Pair<>(whiteKing.getX(), whiteKing.getY());
            for (Piece p : blackPieces) {
                p.updateEnemyMoves();
                if (p.containsPair(pos)) {
                    attackingPieces.add(p);
                    blackPieces.remove(p);
                    discoveredCheck(attackingPieces);
                    break;
                }
            }
        } else {
            Pair<Integer, Integer> pos = new Pair<>(blackKing.getX(), blackKing.getY());
            for (Piece p : whitePieces) {
                p.updateEnemyMoves();
                if (p.containsPair(pos)) {
                    attackingPieces.add(p);
                    whitePieces.remove(p);
                    discoveredCheck(attackingPieces);
                    break;
                }
            }
        }
        return attackingPieces;
    }

    public void reAdd(ArrayList<Piece> attackingPieces) {
        for (Piece p : attackingPieces) {
            if (p.isWhite()) {
                whitePieces.add(p);
            } else {
                blackPieces.add(p);
            }
        }
    }

}