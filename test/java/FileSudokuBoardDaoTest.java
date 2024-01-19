import org.example.*;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileSudokuBoardDaoTest {


    @Test
    void ioTest() {
        SudokuBoard boardOut = Repository.getBoardInstance();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardOut.set(i,j,j);
            }
        }


        // try-with-resources
        try (FileSudokuBoardDao file = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao("board.txt")) {

            file.write(boardOut);
        } catch (Exception e) {
            System.out.println("Failure");
        }

        try (FileSudokuBoardDao file = (FileSudokuBoardDao) SudokuBoardDaoFactory.getFileDao("board.txt")) {

            SudokuBoard boardIn = file.read();
            boolean flag = boardIn.equals(boardOut);
            assertTrue(flag);
        } catch (Exception e) {
            System.out.println("Failure");
        }

    }

    @Test
    void readFailureTest() {

        Dao<SudokuBoard> file = SudokuBoardDaoFactory.getFileDao("cos");
        Throwable exception = assertThrows(RuntimeException.class, file::read);
        ResourceBundle bundle = ResourceBundle.getBundle("MyBundleException_en");
        assertEquals(bundle.getString("io"),
                exception.getMessage());
    }
}
