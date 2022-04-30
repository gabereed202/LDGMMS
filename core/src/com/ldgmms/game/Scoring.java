//package com.ldgmms.game;

//import java.util.Set;
/*
public class Scoring {


        This function takes a skillset, a skill id, an opposing player's id, a board state, the location of the
        enemy attempting the attack, the range of the attack, whether the move should be counted or not, and
        a hashmap of all the units in the game at this point. It uses this data to evaluate the value of that
        attack. If the attack kills a unit that move is rated much higher, otherwise most moves are evaluated by
        the remaining health of its target. i.e. a resulting the targets health

    public static int attemptAttack(Skills skillSet, int skillID, Board board, int targetID, int row, int col, int range, Boolean real, AllUnits allUnits, int diff){
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
                if (diff == 0){
                    return score;
                }
                return score * 5;
            }
            score = 100 - targetHealth;
        }
        return score;
    }


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

    public static Attack scoreAttacks(AllUnits allUnits, Board board, int enemyID, int diff){

        Enemy enemy = allUnits.enemyMap.get(enemyID);
        int[][] availableTargets = Move.getPotentialTargetCoordinates(board);
        Set<Integer> keySet = enemy.baseSkills.map.keySet();
        int highScore = 0;
        int tempScore;
        Attack bestAttack = new Attack(null, null);

        for (int ii: keySet) {

            availableTargets = Move.filterOutImpossibleTargets(board, availableTargets, enemy.baseSkills.map.get(ii).getRange(),
                    enemy.yPosition, enemy.xPosition);

            for (int jj = 0; jj < availableTargets.length; jj++) {

                int targetID = board.playerMap[availableTargets[jj][0]][availableTargets[jj][1]];
                tempScore = Move.attemptAttack(enemy.baseSkills, ii, board, targetID,
                        enemy.yPosition, enemy.xPosition, enemy.baseSkills.map.get(jj).getRange(), false, allUnits, diff);

                if (tempScore > highScore) {
                    highScore = tempScore;
                    bestAttack.skill = enemy.baseSkills.map.get(ii);
                    bestAttack.target = allUnits.heroMap.get(targetID);
                }
            }
        }
        return bestAttack;
    }

    public static Movement scoreMovements(){
        return null;

    }

}
*/
