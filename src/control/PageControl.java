package control;

import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import model.BattleShipStageFactory;
import model.BattleShipStageModel;
import view.HomePage;
import view.SelectionPage;
import view.ShipRootPane;

public class PageControl {
    ShipRootPane root;
    SelectionPage sp;

    BattleShipControler control;

    Model model;
    public PageControl(ShipRootPane root,BattleShipControler control, Model model){
        this.control=control;

        this.root=root;
        this.model=model;

    }
    public void hp(HomePage homePage){
        root.getChildren().clear();
        homePage.placeWidgets(root);
    }
    public void sp(SelectionPage selectionPage){
        root.getChildren().clear();
        selectionPage.placeWidgets(root);
    }
    public void quit(){
        root.getChildren().clear();
        System.exit(0);
    }

    public void play() throws GameException {
        int[] buttons = ButtonController.returnValues();
        //System.out.println(TextController.getMissiles());
        //TextController.getMissiles();
        if (buttons[0]==0){
            model.addHumanPlayer("Humain");
        } else if (buttons[0]==1) {
            model.addComputerPlayer("IA facile");
        } else if (buttons[0]==2) {
            model.addComputerPlayer("IA difficile");
        }

        if (buttons[1]==0){
            model.addHumanPlayer("Humain");
        } else if (buttons[1]==1) {
            model.addComputerPlayer("IA facile");
        } else if (buttons[1]==2) {
            model.addComputerPlayer("IA difficile");
        }

        control.startGame();

        



    }

    public void option(){
        root.getChildren().clear();
    }
}
