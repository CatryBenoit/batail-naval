package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import model.BattleShipStageModel;
import model.Ship;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class BattleShipControler extends Controller {

    int levelbot;
    Set<Point> potentialTargetsJ1;
    Set<Point> potentialTargetsJ2;
    boolean targetModeJ1;
    boolean targetModeJ2;
    boolean lineModeJ1;
    boolean lineModeJ2;
    int[][] gridJ1 = new int[10][10];
    int[][] gridJ2 = new int[10][10];
    List<Point> lineTargetsJ1 = new ArrayList<>();
    List<Point> lineTargetsJ2 = new ArrayList<>();
    List<Point> hitJ1;
    List<Point> hitJ2;
    List<Point> shipPartJ1;
    List<Point> shipPartJ2;


    public BattleShipControler(Model model, View view) {
        super(model, view);
        setControlKey(new ControllerBattleShipkey(model, view, this));
        setControlMouse(new ControllerBatleShipMouse(model, view, this));
        setControlAction (new ControllerBattleShipAction(model, view, this));
        potentialTargetsJ1 = new HashSet<>();
        potentialTargetsJ2 = new HashSet<>();
        targetModeJ1 = false;
        targetModeJ2 = false;
        hitJ1 = new ArrayList<>();
        hitJ2 = new ArrayList<>();
        shipPartJ1 = new ArrayList<>();
        shipPartJ2 = new ArrayList<>();
        
    }

    public void endOfTurn() {
        // use the default method to compute next player
        model.setNextPlayer();
        // get the new player
        Player p = model.getCurrentPlayer();
        // change the text of the TextElement
        BattleShipStageModel stageModel = (BattleShipStageModel) model.getGameStage();

        if (p.getType() == Player.COMPUTER) {
            if(levelbot ==1){
                Logger.debug("COMPUTER PLAYS");
                BattleShipDecider decider = new BattleShipDecider(model,this,1,1);
                ActionPlayer play = new ActionPlayer(model, this, decider, null);
                play.start();
            } else {
                Logger.debug("COMPUTER PLAYS");
                BattleShipDecider decider = new BattleShipDecider(model,this,1,2);
                ActionPlayer play = new ActionPlayer(model, this, decider, null);
                play.start();
            }

        }
        else {
            Logger.debug("PLAYER PLAYS");
        }
    }


    public void setlevelbot(int level){
        if(level > 2|| level <0){
            return;
        }
        else {
            levelbot = level;
        }
    }

    public void setfirstplayer(int numerojoueur){
        if (numerojoueur == 1){
            return;
        } else if (numerojoueur==2) {
            model.setNextPlayer();
        }
    }

  }