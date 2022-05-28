package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class QueenChessComponent extends ChessComponent{
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;
    private Image queenImage;

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size, char name) {
        super(chessboardPoint, location, color, listener, size);
        this.name = name;
        initiateQueenImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();//不用判断目标是否在棋盘内？
        if (source.getX() == destination.getX()) {//在同一列
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {//遍历棋子与目标位之间的空格
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {//如果中间有其他子，不能移动（车不可越子）
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }else if (source.getX()+source.getY() == destination.getX()+ destination.getY()) {
            for (int i = Math.min(source.getX(), destination.getX())+1; i < Math.max(source.getX(), destination.getX()); i++) {
                if (!(chessComponents[i][source.getX()+ source.getY()-i] instanceof EmptySlotComponent)){
                    return false;
                }
            }
        }else if (source.getX()- source.getY() == destination.getX()- destination.getY()){
            for (int i = Math.min(source.getX(), destination.getX())+1; i < Math.max(source.getX(), destination.getX()); i++) {
                if (!(chessComponents[i][i- source.getX()+ source.getY()] instanceof EmptySlotComponent)){
                    return false;
                }
            }
        }else {
            return false;
        }
          return true;
    }

    @Override
    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("C:\\Users\\13601\\IdeaProjects\\spring102a-22-3\\chessProject\\src\\Image\\chess1-Queen.png"));
        }
        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("C:\\Users\\13601\\IdeaProjects\\spring102a-22-3\\chessProject\\src\\Image\\chess2-queen.png"));
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, -getWidth()/2, -getHeight()/2, getWidth()*2 , getHeight()*2, this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

}
