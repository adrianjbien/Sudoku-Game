import org.example.Level;
import org.example.Repository;
import org.example.SudokuBoard;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LevelTest {
    @Test
    void setTest() {
        Level easy = Level.EASY;
        boolean flagEasy = false;


        boolean flagMedium = false;


        boolean flagHard = false;

        SudokuBoard board = Repository.getBoardInstance();
        board.solveGame();
        easy.digger(board);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i, j) == 0) {
                    flagEasy = true;
                    break;
                }
            }
        }

        Level medium = Level.MEDIUM;
        assertTrue(flagEasy);
        board.solveGame();
        medium.digger(board);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i, j) == 0) {
                    flagMedium = true;
                    break;
                }
            }
        }

        Level hard = Level.HARD;
        assertTrue(flagMedium);
        board.solveGame();
        hard.digger(board);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i, j) == 0) {
                    flagHard = true;
                    break;
                }
            }
        }

        assertTrue(flagHard);
    }
}
