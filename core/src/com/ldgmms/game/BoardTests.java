package com.ldgmms.game;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTests {
    // tests for "map" construction functions
    @Test
    public void testCorrectMapInit(){
        Board board = new Board();
        assertEquals(board.itemMap.length, board.playerMap.length);
        int col_size = board.playerMap.length;
        for (int i = 0; i < col_size; i++){
            assertEquals(board.itemMap[i].length, board.playerMap[i].length);
            int row_size = board.playerMap[i].length;
            for (int j = 0; j < row_size; j++){
                assertEquals(board.itemMap[i][j], 0);
                assertEquals(board.playerMap[i][j], 0);
            }
        }
    }
    @Test
    public void testItemAddition(){
        Board board = new Board();
        board.addItem(22, 1, 1);
        assertEquals(board.itemMap[1][1], 22);
    }
    @Test
    public void testItemRemoval(){
        Board board = new Board();
        board.addItem(22, 1, 1);
        assertEquals(board.itemMap[1][1], 22);
        board.removeItem(1, 1);
        assertEquals(board.itemMap[1][1], 0);
    }
    @Test
    public void testUnitAddition(){
        Board board = new Board();
        board.addUnit(22, 1, 1);
        assertEquals(board.playerMap[1][1], 22);
    }
    @Test
    public void testUnitRemoval(){
        Board board = new Board();
        board.addUnit(22, 1, 1);
        assertEquals(board.playerMap[1][1], 22);
        board.removeUnit(1, 1);
        assertEquals(board.playerMap[1][1], 0);
    }
    @Test
    public void testUnitPickUpItem(){
        Board board = new Board();
        board.addUnit(33, 1, 1);
        board.addItem(44, 1, 1);
        assertEquals(board.playerMap[1][1], 33);
        assertEquals(board.itemMap[1][1], 44);
        assertTrue(board.unitPickUpItem(1, 1));
        assertEquals(board.itemMap[1][1], 0);
    }

    // tests for the checkOpponentInRange
    @Test
    public void testNorthWestCorner(){
        Board board = new Board();
        board.addUnit(1, 0, 0);
        board.addUnit(-1, 0, 1);
        board.addUnit(-2, 1, 1);
        board.addUnit(-3, 1, 0);
        assertTrue(board.checkOpponentInRange(0, 0, -1, 0));
        board.removeUnit(0, 1);
        assertTrue(board.checkOpponentInRange(0, 0, -1, 0));
        board.removeUnit(1, 1);
        assertTrue(board.checkOpponentInRange(0, 0, -1, 0));
        board.removeUnit(1, 0);
        assertFalse(board.checkOpponentInRange(0, 0, -1, 0));
    }
    @Test
    public void testNorthEastCorner(){
        Board board = new Board();
        board.addUnit(1, 0, 9);
        board.addUnit(-1, 0, 8);
        board.addUnit(-2, 1, 8);
        board.addUnit(-3, 1, 9);
        assertTrue(board.checkOpponentInRange(0, 9, -1, 0));
        board.removeUnit(0, 8);
        assertTrue(board.checkOpponentInRange(0, 9, -1, 0));
        board.removeUnit(1, 8);
        assertTrue(board.checkOpponentInRange(0, 9, -1, 0));
        board.removeUnit(1, 9);
        assertFalse(board.checkOpponentInRange(0, 9, -1, 0));
    }
    @Test
    public void testSouthWestCorner(){
        Board board = new Board();
        board.addUnit(1, 9, 0);
        board.addUnit(-1, 9, 1);
        board.addUnit(-2, 8, 1);
        board.addUnit(-3, 8, 0);
        assertTrue(board.checkOpponentInRange(9, 0, -1, 0));
        board.removeUnit(9, 1);
        assertTrue(board.checkOpponentInRange(9, 0, -1, 0));
        board.removeUnit(8, 1);
        assertTrue(board.checkOpponentInRange(9, 0, -1, 0));
        board.removeUnit(8, 0);
        assertFalse(board.checkOpponentInRange(9, 0, -1, 0));
    }
    @Test
    public void testSouthEastCorner(){
        Board board = new Board();
        board.addUnit(1, 9, 9);
        board.addUnit(-1, 9, 8);
        board.addUnit(-2, 8, 8);
        board.addUnit(-3, 8, 9);
        assertTrue(board.checkOpponentInRange(9, 9, -1, 0));
        board.removeUnit(9, 8);
        assertTrue(board.checkOpponentInRange(9, 9, -1, 0));
        board.removeUnit(8, 8);
        assertTrue(board.checkOpponentInRange(9, 9, -1, 0));
        board.removeUnit(8, 9);
        assertFalse(board.checkOpponentInRange(9, 9, -1, 0));
    }
    @Test
    public void testNorth(){
        Board board = new Board();
        board.addUnit(1, 9, 1);
        board.addUnit(-1, 8, 1);
        assertTrue(board.checkOpponentInRange(9, 1, -1, 0));
        board.removeUnit(8, 1);
        assertFalse(board.checkOpponentInRange(9, 1, -1, 0));
    }
    @Test
    public void testSouth(){
        Board board = new Board();
        board.addUnit(1, 0, 5);
        board.addUnit(-1, 1, 5);
        assertTrue(board.checkOpponentInRange(0, 5, -1, 0));
        board.removeUnit(1, 5);
        assertFalse(board.checkOpponentInRange(0, 5, -1, 0));
    }
    @Test
    public void testEast(){
        Board board = new Board();
        board.addUnit(1, 1, 0);
        board.addUnit(-1, 1, 1);
        assertTrue(board.checkOpponentInRange(1, 0, -1, 0));
        board.removeUnit(1, 1);
        assertFalse(board.checkOpponentInRange(1, 0, -1, 0));
    }
    @Test
    public void testWest(){
        Board board = new Board();
        board.addUnit(1, 2, 9);
        board.addUnit(-1, 2, 8);
        assertTrue(board.checkOpponentInRange(2, 9, -1, 0));
        board.removeUnit(2, 8);
        assertFalse(board.checkOpponentInRange(2, 9, -1, 0));
    }
    @Test
    public void testNorthWest(){
        Board board = new Board();
        board.addUnit(1, 3, 3);
        board.addUnit(-1, 2, 2);
        assertTrue(board.checkOpponentInRange(3, 3, -1, 0));
        board.removeUnit(2, 2);
        assertFalse(board.checkOpponentInRange(3, 3, -1, 0));
    }
    @Test
    public void testNorthEast(){
        Board board = new Board();
        board.addUnit(1, 3, 3);
        board.addUnit(-1, 2, 4);
        assertTrue(board.checkOpponentInRange(3, 3, -1, 0));
        board.removeUnit(2, 4);
        assertFalse(board.checkOpponentInRange(3, 3, -1, 0));
    }
    @Test
    public void testSouthWest(){
        Board board = new Board();
        board.addUnit(1, 6, 6);
        board.addUnit(-1, 7, 5);
        assertTrue(board.checkOpponentInRange(6, 6, -1, 0));
        board.removeUnit(7, 5);
        assertFalse(board.checkOpponentInRange(6, 6, -1, 0));
    }
    @Test
    public void testSouthEast(){
        Board board = new Board();
        board.addUnit(1, 6, 6);
        board.addUnit(-1, 7, 7);
        assertTrue(board.checkOpponentInRange(6, 6, -1, 0));
        board.removeUnit(7, 7);
        assertFalse(board.checkOpponentInRange(6, 6, -1, 0));
    }
    @Test
    public void testOutOfLongRange(){
        Board board = new Board();
        board.addUnit(1, 0, 0);
        board.addUnit(-1, 9, 9);
        assertFalse(board.checkOpponentInRange(0, 0, -1, 7));
        board.removeUnit(9, 9);
        assertFalse(board.checkOpponentInRange(0, 0, -1, 8));
    }
    @Test
    public void testInLongWestRange(){
        Board board = new Board();
        board.addUnit(1, 3, 3);
        board.addUnit(-1, 3, 1);
        assertTrue(board.checkOpponentInRange(3, 3, -1, 1));
        assertFalse(board.checkOpponentInRange(3, 3, -1, 0));
        board.removeUnit(3, 1);
        assertFalse(board.checkOpponentInRange(3, 3, -1, 1));
    }
    @Test
    public void testInLongEastRange(){
        Board board = new Board();
        board.addUnit(1, 5, 3);
        board.addUnit(-1, 5, 6);
        assertTrue(board.checkOpponentInRange(5, 3, -1, 2));
        assertFalse(board.checkOpponentInRange(5, 3, -1, 1));
        board.removeUnit(5, 6);
        assertFalse(board.checkOpponentInRange(5, 3, -1, 2));
    }
    @Test
    public void testRangeAcrossMap1(){
        Board board = new Board();
        board.addUnit(1, 0, 0);
        board.addUnit(-1, 9, 9);
        assertTrue(board.checkOpponentInRange(0, 0, -1, 8));
        assertFalse(board.checkOpponentInRange(0, 0, -1, 7));
        board.removeUnit(9, 9);
        assertFalse(board.checkOpponentInRange(0, 0, -1, 8));
    }
    @Test
    public void testRangeAcrossMap2(){
        Board board = new Board();
        board.addUnit(1, 9, 0);
        board.addUnit(-1, 0, 9);
        assertTrue(board.checkOpponentInRange(9, 0, -1, 8));
        assertFalse(board.checkOpponentInRange(9, 0, -1, 7));
        board.removeUnit(0, 9);
        assertFalse(board.checkOpponentInRange(9, 0, -1, 8));
    }
    @Test
    public void testAllyReturnsFalse(){
        Board board = new Board();
        board.addUnit(1, 9, 0);
        board.addUnit(1, 0, 9);
        assertFalse(board.checkOpponentInRange(9, 0, -1, 8));
    }
    @Test
    public void testCheckMovementFalse(){
        Board board = new Board();
        board.addUnit(1, 0, 0);
        board.addUnit(-1, 1, 1);
        Move move = new Move();
        assertFalse(move.checkValidMovement(board, 1, 1, 0, 0));
    }
    @Test
    public void testCheckMovementTrue(){
        Board board = new Board();
        board.addUnit(-2, 0, 0);
        board.addUnit(-1, 1, 1);
        Move move = new Move();
        assertTrue(move.checkValidMovement(board, 0, 0, 2, 2));
    }
    @Test
    public void testCheckMovementFalseFriend(){
        Board board = new Board();
        board.addUnit(-2, 0, 0);
        board.addUnit(-1, 1, 1);
        Move move = new Move();
        assertFalse(move.checkValidMovement(board, 1, 1, 0, 0));
    }
}
