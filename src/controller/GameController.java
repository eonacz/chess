package controller;

import view.Chessboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    private Chessboard chessboard;
    private boolean fileType = true;
    private List<String> chessData;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        String type = path.substring(path.length()-3,path.length());
        if (!type.equals("txt")) {
            fileType = false;
        }
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            this.chessData = chessData;
            chessboard.loadGame(chessData,fileType);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isLoad(){
        return chessboard.loadGame(chessData,fileType);
    }

}
//C:\Users\13601\IdeaProjects\spring102a-22-3\chessProject\src\ChessGame1.txt
//C:\Users\13601\IdeaProjects\spring102a-22-3\chessProject\src\GameFrame\ChessGame1.txt