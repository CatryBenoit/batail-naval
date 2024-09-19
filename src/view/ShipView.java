package view;

import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.stage.Stage;

public class ShipView extends View {

    public ShipView(Model model, Stage stage, RootPane rootPane) {
        super(model, stage, rootPane);
    }

    private javafx.scene.control.MenuItem menuStart, menuIntro, menuQuit;

    @Override
    protected void createMenuBar() {
        menuBar = new javafx.scene.control.MenuBar();
        javafx.scene.control.Menu menu1 = new javafx.scene.control.Menu("Game");
        menuStart = new javafx.scene.control.MenuItem("New game");
        menuIntro = new javafx.scene.control.MenuItem("Intro");
        menuQuit = new javafx.scene.control.MenuItem("Quit");
        menu1.getItems().addAll(menuStart, menuIntro, menuQuit);
        menuBar.getMenus().add(menu1);
    }


    public javafx.scene.control.MenuItem getMenuStart() {
        return menuStart;
    }

    public javafx.scene.control.MenuItem getMenuIntro() {
        return menuIntro;
    }

    public javafx.scene.control.MenuItem getMenuQuit() {
        return menuQuit;
    }
}
