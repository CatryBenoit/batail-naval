package control;

import boardifier.model.GameException;
import boardifier.model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.HomePage;
import view.SelectionPage;

public class ButtonController {
    private SelectionPage sp;
    private HomePage hp;
    private Model model;
    private PageControl pageControl;

    public ButtonController(SelectionPage sp, HomePage hp, Model model, PageControl pageControl) {
        this.sp = sp;
        this.model = model;
        this.hp = hp;
        this.pageControl = pageControl;
        attachEvent();
    }

    static int startPlayer, p1, p2, mode,perso=0;

    private void attachEvent() {
        EventHandler<ActionEvent> buttonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == hp.getStart()) {
                    pageControl.sp(sp);
                } else if (event.getSource() == hp.getQuit()) {
                    pageControl.quit();
                } else if (event.getSource() == hp.getOptions()) {
                    System.out.println("Options button clicked");
                    // Handle options logic
                } else if (event.getSource() == sp.getValid()) {
                    try {
                        pageControl.play();
                    } catch (GameException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (event.getSource() == sp.getSj1()){
                    startPlayer=0;
                }

                else if (event.getSource() == sp.getSj2()){
                    startPlayer=1;
                }

                else if (event.getSource() == sp.getSrd()){
                    startPlayer=2;
                } else if (event.getSource()  == sp.getJ1()) {
                    p1=sp.getJ1().getSelectionModel().getSelectedIndex();
                }else if (event.getSource()  == sp.getJ2()) {
                    p2=sp.getJ2().getSelectionModel().getSelectedIndex();
                } else if (event.getSource()==sp.getMode()) {
                    mode=sp.getMode().getSelectionModel().getSelectedIndex();
                } else if (event.getSource()==sp.getModifier()) {
                    if (perso==0){
                        perso=1;
                    }
                    else {
                        perso = 0;
                    }
                }
                System.out.println(p1+" "+p2+" "+startPlayer+" "+mode+" "+perso);

            }
        };


        hp.setButtonListener(buttonHandler);
        sp.setButtonListener(buttonHandler);
    }

    public static int[] returnValues(){
        System.out.println("valeurs boutons");
        return new int[]{p1, p2,startPlayer,mode,perso};
    }
}
