package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.ContainerElement;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.BattleBoard;
import model.BattleShipStageModel;
import model.Ship;

import java.awt.Point;
import java.util.*;

public class BattleShipDecider extends Decider {
    private int[][] gridP1;
    private int[][] gridP2;
    private Random random;
    private BattleShipStageModel battleShipStageModel;
    private BattleShipControler battleShipControler;
    private int id_Bot;
    private int levelBot;

    public BattleShipDecider(Model model, Controller control, int id, int level) {
        super(model, control);
        this.id_Bot = id;
        this.battleShipStageModel = (BattleShipStageModel) model.getGameStage();
        this.battleShipControler = (BattleShipControler) control;
        this.levelBot = level;
        gridP1 = battleShipControler.gridJ1;
        gridP2 = battleShipControler.gridJ2;
        random = new Random();
    }

    public void markMiss(Point p) {
        gridP1[p.y][p.x] = 2; // Mark as miss
    }
    public void markMiss2(Point p) {
        gridP2[p.y][p.x] = 2; // Mark as miss
    }

    public void markHit(Point p) {
        gridP1[p.y][p.x] = 3; // Mark as hit
        battleShipControler.hitJ1.add(p);
        updatePotentialTargets(p);
        battleShipControler.targetModeJ1 = true; // Switch to target mode
    }

    public void markHit2(Point p) {
        gridP2[p.y][p.x] = 3; // Mark as hit
        battleShipControler.hitJ2.add(p);
        updatePotentialTargets2(p);
        battleShipControler.targetModeJ2 = true; // Switch to target mode
    }

    public void markSunk(Point p) {
        markHit(p); // Mark the hit point
        for (Point hit : battleShipControler.hitJ1) {
            if (gridP1[hit.y][hit.x] == 3) {
                gridP1[hit.y][hit.x] = 4; // Mark as sunk
            }
        }
        battleShipControler.hitJ1.clear(); // Clear hits

        getSurroundingPoints(battleShipControler.shipPartJ1);
        battleShipControler.shipPartJ1.clear();

        battleShipControler.targetModeJ1 = false; // Switch back to random mode
        battleShipControler.lineModeJ1 = false; // Switch off-line mode
        battleShipControler.potentialTargetsJ1.clear(); // Clear potential targets
        battleShipControler.lineTargetsJ1.clear(); // Clear line targets
    }
    public void markSunk2(Point p) {
        markHit2(p); // Mark the hit point
        for (Point hit : battleShipControler.hitJ2) {
            if (gridP2[hit.y][hit.x] == 3) {
                gridP2[hit.y][hit.x] = 4; // Mark as sunk
            }
        }
        battleShipControler.hitJ2.clear(); // Clear hits

        getSurroundingPoints(battleShipControler.shipPartJ2);
        battleShipControler.shipPartJ2.clear();

        battleShipControler.targetModeJ2 = false; // Switch back to random mode
        battleShipControler.lineModeJ2 = false; // Switch off-line mode
        battleShipControler.potentialTargetsJ2.clear(); // Clear potential targets
        battleShipControler.lineTargetsJ2.clear(); // Clear line targets
    }

    private void updatePotentialTargets(Point p) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            Point adjacent = new Point(p.x + dir[0], p.y + dir[1]);
            if (isValidPosition(adjacent) && gridP1[adjacent.y][adjacent.x] == 0) {
                battleShipControler.potentialTargetsJ1.add(adjacent);
            }
        }
    }
    private void updatePotentialTargets2(Point p) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            Point adjacent = new Point(p.x + dir[0], p.y + dir[1]);
            if (isValidPosition(adjacent) && gridP2[adjacent.y][adjacent.x] == 0) {
                battleShipControler.potentialTargetsJ2.add(adjacent);
            }
        }
    }
    private Point getLineModeShot() {
        if (!battleShipControler.lineTargetsJ1.isEmpty()) {
            return battleShipControler.lineTargetsJ1.remove(0);
        } else {
            battleShipControler.lineModeJ1 = false;
            battleShipControler.targetModeJ1 = true; // Switch back to target mode
            return getTargetModeShot();
        }
    }
    private Point getLineModeShot2() {
        if (!battleShipControler.lineTargetsJ2.isEmpty()) {
            return battleShipControler.lineTargetsJ2.remove(0);
        } else {
            battleShipControler.lineModeJ2 = false;
            battleShipControler.targetModeJ2 = true; // Switch back to target mode
            return getTargetModeShot2();
        }
    }
    /*
    get next target permet de cibler un point sur la grille en fonction des modes de tirs
     */
    public Point getNextTarget() {
        if (battleShipControler.lineModeJ1) {
            return getLineModeShot(); // mode ligne
        }else if (battleShipControler.targetModeJ1) {
            return getTargetModeShot(); // mode target
        } else {
            Point randomTarget = getRandomTarget(); // mode random
            if (randomTarget != null) {
                return randomTarget;
            } else {
                return getRandomTarget();
            }
        }
    }

    public Point getNextTarget2() {
        if (battleShipControler.lineModeJ2) {
            return getLineModeShot2();
        }else if (battleShipControler.targetModeJ2) {
            return getTargetModeShot2();
        } else {
            Point randomTarget = getRandomTarget2();
            if (randomTarget != null) {
                return randomTarget;
            } else {
                return getRandomTarget2();
            }
        }
    }
    /*
    getRandomTarget permet d'obtenir un point random sur la grille
     */
    private Point getRandomTarget() {
        List<Point> availableTargets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gridP1[i][j] == 0) {
                    availableTargets.add(new Point(j, i));
                }
            }
        }
        if (availableTargets.isEmpty()) {
            return null;
        }
        return availableTargets.get(random.nextInt(availableTargets.size()));
    }
    private Point getRandomTarget2() {
        List<Point> availableTargets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gridP2[i][j] == 0) {
                    availableTargets.add(new Point(j, i));
                }
            }
        }
        if (availableTargets.isEmpty()) {
            return null;
        }
        return availableTargets.get(random.nextInt(availableTargets.size()));
    }
    /*
    getTargetModeShot permet d'obtenir un point selon le mode target
     */
    private Point getTargetModeShot() {
        if (!battleShipControler.potentialTargetsJ1.isEmpty()) {
            Point nextTarget = battleShipControler.potentialTargetsJ1.iterator().next();
            battleShipControler.potentialTargetsJ1.remove(nextTarget);
            return nextTarget;
        } else {
            battleShipControler.targetModeJ1 = false; // No potential targets, switch back to random mode
            return getRandomTarget();
        }
    }
    private Point getTargetModeShot2() {
        if (!battleShipControler.potentialTargetsJ2.isEmpty()) {
            Point nextTarget = battleShipControler.potentialTargetsJ2.iterator().next();
            battleShipControler.potentialTargetsJ2.remove(nextTarget);
            return nextTarget;
        } else {
            battleShipControler.targetModeJ2 = false; // No potential targets, switch back to random mode
            return getRandomTarget2();
        }
    }

    private boolean isValidPosition(Point p) {
        return p.x >= 0 && p.x < 10 && p.y >= 0 && p.y < 10;
    }

    public void printGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(gridP1[i][j] + " ");
            }
            System.out.println();
        }
    }
    public void pointSurLigneJ2(){
        Point lastHit = battleShipControler.hitJ2.get(battleShipControler.hitJ2.size() - 1);
        Point secondLastHit = battleShipControler.hitJ2.get(battleShipControler.hitJ2.size() - 2);
        if (lastHit.x == secondLastHit.x) {
            // Vertical line
            if (isValidPosition(new Point(lastHit.x, lastHit.y - 1)) && gridP2[lastHit.y - 1][lastHit.x] == 0) {
                battleShipControler.lineTargetsJ2.add(new Point(lastHit.x, lastHit.y - 1));
            }
            if (isValidPosition(new Point(lastHit.x, lastHit.y + 1)) && gridP2[lastHit.y + 1][lastHit.x] == 0) {
                battleShipControler.lineTargetsJ2.add(new Point(lastHit.x, lastHit.y + 1));
            }
        } else if (lastHit.y == secondLastHit.y) {
            // Horizontal line
            if (isValidPosition(new Point(lastHit.x - 1, lastHit.y)) && gridP2[lastHit.y][lastHit.x - 1] == 0) {
                battleShipControler.lineTargetsJ2.add(new Point(lastHit.x - 1, lastHit.y));
            }
            if (isValidPosition(new Point(lastHit.x + 1, lastHit.y)) && gridP2[lastHit.y][lastHit.x + 1] == 0) {
                battleShipControler.lineTargetsJ2.add(new Point(lastHit.x + 1, lastHit.y));
            }
        }
        if (!battleShipControler.lineTargetsJ2.isEmpty()) {
            battleShipControler.lineModeJ2 = true;
            battleShipControler.targetModeJ2 = false;
        }
    }
    public void pointSurLigneJ1(){
        Point lastHit = battleShipControler.hitJ1.get(battleShipControler.hitJ1.size() - 1);
        Point secondLastHit = battleShipControler.hitJ1.get(battleShipControler.hitJ1.size() - 2);
        if (lastHit.x == secondLastHit.x) {
            // Vertical line
            if (isValidPosition(new Point(lastHit.x, lastHit.y - 1)) && gridP1[lastHit.y - 1][lastHit.x] == 0) {
                battleShipControler.lineTargetsJ1.add(new Point(lastHit.x, lastHit.y - 1));
            }
            if (isValidPosition(new Point(lastHit.x, lastHit.y + 1)) && gridP1[lastHit.y + 1][lastHit.x] == 0) {
                battleShipControler.lineTargetsJ1.add(new Point(lastHit.x, lastHit.y + 1));
            }
        } else if (lastHit.y == secondLastHit.y) {
            // Horizontal line
            if (isValidPosition(new Point(lastHit.x - 1, lastHit.y)) && gridP1[lastHit.y][lastHit.x - 1] == 0) {
                battleShipControler.lineTargetsJ1.add(new Point(lastHit.x - 1, lastHit.y));
            }
            if (isValidPosition(new Point(lastHit.x + 1, lastHit.y)) && gridP1[lastHit.y][lastHit.x + 1] == 0) {
                battleShipControler.lineTargetsJ1.add(new Point(lastHit.x + 1, lastHit.y));
            }
        }

        if (!battleShipControler.lineTargetsJ1.isEmpty()) {
            battleShipControler.lineModeJ1 = true;
            battleShipControler.targetModeJ1 = false;
        }
    }

    @Override
    public ActionList decide() {
        BattleShipStageModel gameStage = (BattleShipStageModel) model.getGameStage();
        BattleBoard board;
        ContainerElement tire = null;
        GameElement missile = null;
        ActionList actions = null;

        String boardName;
        if (id_Bot == 0) {
            board = battleShipStageModel.getBoardPlayer1();
            System.out.println("boardP1");
            boardName = "boardplayer1";
        } else {
            board = battleShipStageModel.getBoardPlayer2();
            System.out.println("boardP2");
            boardName = "boardplayer2";
        }

        Point target;

        if (levelBot == 1){
            if (id_Bot == 0) {
                target = getNextTarget();
                tire = gameStage.getStockMissileJ1();
                missile = tire.getElement(0, 0);
            } else {
                target = getNextTarget2();
                tire = gameStage.getStockMissileJ2();
                missile = tire.getElement(0, 0);
            }
            int x = target.x;
            int y = target.y;

            // Generate the action
            actions = ActionFactory.generatePutInContainer(model, missile, boardName, y, x);
            actions.setDoEndOfTurn(true);
            boolean result;
            if (id_Bot == 1) {
                if (gameStage.toucheroupas(gameStage.getShipsPlayer1(), x, y)) {
                    for (int i = 0; i < gameStage.getMissileJoueur2().length; i++) {
                        if (gameStage.getMissileJoueur2()[i] == missile) {
                            gameStage.getMissileJoueur2()[i].setColor(2);
                        }
                    }
                    battleShipControler.tabCordMissileJ2[battleShipControler.numJ2][0] = x;
                    battleShipControler.tabCordMissileJ2[battleShipControler.numJ2][1] = y;
                    battleShipControler.numJ2++;
                }
                // Update grid based on the result
                result = gameStage.toucheroupas(gameStage.getShipsPlayer1(), x, y); // Implement this method
                if (!result) {
                    markMiss2(target);
                } else {
                    markHit2(target);
                    battleShipControler.shipPartJ2.add(target);
                    if (isSunk(gameStage.getShipsPlayer1(), x, y)) {
                        markSunk2(target);
                    } else if (battleShipControler.targetModeJ2 && battleShipControler.hitJ2.size() > 1) {
                        // Check if we can switch to line mode
                        pointSurLigneJ2();
                    }
                }

            } else {

                if (gameStage.toucheroupas(gameStage.getShipsPlayer2(), x, y)) {
                    for (int i = 0; i < gameStage.getMissileJoueur1().length; i++) {
                        if (gameStage.getMissileJoueur1()[i] == missile) {
                            gameStage.getMissileJoueur1()[i].setColor(2);
                        }
                    }
                    battleShipControler.tabCordMissileJ1[battleShipControler.numJ1][0] = x;
                    battleShipControler.tabCordMissileJ1[battleShipControler.numJ1][1] = y;
                    battleShipControler.numJ1++;
                }
                // Update grid based on the result
                result = gameStage.toucheroupas(gameStage.getShipsPlayer2(), x, y); // Implement this method
                if (!result) {

                    markMiss(target);
                } else {
                    markHit(target);
                    battleShipControler.shipPartJ1.add(target);
                    if (isSunk(gameStage.getShipsPlayer2(), x, y)) {
                        markSunk(target);
                    } else if (battleShipControler.targetModeJ1 && battleShipControler.hitJ1.size() > 1) {
                        printGrid();
                        // Check if we can switch to line mode
                        pointSurLigneJ1();
                    }
                }
            }
        } else {

            // LEVEL 2 DU BOT

            if (id_Bot == 0) {
                target = getNextTargetBot2J1();
                tire = gameStage.getStockMissileJ1();
                missile = tire.getElement(0, 0);
            } else {
                target = getNextTargetBot2J2();
                tire = gameStage.getStockMissileJ2();
                missile = tire.getElement(0, 0);
            }
            int X = target.x;
            int Y = target.y;
            // Generate the action
            actions = ActionFactory.generatePutInContainer(model, missile, boardName, Y, X);
            actions.setDoEndOfTurn(true);
            boolean result;
            if (id_Bot == 1) {
                if (gameStage.toucheroupas(gameStage.getShipsPlayer1(), X, Y)) {
                    for (int i = 0; i < gameStage.getMissileJoueur2().length; i++) {
                        if (gameStage.getMissileJoueur2()[i] == missile) {
                            gameStage.getMissileJoueur2()[i].setColor(2);
                        }
                    }
                    battleShipControler.tabCordMissileJ2[battleShipControler.numJ2][0] = X;
                    battleShipControler.tabCordMissileJ2[battleShipControler.numJ2][1] = Y;
                    battleShipControler.numJ2++;
                }
                // Update grid based on the result
                result = gameStage.toucheroupas(gameStage.getShipsPlayer1(), X, Y); // Implement this method
                if (!result) {
                    markMiss2(target);
                } else {
                    markHit2(target);
                    battleShipControler.shipPartJ2.add(target);
                    if (isSunk(gameStage.getShipsPlayer1(), X, Y)) {
                        markSunk2(target);
                    } else if (battleShipControler.targetModeJ2 && battleShipControler.hitJ2.size() > 1) {
                        // Check if we can switch to line mode
                        pointSurLigneJ2();
                    }
                }
            } else {
                if (gameStage.toucheroupas(gameStage.getShipsPlayer2(), X, Y)) {
                    for (int i = 0; i < gameStage.getMissileJoueur1().length; i++) {
                        if (gameStage.getMissileJoueur1()[i] == missile) {
                            gameStage.getMissileJoueur1()[i].setColor(2);
                        }
                    }
                    battleShipControler.tabCordMissileJ1[battleShipControler.numJ1][0] = X;
                    battleShipControler.tabCordMissileJ1[battleShipControler.numJ1][1] = Y;
                    battleShipControler.numJ1++;
                }
                // Update grid based on the result
                result = gameStage.toucheroupas(gameStage.getShipsPlayer2(), X, Y); // Implement this method
                if (!result) {
                    markMiss(target);
                } else {
                    markHit(target);
                    battleShipControler.shipPartJ1.add(target);
                    if (isSunk(gameStage.getShipsPlayer2(), X, Y)) {
                        markSunk(target);
                    } else if (battleShipControler.targetModeJ1 && battleShipControler.hitJ1.size() > 1) {
                        // Check if we can switch to line mode
                        pointSurLigneJ1();

                    }
                }
            }

        }

        return actions;

    }
    public Point getNextTargetBot2J1() {
        if (battleShipControler.lineModeJ1) {
            return getLineModeShot();
        }else if (battleShipControler.targetModeJ1) {
            return getTargetModeShot();
        } else {
            calculerGrilleProba(gridP1);
            Point probaTarget = trouverLaMeilleurProba();
            if (probaTarget != null) {
                return probaTarget;
            } else {
                return trouverLaMeilleurProba();
            }
        }
    }
    public Point getNextTargetBot2J2() {
        if (battleShipControler.lineModeJ2) {
            return getLineModeShot2();
        }else if (battleShipControler.targetModeJ2) {
            return getTargetModeShot2();
        } else {
            calculerGrilleProba(gridP2);
            Point probaTarget = trouverLaMeilleurProba2();
            if (probaTarget != null) {
                return probaTarget;
            } else {
                return trouverLaMeilleurProba2();
            }
        }
    }
    public boolean isSunk(Ship[] ships,int x, int y){
        int coordX;
        int coordY;
        for (Ship s :ships) {
            s.verifcouler();
            if (s.getcouler()){
                for (int i = 0;i<s.getTaille();i++){
                    coordX=s.getPartCordonneX(i);
                    coordY=s.getPartCordonneY(i);
                    if (coordX==x && coordY==y)
                        return true;
                }
            }
        }
        return false;
    }


    public static int[][] convertPointsToArray(List<Point> points) {
        int[][] array = new int[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                array[i][j] = 0;
            }
        }

        for (Point point : points) {
            int x = point.x;
            int y = point.y;
            array[x][y] = 1; // Marquer la case comme tirée
        }

        return array;
    }


    //===================================   niveau 2 Grille proba   ==================================


    private static final int GRID_SIZE = 10;
    private int[] SHIP_SIZES; // Liste de la taille des bateaux non-couler
    public int[][] grilleProba;

    public void getSurroundingPoints(List<Point> shipParts) {
        List<Point> surroundingPoints = new ArrayList<>();

        // Directions adjacentes et diagonales
        int[][] directions = { {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1} };

        // Parcourez chaque partie du bateau
        for (Point part : shipParts) {
            for (int[] dir : directions) {
                Point adjacent = new Point(part.x + dir[0], part.y + dir[1]);
                if (isValidPosition(adjacent) && !shipParts.contains(adjacent) && !surroundingPoints.contains(adjacent)) {
                    gridP1[adjacent.y][adjacent.x]=2;
                    surroundingPoints.add(adjacent);
                }
            }
        }
    }

    public void listeBateauxNonCoulerJ1(){
        SHIP_SIZES = new int[battleShipStageModel.ShipPlayer1.length];
        for (int i = 0; i<battleShipStageModel.ShipPlayer1.length;i++){
            battleShipStageModel.ShipPlayer1[i].verifcouler();
            if (!battleShipStageModel.ShipPlayer1[i].getcouler())
                SHIP_SIZES[i] = battleShipStageModel.ShipPlayer1[i].getTaille();
        }
    }
    public void listeBateauxNonCoulerJ2(){
        SHIP_SIZES = new int[battleShipStageModel.ShipPlayer2.length];
        for (int i = 0; i<battleShipStageModel.ShipPlayer2.length;i++){
            battleShipStageModel.ShipPlayer2[i].verifcouler();
            if (!battleShipStageModel.ShipPlayer2[i].getcouler())
                SHIP_SIZES[i] = battleShipStageModel.ShipPlayer2[i].getTaille();
        }
    }

    public void calculerGrilleProba(int[][] previousHitsGrid) {
        if (id_Bot==0)
            listeBateauxNonCoulerJ2(); // calculer la liste des tailles des bateaux non-couler du joueur 2
        else
            listeBateauxNonCoulerJ1(); // calculer la liste des tailles des bateaux non-couler du joueur 1
        System.out.println("SHIP_SIZE : ");
        for (int i = 0;i<SHIP_SIZES.length;i++)
            System.out.println(SHIP_SIZES[i]);
        // Reset the probability grid
        if (grilleProba == null) {
            grilleProba = new int[GRID_SIZE][GRID_SIZE];
        }
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (previousHitsGrid[i][j] != 0) {
                    grilleProba[i][j] = 0;
                } else {
                    grilleProba[i][j] = 1;
                } // Reset to 0 if a missile was already launched, otherwise set to 1
            }
        }
        // Calculate probabilities for each ship size
        for (int size : SHIP_SIZES) {
            calculerProbaParBateau(size, previousHitsGrid);
        }
    }

    private void calculerProbaParBateau(int size, int[][] previousHitsGrid) {
        // horizontal
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col <= GRID_SIZE - size; col++) {
                boolean canPlace = true;
                for (int k = 0; k < size; k++) {
                    if (previousHitsGrid[row][col + k] != 0) {
                        canPlace = false;
                        break;
                    }
                }
                if (canPlace) {
                    for (int k = 0; k < size; k++) {
                        grilleProba[row][col + k]++;
                    }
                }
            }
        }

        // vertical
        for (int col = 0; col < GRID_SIZE; col++) {
            for (int row = 0; row <= GRID_SIZE - size; row++) {
                boolean canPlace = true;
                for (int k = 0; k < size; k++) {
                    if (previousHitsGrid[row + k][col] != 0) {
                        canPlace = false;
                        break;
                    }
                }
                if (canPlace) {
                    for (int k = 0; k < size; k++) {
                        grilleProba[row + k][col]++;
                    }
                }
            }
        }
    }


    public Point trouverLaMeilleurProba() {
        int maxProbability = -1;
        Point bestCell = null;
        int x = 0;
        int y = 0;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grilleProba[row][col] > maxProbability && gridP1[row][col]==0) {
                    maxProbability = grilleProba[row][col];
                    x = col;
                    y = row;
                }
            }
        }
        bestCell = new Point(x,y);
        return bestCell;
    }
    public Point trouverLaMeilleurProba2() {
        int maxProbability = -1;
        Point bestCell = null;
        int x = 0;
        int y = 0;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grilleProba[row][col] > maxProbability && gridP2[row][col]==0) {
                    maxProbability = grilleProba[row][col];
                    x = col;
                    y = row;
                }
            }
        }
        bestCell = new Point(x,y);
        return bestCell;
    }

    //=========================== Méthode de placement =============================

    public int placeAllShips(int m) {
        if(battleShipStageModel.ShipPlayer1.length-1 == m){
            battleShipControler.count ++;
            if(battleShipControler.count == 2){battleShipControler.numJ1 = 0; battleShipControler.numJ2 = 0; battleShipControler.tabCordMissileJ1 = new int[battleShipStageModel.getMissileJoueur1().length][2]; battleShipControler.tabCordMissileJ2 = new int[battleShipStageModel.getMissileJoueur2().length][2];}
        }
        if (id_Bot == 0) {
            int taille = battleShipStageModel.ShipPlayer1[m].getTaille();
            placeShip(battleShipStageModel.ShipPlayer1[m], taille, battleShipStageModel.ShipPlayer1, m);
        } else if (id_Bot == 1) {
            int taille = battleShipStageModel.ShipPlayer2[m].getTaille();
            placeShip(battleShipStageModel.ShipPlayer2[m], taille, battleShipStageModel.ShipPlayer2, m);
        }
        if (battleShipStageModel.ShipPlayer2.length - 1 == m) {
            return 1;
        } else {
            return 0;
        }

    }

    private void placeShip(Ship bateau, int taille, Ship[] ship,int m) {
        random = new Random();
        int x;
        int y;
        char sens;

        do {
            x = random.nextInt(9);
            y = random.nextInt(9);
            boolean n = random.nextBoolean();
            if (n)
                sens = 'V';
            else
                sens = 'H';
        } while (!battleShipStageModel.Verifpeutetreposer(ship, x, y, taille, sens) || !surGrille(y,x,bateau.getTaille(),sens));
        if (id_Bot == 0)
            battleShipStageModel.ShipPlayer1[m].setCordonnerShip(y, x, sens);
        else
            battleShipStageModel.ShipPlayer2[m].setCordonnerShip(y, x, sens);
    }
    public boolean surGrille(double y, int x,int taille, char sens){
        System.out.println("Check"+y+" "+x+" "+sens);
        if(sens=='V'){
            return x <= 9 && y + taille - 1 <= 9 && 0 <= y && 0 <= x;

        } else {
            return x + taille - 1 <= 9 && y <= 9 && 0 <= y && 0 <= x;
        }


    }


}