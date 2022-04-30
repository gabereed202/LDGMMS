package com.ldgmms.game;

import java.util.Set;

public class HardMelee implements Strategy{
    /*

     */
    @Override
    public int attemptAttack(Skills skillSet, int skillID, Board board, int targetID, int row, int col, int range, Boolean real, AllUnits allUnits){
        // first check if the opponent is in range
        int score = 0;
        Attack attack = new Attack(null, null);
        if (board.checkOpponentInRange(row, col, targetID, range)){

            Hero target = allUnits.heroMap.get(targetID);
            SkillData skill = skillSet.map.get(skillID);
            attack.target = target;
            attack.skill = skill;
            int targetCurrentHealth = (int) target.health;
            int targetHealth = attack.enemyAttack(real);
            if (targetHealth <= 0) {
                score = 100 - targetHealth;
                score += targetCurrentHealth - target.getHealth();
                return score * 5;
            }
            score = 100 - targetHealth;
        }
        return score;
    }

    /*
    This takes a current board state and a particular enemy and grades
    all the possible attacks they can make against all enemies that are in
    range. The highest scoring attack will be returned so the enemy can make it.

        Attack bestMove;
        for all possible attacks{
            for all possible targets of current attack{
                check score of attack
                if score is higher than previous high score{
                    bestAttack = currentAttack
                }
            }
        }
     */

    @Override
    public Attack scoreAttacks(AllUnits allUnits, Board board, int enemyID){

        Enemy enemy = allUnits.enemyMap.get(enemyID);
        int[][] availableTargets = Move.getPotentialTargetCoordinates(board);
        Set<Integer> keySet = enemy.baseSkills.map.keySet();
        int highScore = 0;
        int tempScore;
        Attack bestAttack = new Attack(null, null);
        bestAttack.score = 0;

        for (int ii: keySet) {

            availableTargets = Move.filterOutImpossibleTargets(board, availableTargets, enemy.baseSkills.map.get(ii).getRange(),
                    enemy.yPosition, enemy.xPosition);

            for (int jj = 0; jj < availableTargets.length; jj++) {

                int targetID = board.playerMap[availableTargets[jj][0]][availableTargets[jj][1]];
                tempScore = this.attemptAttack(enemy.baseSkills, ii, board, targetID,
                        enemy.yPosition, enemy.xPosition, enemy.baseSkills.map.get(jj).getRange(), false, allUnits);

                if (tempScore > highScore) {
                    highScore = tempScore;
                    bestAttack.skill = enemy.baseSkills.map.get(ii);
                    bestAttack.target = allUnits.heroMap.get(targetID);
                    bestAttack.score = highScore;
                }
            }
        }
        return bestAttack;
    }
    @Override
    public Movement scoreMovements(Enemy enemy, Board board, AllUnits allUnits){
        int bestScore = 0;
        int tempScore;
        Movement bestMovement = new Movement(enemy.yPosition, enemy.xPosition, enemy.yPosition, enemy.xPosition);
        bestMovement.score = 0;

        for (int ii = 0; ii < board.playerMap.length; ii++){
            for (int jj = 0; jj < board.playerMap[ii].length; jj++){
                tempScore = attemptMovement(board, allUnits, enemy.yPosition, enemy.xPosition, ii, jj, false, enemy);
                if (tempScore > bestScore){
                    bestScore = tempScore;
                    bestMovement.targetCol = jj;
                    bestMovement.targetRow = ii;
                    bestMovement.currentCol = enemy.xPosition;
                    bestMovement.currentRow = enemy.yPosition;
                    bestMovement.score = bestScore;
                }
            }
        }
        return bestMovement;
    }

    @Override
    public int attemptMovement(Board board, AllUnits allUnits, int currentRow, int currentCol, int targetRow, int targetCol, Boolean real, Enemy enemy){

        int score = 0;
        if (Move.checkValidMovement(board, currentCol, currentRow, targetCol, targetRow)){
            // checking for an item in the spot they can move to
            if (board.itemMap[targetRow][targetCol] != 0) {
                if ( enemy.health < 25){
                    score += 1;
                }
                if (enemy.health < 10){
                    score += 1;
                }
                score += 1;
            }
            if (enemy.health < 10){

                // moving away from opponents is better
                if (board.checkOpponentInRange(targetRow, targetCol, 1, 0)){
                    score -= 3;
                }
                else if (board.checkOpponentInRange(targetRow, targetCol, 1, 1)){
                    score -= 2;
                }
                else if (board.checkOpponentInRange(targetRow, targetCol, 1, 2)){
                    score -= 1;
                }
                else if (!board.checkOpponentInRange(targetRow, targetCol, 1, 3)){
                    score += 2;
                }

                // these are checking if there are other enemies nearby to help protect them
                // being closer to allies will help an injured enemy more than a healthier one
                if (board.checkOpponentInRange(targetRow, targetCol, -1, 0)){
                    score += 3;
                }
                else if (board.checkOpponentInRange(targetRow, targetCol, -1, 1)){
                    score += 2;
                }
                else if (board.checkOpponentInRange(targetRow, targetCol, -1, 2)){
                    score += 1;
                }
                else if (!board.checkOpponentInRange(targetRow, targetCol, -1, 3)){
                    score -= 2;
                }


            }
            else{
                // moving towards opponent to attack
                if (board.checkOpponentInRange(targetRow, targetCol, 1, 0)){
                    score += 3;
                }
                else if (board.checkOpponentInRange(targetRow, targetCol, 1, 1)){
                    score += 2;
                }
                else if (!board.checkOpponentInRange(targetRow, targetCol, 1, 3)){
                    score -= 1;
                }

                // checking if the space is close to other enemies, better for strategy to be near allies
                if (board.checkOpponentInRange(targetRow, targetCol, -1, 0)){
                    score += 2;
                }
                else if (board.checkOpponentInRange(targetRow, targetCol, -1, 1)){
                    score += 1;
                }
            }
        }
        else{
            // cannot move here, this should never be picked
            return -10000;
        }
        return score;
    }



    @Override
    public Move getNextMovement(Enemy enemy, Board board, AllUnits allUnits) {

        Attack attack = scoreAttacks(allUnits, board, enemy.ID);
        Movement movement = scoreMovements(enemy, board, allUnits);
        if(enemy.health >= 25) {
            if (attack.score >= movement.score){
                return attack;
            }
            else {
                // this calls a function that returns a movement that is actually attainable, scoreMovements calculates the best space on the entire board
                return movement.moveTowardsSpace(movement.targetRow, movement.targetCol, movement.currentRow, movement.currentCol, board);
            }
        }
        else{
            if (attack.score > movement.score){
                return attack;
            }
            else{
                return movement.moveTowardsSpace(movement.targetRow, movement.targetCol, movement.currentRow, movement.currentCol, board);
            }
        }
    }
}
