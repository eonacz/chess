package model;

import controller.ClickController;
import view.ChessboardPoint;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static view.Chessboard.chessComponents;

/**
 * 这个类表示棋盘上的空位置
 */
public class EmptySlotComponent extends ChessComponent {

    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location, ClickController listener, int size, char name) {
        super(chessboardPoint, location, ChessColor.NONE, listener, size);
        this.name = name;
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
        return false;
    }

    @Override
    public void loadResource() throws IOException {
        //No resource!
    }

}
