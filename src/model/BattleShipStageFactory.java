package model;
import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;

import java.sql.SQLOutput;
import java.util.Scanner;
import control.ButtonController;
import control.TextController;

public class BattleShipStageFactory extends StageElementsFactory {

    private BattleShipStageModel stageModel;

    public BattleShipStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (BattleShipStageModel) gameStageModel;
    }


    /* setup pour le mode de jeu 1
            - 1 porte-avion de 5 cases,
            - 1 croiseur de 4 cases,
            - 2 contre-torpilleurs de 3 cases,
            - 1 torpilleur de 2 cases.
     */

    private void setupMode1(){
        TextElement textplayer1 = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        textplayer1.setLocation(0,0);
        stageModel.setPlayer1Name(textplayer1);


        BattleBoard boardplayer1 = new BattleBoard(0, 10, stageModel, "boardplayer1");
        // assign the board to the game stage model
        stageModel.setBoardPlayer1(boardplayer1);

        BattleBoard boardplayer2 = new BattleBoard(1000, 10, stageModel, "boardplayer2");
        // assign the board to the game stage model
        stageModel.setBoardPlayer2(boardplayer2);

        //création des ship et de leur partie
        //ship pour player 1
        Ship[] shipplayer1 = new Ship[5];
        shipplayer1[0] = new Ship(550,30,5,stageModel);
        shipplayer1[1] = new Ship(600,30,4,stageModel);
        shipplayer1[2] = new Ship(650,30,3,stageModel);
        shipplayer1[3] = new Ship(700,30,3,stageModel);
        shipplayer1[4] = new Ship(750,30,2,stageModel);


        //ship pour player 2
        Ship[] shipplayer2 = new Ship[5];
        shipplayer2[0] = new Ship(1550,30,5,stageModel);
        shipplayer2[1] = new Ship(1600,30,4,stageModel);
        shipplayer2[2] = new Ship(1650,30,3,stageModel);
        shipplayer2[3] = new Ship(1700,30,3,stageModel);
        shipplayer2[4] = new Ship(1750,30,2,stageModel);


        for (int i = 0; i < shipplayer1.length; i++) {
            shipplayer1[i].setShipParts(stageModel);
        }stageModel.setShipsPlayer1(shipplayer1);


        for(int i = 0; i < shipplayer2.length; i++) {
            shipplayer2[i].setShipParts(stageModel);
        }stageModel.setShipsPlayer2(shipplayer2);


        //System.out.println(stageModel.nbdepart(shipplayer2));
        shipPart[] partsj1 = new shipPart[stageModel.nbdepart(shipplayer1)];
        shipPart[] partsj2 = new shipPart[stageModel.nbdepart(shipplayer2)];
        for (int i = 0; i < 17 ; i++) {
            partsj1[i] = new shipPart(i+1,1,1,stageModel);
            partsj2[i] = new shipPart(i+1,1,0,stageModel);
            //System.out.println(i);
        }

        stageModel.setshippartplayer1(partsj1);
        stageModel.setshippartplayer2(partsj2);

        TextElement infopartie = new TextElement("d", stageModel);
        stageModel.setInfoPartie(infopartie);



    }


    //setup mode de jeux 2 :
    //                      -1 cuirassé de 4 cases,
    //                      -2 croiseurs de 3 cases,
    //                      -3 torpilleurs de 2 cases,
    //                      -4 sous-marins de 1 case

    private void setupMode2(){
        TextElement textplayer1 = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        textplayer1.setLocation(0,0);
        stageModel.setPlayer1Name(textplayer1);


        BattleBoard boardplayer1 = new BattleBoard(0, 10, stageModel, "boardplayer1");
        // assign the board to the game stage model
        stageModel.setBoardPlayer1(boardplayer1);

        BattleBoard boardplayer2 = new BattleBoard(1000, 10, stageModel, "boardplayer2");
        // assign the board to the game stage model
        stageModel.setBoardPlayer2(boardplayer2);

        //création des ship et de leur partie
        //ship pour player 1
        Ship[] shipplayer1 = new Ship[10];
        shipplayer1[0] = new Ship(550,30,4,stageModel);
        shipplayer1[1] = new Ship(600,30,3,stageModel);
        shipplayer1[2] = new Ship(650,30,3,stageModel);
        shipplayer1[3] = new Ship(700,30,2,stageModel);
        shipplayer1[4] = new Ship(750,30,2,stageModel);
        shipplayer1[5] = new Ship(550,130,2,stageModel);
        shipplayer1[6] = new Ship(600,130,1,stageModel);
        shipplayer1[7] = new Ship(650,130,1,stageModel);
        shipplayer1[8] = new Ship(700,130,1,stageModel);
        shipplayer1[9] = new Ship(750,130,1,stageModel);
        stageModel.setShipsPlayer1(shipplayer1);

        //ship pour player 2
        Ship[] shipplayer2 = new Ship[10];
        shipplayer2[0] = new Ship(1550,20,4,stageModel);
        shipplayer2[1] = new Ship(1600,30,3,stageModel);
        shipplayer2[2] = new Ship(1650,30,3,stageModel);
        shipplayer2[3] = new Ship(1700,30,2,stageModel);
        shipplayer2[4] = new Ship(1750,30,2,stageModel);
        shipplayer2[5] = new Ship(1550,130,2,stageModel);
        shipplayer2[6] = new Ship(1600,130,1,stageModel);
        shipplayer2[7] = new Ship(1650,130,1,stageModel);
        shipplayer2[8] = new Ship(1700,130,1,stageModel);
        shipplayer2[9] = new Ship(1750,130,1,stageModel);
        stageModel.setShipsPlayer2(shipplayer2);

        for (int i = 0; i < shipplayer1.length; i++) {
            shipplayer1[i].setShipParts(stageModel);
        }stageModel.setShipsPlayer1(shipplayer1);


        for(int i = 0; i < shipplayer2.length; i++) {
            shipplayer2[i].setShipParts(stageModel);
        }stageModel.setShipsPlayer2(shipplayer2);


        //System.out.println(stageModel.nbdepart(shipplayer2));
        shipPart[] partsj1 = new shipPart[stageModel.nbdepart(shipplayer1)];
        shipPart[] partsj2 = new shipPart[stageModel.nbdepart(shipplayer2)];
        for (int i = 0; i < stageModel.nbdepart(shipplayer2) ; i++) {
            partsj1[i] = new shipPart(i+1,1,1,stageModel);
            partsj2[i] = new shipPart(i+1,1,0,stageModel);
            //System.out.println(i);
        }

        stageModel.setshippartplayer1(partsj1);
        stageModel.setshippartplayer2(partsj2);

        TextElement infopartie = new TextElement("d", stageModel);
        stageModel.setInfoPartie(infopartie);




    }



    private void setupmissile(int nb){
        StockMissile stkj1 = new StockMissile(44,1,stageModel);
        StockMissile stkj2 = new StockMissile(144,1,stageModel);
        stageModel.setStockMissileJ1(stkj1);
        stageModel.setStockMissileJ2(stkj2);
        stageModel.setPlayer1ToPlay(nb);
        stageModel.setPlayer2ToPlay(nb);
        Missille[] missillePlayer1 = new Missille[nb];
        for (int i = 0; i < missillePlayer1.length; i++) {
            missillePlayer1[i]= new Missille(i+1,1,0,stageModel);

        }
        stageModel.setMissileJoueur1(missillePlayer1);
        Missille[] missillePlayer2 = new Missille[nb];
        for (int i = 0; i < missillePlayer2.length; i++) {
            missillePlayer2[i]= new Missille(i+1,1,1,stageModel);
        }
        stageModel.setMissileJoueur2(missillePlayer2);


        for(int i =0; i < missillePlayer1.length; i++){
            stkj1.addElement(missillePlayer1[i],0,0);
            stkj2.addElement(missillePlayer2[i],0,0);
        }
    }






    @Override
    public void setup() {
       int tab[] =ButtonController.returnValues();
        int nb= TextController.getMissiles();
        System.out.println(nb);
        int mode =tab[3];
        if(mode == 0){
            setupMode1();
        } else if (mode == 1) {
            setupMode2();

        }
        if(nb == -1){
            if(mode == 0){
                nb=35;
            } else if (mode == 1) {
                nb=50;

            }
        }
        setupmissile(nb);
    }







}







