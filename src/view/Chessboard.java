package view;

import GameFrame.GamePage;
import controller.ClickController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.BLACK;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private int cnt = 0;//每新建一个游戏，cnt++，方便存档
    private int round = 0;
    private JLabel player = new JLabel("White's turn");


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK, 'R');
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK, 'R');
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE, 'r');
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE, 'r');

        initKingOnBoard(0, 4, ChessColor.BLACK, 'K');
        initKingOnBoard(7, 4, ChessColor.WHITE, 'k');

        initQueenOnBoard(0, 3, ChessColor.BLACK, 'Q');
        initQueenOnBoard(7, 3, ChessColor.WHITE, 'q');

        initBishopOnBoard(0, 2, ChessColor.BLACK, 'B');
        initBishopOnBoard(0, 5, ChessColor.BLACK, 'B');
        initBishopOnBoard(7, 2, ChessColor.WHITE, 'b');
        initBishopOnBoard(7, 5, ChessColor.WHITE, 'b');

        initKnightOnBoard(0, 1, ChessColor.BLACK, 'N');
        initKnightOnBoard(0, 6, ChessColor.BLACK, 'N');
        initKnightOnBoard(7, 1, ChessColor.WHITE, 'n');
        initKnightOnBoard(7, 6, ChessColor.WHITE, 'n');

        for (int i = 0; i < 8; i++) {
            initPawnOnBoard(1, i, ChessColor.BLACK, 'P');
            initPawnOnBoard(6, i, ChessColor.WHITE, 'p');
        }
    }

    public Chessboard(int width, int height, List<String> ChessData) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;//新构造器，用于存档
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        for (int i = 0; i < ChessData.size() - 1; i++) {
            for (int j = 0; j < 8; j++) {
                switch (ChessData.get(i).charAt(j)) {
                    case 'K': {
                        initKingOnBoard(i, j, ChessColor.BLACK, 'K');
                        break;
                    }
                    case 'k': {
                        initKingOnBoard(i, j, ChessColor.WHITE, 'k');
                        break;
                    }
                    case 'Q': {
                        initQueenOnBoard(i, j, ChessColor.BLACK, 'Q');
                        break;
                    }
                    case 'q': {
                        initQueenOnBoard(i, j, ChessColor.WHITE, 'q');
                        break;
                    }
                    case 'B': {
                        initBishopOnBoard(i, j, ChessColor.BLACK, 'B');
                        break;
                    }
                    case 'b': {
                        initBishopOnBoard(i, j, ChessColor.WHITE, 'b');
                        break;
                    }
                    case 'N': {
                        initKnightOnBoard(i, j, ChessColor.BLACK, 'N');
                        break;
                    }
                    case 'n': {
                        initKnightOnBoard(i, j, ChessColor.WHITE, 'n');
                        break;
                    }
                    case 'R': {
                        initRookOnBoard(i, j, ChessColor.BLACK, 'R');
                        break;
                    }
                    case 'r': {
                        initRookOnBoard(i, j, ChessColor.WHITE, 'r');
                        break;
                    }
                    case 'P': {
                        initPawnOnBoard(i, j, ChessColor.BLACK, 'P');
                        break;
                    }
                    case 'p': {
                        initPawnOnBoard(i, j, ChessColor.WHITE, 'p');
                        break;
                    }
                    case '_': {
                        initiateEmptyChessboard(i, j);
                        break;
                    }
                }
            }
        }
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {//规则
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE, '_'));
        }

        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
        round++;
    }

    public int getRound() {
        return round;
    }




    public boolean check(){
        for (int i = 0; i < 8; i++) {

        }
    }



    private boolean ischecking1(ChessComponent chess1, ChessComponent chess2) {//判断将军的函数1
        if (chess2 != null && (chess2.toString() == "K") || (chess2.toString() == "k")) {//找到王
            if (chess1 != null && chess1.getChessColor() != chess2.getChessColor()
                    && chess1.getChessColor() == currentColor
                    && chess1.canMoveTo(chessComponents, chess2.getChessboardPoint()))
                return true;
        }
        return false;
    }

    private boolean ischecking2(ChessComponent chess1, ChessComponent chess2) {//判断将军的函数2
        if (chess2 != null && (chess2.toString() == "K") || (chess2.toString() == "k")) {//找到王
            if (chess1 != null && chess1.getChessColor() != chess2.getChessColor()
                    && chess1.getChessColor() != currentColor
                    && chess1.canMoveTo(chessComponents, chess2.getChessboardPoint()))
                return true;
        }
        return false;
    }






    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE, '_'));
            }
        }
    }//棋盘重置，全空

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        if (currentColor == ChessColor.BLACK){
            player.setText("Black's turn!");
        }else{
            player.setText("White's turn!");
        }
    }

    public JLabel getPlayer() {
        return player;
    }

    public void initiateEmptyChessboard(int row, int col) {
        ChessComponent chessComponent = new EmptySlotComponent(new ChessboardPoint(row, col), calculatePoint(row, col), clickController, CHESS_SIZE, '_');
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }//摆新棋子，及以下

    private void initRookOnBoard(int row, int col, ChessColor color, char name) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color, char name) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color, char name) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color, char name) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color, char name) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color, char name) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public String getChessboardGraph() {//存档，as5
        StringBuilder[] string1 = new StringBuilder[8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (j == 0) {
                    string1[i] = new StringBuilder();
                }
                string1[i].append(chessComponents[i][j].toString());
            }
        }
        return string1[0].toString() + "\n" + string1[1].toString() + "\n" + string1[2].toString() + "\n"
                + string1[3].toString() + "\n" + string1[4].toString() + "\n" + string1[5].toString() + "\n"
                + string1[6].toString() + "\n" + string1[7].toString();
    }

    public File createFile(int cnt) {
        boolean flag = false;
        File file = null;
        try {
            do {
                String filename = "C:\\Users\\13601\\IdeaProjects\\spring102a-22-3\\chessProject\\src\\ChessGame" + cnt + ".txt";
                file = new File(filename);
                cnt++;
            } while (file.exists());
            file.createNewFile();
            flag = true;
            /**if (!file.exists()){
             file.createNewFile();
             flag=true;
             }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public void saveGame(int cnt) throws IOException {
        FileWriter file = new FileWriter(createFile(cnt));
        BufferedWriter bufferedWriter = new BufferedWriter(file);
        bufferedWriter.write(getChessboardGraph() + "\n");
        if (currentColor == ChessColor.BLACK) {
            bufferedWriter.write('b');
        } else {
            bufferedWriter.write('w');
        }
        bufferedWriter.flush();
        bufferedWriter.close();
        file.close();
    }


    @Override
    protected void paintComponent(Graphics g) {//画棋子
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {//？
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public boolean loadGame(List<String> chessData, boolean fileType) {//load方法体
        boolean isLoad = true;
        if (fileType == false) {
            isLoad = false;
        } else {
            if (chessData.size() != 9) {
                isLoad = false;
            } else {
                for (int i = 0; i < chessData.size() - 1; i++) {
                    if (chessData.get(i).length() != 8) {
                        isLoad = false;
                        break;
                    }
                    for (int j = 0; j < chessData.get(i).length(); j++) {
                        char m = chessData.get(i).charAt(j);
                        if (!(m == 'K' || m == 'k' || m == 'Q' || m == 'q' || m == 'B' || m == 'b' || m == 'R' || m == 'r' || m == 'N' || m == 'n' || m == 'P' || m == 'p' || m == '_')) {
                            isLoad = false;
                        }
                    }
                }
                char m = chessData.get(8).charAt(0);
                if (!(m == 'w' || m == 'b')) {
                    isLoad = false;
                }
            }
        }
        if (isLoad) {
            new GamePage(chessData);
            chessData.forEach(System.out::println);
        }
        return isLoad;
    }

}
