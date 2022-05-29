package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static view.Chessboard.chessComponents;

public class KnightChessComponent extends ChessComponent{

    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;
    private Image knightImage;

    private void initiateKnightImage(ChessColor color){
        try{
            loadResource();
            if (color == ChessColor.WHITE){
                knightImage = KNIGHT_WHITE;
            }else if (color == ChessColor.BLACK){
                knightImage = KNIGHT_BLACK;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController clickController, int size, char name) {
        super(chessboardPoint, location, color, clickController, size);
        this.name = name;
        initiateKnightImage(color);
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
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (Math.abs(source.getX()- destination.getX()) == 1 && Math.abs(source.getY()- destination.getY()) == 2){
            return true;
        }else if (Math.abs(source.getX()- destination.getX()) == 2 && Math.abs(source.getY()- destination.getY()) == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("C:\\Users\\eonacz\\Documents\\GitHub\\chess\\src\\Image\\chess1-knight.png"));
        }
        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("C:\\Users\\eonacz\\Documents\\GitHub\\chess\\src\\Image\\chess2-knight.png"));
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(knightImage, -getWidth()/2, -getHeight()/2, getWidth()*2, getHeight()*2, this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
