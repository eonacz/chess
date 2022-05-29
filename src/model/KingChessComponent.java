package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static view.Chessboard.chessComponents;

public class KingChessComponent extends ChessComponent{
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;

    private void initiateKingImage(ChessColor color){
        try{
            loadResource();
            if (color == ChessColor.WHITE){
                kingImage = KING_WHITE;
            }else if (color == ChessColor.BLACK){
                kingImage = KING_BLACK;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController clickController, int size, char name) {
        super(chessboardPoint, location, color, clickController, size);
        this.name = name;
        initiateKingImage(color);
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
        if (Math.abs(source.getX()-destination.getX()) == 1 && Math.abs(source.getY()- destination.getY()) == 1){
            return true;
        }else if (destination.getX() == source.getX() && Math.abs(source.getY()- destination.getY()) == 1){
            return true;
        }else if (Math.abs(source.getX()-destination.getX()) == 1 && destination.getY() == source.getY()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("C:\\Users\\eonacz\\Documents\\GitHub\\chess\\src\\Image\\chess1-King.png"));
        }
        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("C:\\Users\\eonacz\\Documents\\GitHub\\chess\\src\\Image\\chess2-King.png"));
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, -getWidth()/2, -getHeight()/2, getWidth()*2, getHeight()*2, this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
