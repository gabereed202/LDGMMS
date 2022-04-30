package com.ldgmms.game;

public class Movement extends Move{
    int currentRow;
    int currentCol;
    int targetRow;
    int targetCol;
    int score;

    public Movement(int currentRow, int currentCol, int targetRow, int targetCol){
        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.targetRow = targetRow;
        this.targetCol = targetCol;
    }
    public void movement(Board board){
        if (Move.checkValidMovement(board, this.currentCol, this.currentRow, this.targetCol, this.targetRow)){
            board.playerMap[this.targetRow][this.targetCol] = board.playerMap[this.currentRow][this.currentCol];
            board.playerMap[this.currentRow][this.currentCol] = 0;
        }
        return;
    }

    public Movement moveTowardsSpace(int targetRow, int targetCol, int currentRow, int currentCol, Board board){

        int endCol = currentCol;
        int endRow = currentRow;
        int range = 2;
        while (range > 0){
            if (targetCol > endCol){
                endCol++;
            }
            else if (targetCol < endCol){
                endCol--;
            }
            if (targetRow > endRow){
                endRow++;
            }
            else if (targetRow < endRow){
                endRow--;
            }
            range--;
        }
        Movement movement = new Movement(currentRow, currentCol, endRow, endCol);
        return movement;
    }

}
