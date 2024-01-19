import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class JdbcTest {

    private static final Logger logger = LogManager.getLogger("model");

    @Test
    public void readWriteTest() {
        SudokuBoard board = Repository.getBoardInstance();
        board.solveGame();
        Level level = Level.EASY;
        level.digger(board);

        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getJdbcDao("boardTest1")) {
            dao.clearDataBase();
            dao.write(board);

            dao.setNameOfLoadedBoard("boardTest1");
            SudokuBoard result = dao.read();

            assertNotSame(board, result);
            boolean flag = board.equals(result);
            assertTrue(flag);

            dao.clearDataBase();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    public void getNamesTest() {
        SudokuBoard board = Repository.getBoardInstance();
        board.solveGame();
        Level level = Level.EASY;
        level.digger(board);

        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getJdbcDao("boardTest1")) {
            dao.write(board);
            ArrayList<String> result = dao.getNames();
            assertEquals("boardTest1", result.get(0));
            dao.clearDataBase();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

//    @Test
//    public void failureTest() {
//        SudokuBoard board = Repository.getBoardInstance();
//        board.solveGame();
//
//        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getJdbcDao("boardTest1")) {
//            dao.setNameOfLoadedBoard("BoardTestNull");
//            Throwable exception = assertThrows(RuntimeException.class, dao::read);
//
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//    }
}