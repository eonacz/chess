package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static view.Chessboard.chessComponents;

public class PawnChessComponent extends ChessComponent{
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private Image pawnImage;

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController clickController, int size, char name) {
        super(chessboardPoint, location, color, clickController, size);
        this.name = name;
        initiatePawnImage(color);
    }

    public ArrayList<ChessComponent> getCanMoves(){
        ArrayList<ChessComponent> zz =new ArrayList<>();
        ChessboardPoint z;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                z =new ChessboardPoint(i,j);
                if (canMoveTo(chessComponents,z)){
                    zz.add(chessComponents[i][j]);
                }
            }
        }
        return zz;
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        ChessColor color = getChessColor();
        if (color == ChessColor.BLACK){
            if (destination.getY() == source.getY() && destination.getX() - source.getX() == 1){
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                        return true;
                }
            }else if (source.getX() == 1 && destination.getY() == source.getY() && destination.getX() - source.getX() == 2){
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent && chessComponents[destination.getX()-1][destination.getY()] instanceof EmptySlotComponent){
                    return true;
                }
            }else if (Math.abs(destination.getY() - source.getY()) == 1 && destination.getX() - source.getX() == 1){
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)){
                    return true;
                }
            }
        }else if (color == ChessColor.WHITE){
            if (destination.getY() == source.getY() && destination.getX() - source.getX() == -1){
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                    return true;
                }
            }else if (source.getX() == 6 && destination.getY() == source.getY() && destination.getX() - source.getX() == -2){
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent && chessComponents[destination.getX()+1][destination.getY()] instanceof EmptySlotComponent){
                    return true;
                }
            }else if (Math.abs(destination.getY() - source.getY()) == 1 && destination.getX() - source.getX() == -1){
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("C:\\Users\\eonacz\\Documents\\GitHub\\chess\\src\\Image\\chess1-pawn.png"));
        }
        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("C:\\Users\\eonacz\\Documents\\GitHub\\chess\\src\\Image\\chess2-pawn.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13 , getHeight() - 20, this);
        g.drawImage(pawnImage, -getWidth()/4, -getHeight()/4, getWidth()*3/2, getHeight()*3/2, this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

}
