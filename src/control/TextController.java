package control;

import boardifier.model.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.Ship;
import view.SelectionPage;

public class TextController implements ChangeListener<String> {

    private Model model;
    private SelectionPage sp;
    private static String missiles;

    public TextController(Model model, SelectionPage sp) {
        this.model = model;
        this.sp = sp;
        sp.setTextListener(this);  // Set the text listener in the SelectionPage
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            missiles = newValue;
            System.out.println(missiles);
            // Update the model with the new missile count if needed
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + newValue);
            // Handle the invalid number format if needed
        }
    }



    // Static method to get the current missiles value
    public static int getMissiles() {
        if (missiles == null) {
            return -1;
        }

            return Integer.parseInt(missiles);

    }
}
