package GameFrame;

import controller.GameController;
import view.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class GamePage {
    private int cnt = 0;//怎么让它存一次档次数就变换一次啊啊啊啊啊啊
    private GameController gameController;
    static JFrame game = new JFrame("ChessGame");
    private Chessboard chessboard;

    public GamePage(){
        game.setSize(420,630);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setLayout(null);
        game.setLocationRelativeTo(null);


        Container container = game.getContentPane();
        container.setBackground(Color.orange);

        addChessboard();
        game.setVisible(true);

        JButton reset = new JButton("Reset");
        reset.setSize(80,40);
        reset.setLocation(295,410);
        reset.setFont(new Font("Rockwell",Font.BOLD,16));
        container.add(reset);
        reset.addActionListener(e -> {
            System.out.println("Click Reset");
            game.getContentPane().removeAll();
            game.repaint();
            addChessboard();
            addUndo();
            addSave();
            game.add(reset);
            //game.revalidate();
        });
        addUndo();
        addSave();

        //JPanel round = new round(this);
        //game.add(round);

    }

    public GamePage(List chessData){
        game.setSize(420,630);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setLayout(null);
        game.setLocationRelativeTo(null);


        Container container = game.getContentPane();
        container.setBackground(Color.orange);

        addChessboard(chessData);
        game.setVisible(true);

        JButton reset = new JButton("Reset");
        reset.setSize(80,40);
        reset.setLocation(295,410);
        reset.setFont(new Font("Rockwell",Font.BOLD,16));
        container.add(reset);
        reset.addActionListener(e -> {
            System.out.println("Click Reset");
            game.getContentPane().removeAll();
            game.repaint();
            addChessboard();
            addUndo();
            addSave();
            game.add(reset);
            //game.revalidate();
        });
        addUndo();
        addSave();

        JLabel round = new JLabel("Round:"+chessboard.getRound()/2);

    }

    protected void processMouseEvent(MouseEvent e){

    }

    private void addChessboard() {
        Chessboard chessboard = new Chessboard(336, 336);
        gameController = new GameController(chessboard);
        chessboard.setLocation(35, 63);
        game.add(chessboard);
        this.chessboard = chessboard;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    private void addChessboard(List ChessData){
        Chessboard chessboard = new Chessboard(336, 336,ChessData);
        gameController = new GameController(chessboard);
        chessboard.setLocation(35,63);
        game.add(chessboard);
        this.chessboard = chessboard;
    }

    private void addUndo(){
        JButton undo = new JButton("Undo");
        undo.setSize(80,40);
        undo.setLocation(35,410);
        undo.setFont(new Font("Rockwell",Font.BOLD,16));
        game.add(undo);
    }

    public int getCnt() {
        return cnt;
    }

    private void addSave(){
        JButton save = new JButton("Save");
        save.setSize(80,40);
        save.setLocation(35,530);
        save.setFont(new Font("Rockwell",Font.BOLD,14));
        game.add(save);
        save.addActionListener(e -> {
            try {
                cnt++;
                chessboard.saveGame(getCnt());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            game.dispose();
        });
    }


}
