package Engine;

import Pieces.Piece;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Images {
    private static BufferedImage whiteRook;
    private static BufferedImage whiteKnight;
    private static BufferedImage whiteBishop;
    //BufferedImage whiteKnight;
    private static BufferedImage whiteQueen;
    private static BufferedImage whiteKing;
    private static BufferedImage whitePawn;
    private static  BufferedImage blackRook;
    private static BufferedImage blackKnight;
    private static BufferedImage blackBishop;
    private static  BufferedImage blackQueen;
    private static BufferedImage blackKing;
    private static  BufferedImage blackPawn;
    private static  BufferedImage boardImg;

    public static BufferedImage getBoard() {
        return boardImg;
    }


    public static BufferedImage getImage(Piece p) {
        if (p.isWhite()) {
            return switch (p.getName()) {
                case "Rook" -> whiteRook;
                case "Knight" -> whiteKnight;
                case "Bishop" -> whiteBishop;
                case "Queen" -> whiteQueen;
                case "King" -> whiteKing;
                default -> whitePawn;
            };
        } else {
            return switch (p.getName()) {
                case "Rook" -> blackRook;
                case "Knight" -> blackKnight;
                case "Bishop" -> blackBishop;
                case "Queen" -> blackQueen;
                case "King" -> blackKing;
                default -> blackPawn;
            };
        }

    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static void loadAll() {


        try {
            whiteRook = ImageIO.read(new File("images/whiteRook.png"));
            whiteKnight = ImageIO.read(new File("images/whiteKnight.png"));
            whiteBishop = ImageIO.read(new File("images/whiteBishop.png"));
            whiteQueen = ImageIO.read(new File("images/whiteQueen.png"));
            whiteKing = ImageIO.read(new File("images/whiteKing.png"));
            whitePawn = ImageIO.read(new File("images/whitePawn.png"));
            blackRook = ImageIO.read(new File("images/blackRook.png"));
            blackKnight = ImageIO.read(new File("images/blackKnight.png"));
            blackBishop = ImageIO.read(new File("images/blackBishop.png"));
            blackQueen = ImageIO.read(new File("images/blackQueen.png"));
            blackKing = ImageIO.read(new File("images/blackKing.png"));
            blackPawn = ImageIO.read(new File("images/blackPawn.png"));
            boardImg = ImageIO.read(new File("images/board.png"));
            boardImg = resize(boardImg, 480, 480);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
