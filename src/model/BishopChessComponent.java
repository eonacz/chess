package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BishopChessComponent extends ChessComponent{

    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private Image bishopImage;



    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController clickController, int size, char name) {
        super(chessboardPoint, location, color, clickController, size);
        this.name = name;
        initiateBishopImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();int row = source.getX();int col = source.getY();
        if (source.getX()+source.getY() == destination.getX()+ destination.getY()){
            for (int i = Math.min(source.getX(), destination.getX())+1; i < Math.max(source.getX(), destination.getX()); i++) {
                if (!(chessComponents[i][row+col-i] instanceof EmptySlotComponent)){
                    return false;
                }
            }
        }else if (source.getX()- source.getY() == destination.getX()- destination.getY()){
            for (int i = Math.min(source.getX(), destination.getX())+1; i < Math.max(source.getX(), destination.getX()); i++) {
                if (!(chessComponents[i][i-row+col] instanceof EmptySlotComponent)){
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }

    @Override
    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null){
            BISHOP_WHITE = ImageIO.read(new File("C:\\Users\\eonacz\\Documents\\GitHub\\chess\\src\\Image\\chess1-bishop.png"));
        }
        if (BISHOP_BLACK == null){
            BISHOP_BLACK = ImageIO.read(new File("C:\\Users\\eonacz\\Documents\\GitHub\\chess\\src\\Image\\chess2-bishop.png"));
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, -getWidth()/2, -getHeight()/2, getWidth()*2, getHeight()*2, this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
