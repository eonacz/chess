package GameFrame;

import controller.GameController;
import view.Chessboard;

import javax.swing.*;
import java.awt.*;

import static java.awt.Image.SCALE_DEFAULT;

public class HomePage extends JFrame {
    private GameController gameController;
    static JFrame home = new JFrame("ChessGame");

    public HomePage(){
        home.setSize(420,630);
        home.setPreferredSize(new Dimension(420,630));
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setLayout(null);
        home.setLocationRelativeTo(null);

        ImageIcon background = new ImageIcon(HomePage.class.getResource("Let‘s Chess!2.png"));
        ((ImageIcon) background).setImage(background.getImage().getScaledInstance(420,594,SCALE_DEFAULT));
        JLabel bg = new JLabel();
        bg.setSize(420,594);
        bg.setIcon(background);
        bg.setHorizontalAlignment(SwingConstants.CENTER);
        home.add(bg);

        Container container = home.getContentPane();
        container.add(bg);
        container.setBackground(Color.orange);

        JButton newGame = new JButton("New Game");
        //ImageIcon button1 = new ImageIcon(HomePage.class.getResource("button2.png"));
        //button1.setImage(button1.getImage().getScaledInstance(168, 59, SCALE_DEFAULT));
        //newGame.setIcon(button1);
        //newGame.setContentAreaFilled(false);
        //newGame.setFocusPainted(false);
        newGame.setSize(150,70);
        newGame.setLocation(135,200);
        newGame.setFont(new Font("Rockwell", Font.BOLD, 18));
        newGame.setOpaque(true);

        home.add(newGame);
        newGame.addActionListener(e -> {
            System.out.println("Click new");
            closeThis();
            new GamePage();
        });


        JButton loadGame = new JButton("Load Game");
        loadGame.setSize(150,70);
        loadGame.setLocation(135,300);
        loadGame.setFont(new Font("Rockwell", Font.BOLD, 18));
        loadGame.setOpaque(true);
        home.add(loadGame);
        loadGame.addActionListener(e -> {
            Chessboard chessboard = new Chessboard(336, 336);
            gameController = new GameController(chessboard);
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            gameController.loadGameFromFile(path);
            if (!gameController.isLoad()){
                JDialog hint = new JDialog(home,"hint");
                Container container1 = hint.getContentPane();
                container1.add(new JLabel("存档格式错误！"));
                hint.setSize(100,80);
                hint.setVisible(true);
            }else{
                home.dispose();
            }
        });

        JButton exit = new JButton("Exit");
        exit.setSize(75,35);
        exit.setFont(new Font("Rockwell", Font.BOLD, 18));
        exit.setLocation(349/2,400);
        home.add(exit);
        exit.addActionListener(e -> {
            HomePage.closeThis();
        });


        home.setResizable(false);
        home.setVisible(true);

    }

    public static void closeThis(){
        home.dispose();
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
