package com.ldgmms.game;

public interface Strategy {
    Movement scoreMovements(Enemy enemy, Board board, AllUnits allUnits);

    int attemptMovement(Board board, AllUnits allUnits, int currentRow, int currentCol, int targetRow, int targetCol, Boolean real, Enemy enemy);

    Move getNextMovement(Enemy enemy, Board board, AllUnits allUnits);

    int attemptAttack(Skills skillSet, int skillID, Board board, int targetID, int row, int col, int range, Boolean real, AllUnits allUnits);

    Attack scoreAttacks(AllUnits allUnits, Board board, int enemyID);
}
