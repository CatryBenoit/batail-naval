package view;

import boardifier.model.GameElement;
import boardifier.view.SpriteImageLook;
import boardifier.view.SpriteLook;
import boardifier.view.ElementLook;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import model.shipPart;


public class ShipPartLook extends ElementLook {

    private Circle circle;
    private int radius;

    public ShipPartLook(int radius,GameElement element) {
        super(element);
        shipPart shipPart = (shipPart) element;
        this.radius = radius;
        render();
    }

    @Override
    public void onSelectionChange() {
        shipPart pawn = (shipPart)getElement();
        if (pawn.isSelected()) {
            circle.setStrokeWidth(3);
            circle.setStrokeMiterLimit(10);
            circle.setStrokeType(StrokeType.CENTERED);
            circle.setStroke(Color.valueOf("0x333333"));
        }
        else {
            circle.setStrokeWidth(0);
        }
    }

    @Override
    public void onFaceChange() {
    }

    public void render(){
        shipPart shipPart = (shipPart)element;
        circle = new Circle();
        circle.setRadius(radius);
        if (shipPart.getColor() == 1 && shipPart.getIdplayer() ==0){ // neutre
            circle.setFill(Color.BLACK);
        } else if (shipPart.getColor() == 1 && shipPart.getIdplayer() ==1) {
            circle.setFill(Color.RED);
        } else if (shipPart.getColor() == 2){ // COULER
            circle.setFill(Color.GREEN);

        } else {
            circle.setFill(Color.BLACK);

        }
        addShape(circle);

    }
}
