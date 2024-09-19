package model;

import boardifier.model.*;

import java.sql.SQLOutput;

public class BattleShipStageModel extends GameStageModel {

    private int player1toplay;
    private int Player2toplay;

    private BattleBoard Boardplayer1;
    private BattleBoard Boardplayer2;
    public Ship[] ShipPlayer1;
    public Ship[] ShipPlayer2;
    private TextElement player1Name;
    private TextElement player2Name;
    private Missille[] MissileJoueur1;
    private Missille[] MissileJoueur2;
    private TextElement InfoPartie;
    private StockMissile stockMissileJ1;
    private StockMissile stockMissileJ2;


    public BattleShipStageModel(String name, Model model) {
        super(name, model);
        player1toplay = 50;
        Player2toplay = 50;
        setupCallbacks();

    }

    public void setStockMissileJ1(StockMissile stockMissileJ1) {this.stockMissileJ1 = stockMissileJ1;
        addContainer(stockMissileJ1);

    }

    public void setStockMissileJ2(StockMissile stockMissileJ2) {this.stockMissileJ2 = stockMissileJ2;
        addContainer(stockMissileJ2);

    }

    public StockMissile getStockMissileJ1() {return stockMissileJ1;}
    public StockMissile getStockMissileJ2() {return stockMissileJ2;}

    public void setshippartplayer1(shipPart[] shipParts){
        int num =0;
        for(int j = 0; j < ShipPlayer1.length; j++) {
            for (int i = 0; i < ShipPlayer1[j].shipParts.length; i++) {
                ShipPlayer1[j].shipParts[i] = shipParts[num];
                addElement(ShipPlayer1[j].shipParts[i]);
                ShipPlayer1[j].addElement(ShipPlayer1[j].shipParts[i], i, 0);
                num++;
            }

        }
    }
    public void setshippartplayer2(shipPart[] shipParts){
        int num =0;
        for(int j = 0; j < ShipPlayer1.length; j++) {
            for (int i = 0; i < ShipPlayer2[j].shipParts.length; i++) {
                ShipPlayer2[j].shipParts[i] = shipParts[num];
                addElement(ShipPlayer2[j].shipParts[i]);
                ShipPlayer2[j].addElement(ShipPlayer2[j].shipParts[i], i, 0);
                num++;
            }

        }
    }


    public void setInfoPartie(TextElement infopartie){this.InfoPartie = infopartie;}
    public TextElement getInfoPartie(){return this.InfoPartie;}


    // set et get pour playerXtoPlay utilisé pour nb de coup réstant
    public void setPlayer1ToPlay(int player1ToPlay) {this.player1toplay = player1ToPlay;}
    public int getPlayer1ToPlay() {return player1toplay;}
    public void setPlayer2ToPlay(int player2ToPlay) {this.Player2toplay = player2ToPlay;}
    public int getPlayer2ToPlay() {return Player2toplay;}

    //set et get de board pour chaque joueur
    public void setBoardPlayer1(BattleBoard boardPlayer1) {this.Boardplayer1 = boardPlayer1; addContainer(boardPlayer1);}
    public BattleBoard getBoardPlayer1() {return Boardplayer1;}
    public void setBoardPlayer2(BattleBoard boardPlayer2) {this.Boardplayer2 = boardPlayer2; addContainer(boardPlayer2);}
    public BattleBoard getBoardPlayer2() {return Boardplayer2;}


    //set et get du tableau des bateau de chaque joueur
    public void setShipsPlayer1(Ship[] shipsPlayer1) {this.ShipPlayer1 = shipsPlayer1;
        for(int i = 0; i < shipsPlayer1.length ; i++){addContainer(shipsPlayer1[i]);}}
    public Ship[] getShipsPlayer1() {return ShipPlayer1;}
    public void setShipsPlayer2(Ship[] shipsPlayer2) {this.ShipPlayer2 = shipsPlayer2;
        for(int i = 0; i < shipsPlayer2.length ; i++){addContainer(shipsPlayer2[i]);}}
    public Ship[] getShipsPlayer2() {return ShipPlayer2;}


    //set et get du nom de chaque joueur
    public void setPlayer1Name(TextElement player1Name) {this.player1Name = player1Name; addElement(player1Name);}
    public TextElement getPlayer1Name() {return player1Name;}
    public void setPlayer2Name(TextElement player1Name) {this.player2Name = player1Name; addElement(player2Name);}
    public TextElement getPlayer2Name() {return player2Name;}

    //Set et get des cellule pour les board
    public Missille[] getMissileJoueur1() {return MissileJoueur1;}
    public void setMissileJoueur1(Missille[] m){
        this.MissileJoueur1 = m;
        for(int i = 0; i < MissileJoueur1.length ; i++){addElement(MissileJoueur1[i]);}}
    public Missille[] getMissileJoueur2() {return MissileJoueur2;}
    public void setMissileJoueur2(Missille[] m){
        this.MissileJoueur2 = m;
        for(int i = 0; i < MissileJoueur2.length ; i++){addElement(MissileJoueur2[i]);}}

    //verif que les cordonnée des ship ne se colle pas : sur les coter et les coin


    public boolean Verifpeutetreposer(Ship[] ships, int xNewship, int yNewShip, int tailleNewShip, char sens){
    int a =0;
        int nbtotal =(tailleNewShip+2)*3;
        int tabcord [][]= new int[nbtotal][2];
        for(int i =0; i< tailleNewShip +2; i++){
            for(int j = -1; j<1;j++){
                if(sens=='H'){
                    tabcord[a][0]=xNewship+i;
                    tabcord[a][1]=yNewShip+j;
                    a++;
                }
                else if(sens=='V'){
                    tabcord[a][0]=xNewship+j;
                    tabcord[a][1]=yNewShip+i;
                    a++;
                }
            }
            for(int k =0; k< ships.length;k++){
                for(int l = 0; l<ships[k].getTaille(); l++){
                    for(int m = 0; m< tabcord.length; m++){
                        if(tabcord[m][0]==ships[k].shipParts[l].getcordonneX() && tabcord[m][1] == ships[k].shipParts[l].getcordonneY()){ return false;}
                    }

                }

            }

        }

        return true;
    }


    public boolean toucheroupas(Ship[] ships, int x , int y){
        for(int i = 0; i < ships.length; i++){
            for(int j= 0; j < ships[i].shipParts.length; j++ ){
                    if(ships[i].shipParts[j].getcordonneX() == x && ships[i].shipParts[j].getcordonneY() == y){
                        ships[i].shipParts[j].setToucher(true);
                        System.out.println("TOUCHER !!!");
                        ships[i].verifcouler();
                        return true;
                    }

            }
        }
        return false;
    }





    public void setupCallbacks(){
        onPutInContainer( (element, gridDest, rowDest, colDest) -> {
                 if (gridDest != Boardplayer1 && gridDest!= Boardplayer2 || element.getType() ==50 ) return;

                Missille m = (Missille) element;
                if (m.getIdjoueur() == 0) {
                    player1toplay--;
                    System.out.println(player1toplay);
                    System.out.println(Player2toplay);

                }
                else {
                    Player2toplay--;
                    System.out.println(player1toplay);
                    System.out.println(Player2toplay);
                }
                if ((player1toplay == 0 && Player2toplay == 0 ) || (toutShipCouler(ShipPlayer1)|| toutShipCouler(ShipPlayer2))) {
                    computePartyResult();
                }
            });


    }


    public boolean toutShipCouler(Ship[] ships){

        for (Ship ship : ships) {
            if (ship.getcouler() ==false) {
                return false;
            }
        }
        return true;
    }




    //méthode pour regarder qui gagne dans la partie
    private void computePartyResult(){
    int shippartcoulerplayer1 =0;
    int shippartcoulerplayer2 = 0;

    for(int i =0; i<ShipPlayer1.length ; i++){
        shippartcoulerplayer1 += ShipPlayer1[i].nbdepartcouler();
    }
    for(int i =0; i<ShipPlayer2.length ; i++){
        shippartcoulerplayer2 += ShipPlayer2[i].nbdepartcouler();
    }
    if(shippartcoulerplayer1 > shippartcoulerplayer2){
        System.out.println("le joueur :" + player1Name + " a gagner avec : "+ shippartcoulerplayer1 + "touche.");
        model.setIdWinner(1);
        model.stopStage();
    } else if (shippartcoulerplayer2 > shippartcoulerplayer1) {
        System.out.println("le joueur :" + player2Name + " a gagner avec : "+ shippartcoulerplayer2 + "touche.");
        model.setIdWinner(2);
        model.stopStage();
    }else{
        model.setIdWinner(-1);
        System.out.println("Match null");
        model.stopStage();
    }
    }


    public int nbdepart(Ship[] ships){
        int nbtotal = 0;
        for (int i = 0 ; i <ships.length; i++){
            nbtotal+= ships[i].getTaille();
        }
        return nbtotal;
    }


    //truc obligatoir c'est pas a quoi il sert mais il est la
    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new BattleShipStageFactory(this);
    }


    public void setinvisiblebateau(Ship[] ships){
        for (int j = 0; j < ships.length; j++) {
            for (int i = 0; i < ships[j].getTaille(); i++) {
                ships[j].shipParts[i].setVisible(false);
            }
        }
    }
    public void setvisiblebateau(Ship[] ships){
        for (int j = 0; j < ships.length; j++) {
            for (int i = 0; i < ships[j].getTaille(); i++) {
                ships[j].shipParts[i].setVisible(true);
            }
        }
    }

    public Ship getship(int numero, Ship[]ships){
        if (ships.length < numero) {return null;}
        return ships[numero];
    }

    public void setjoueur1invisible(){
        getBoardPlayer1().setVisible(false);
        //pas fini
    }
}
