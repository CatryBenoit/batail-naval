package TestModel;

import model.shipPart;
import boardifier.model.GameStageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import model.Ship;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class ShipUnitTest {

    private GameStageModel gameStageModel;
    private Ship ship;

    @BeforeEach
    public void setUp() {
        gameStageModel = mock(GameStageModel.class);
        ship = new Ship(0, 0, 3, gameStageModel);
    }

    @Test
    public void testSetAndGetSenstrue() {
        ship.setSens(true);
        assertTrue(ship.getSens());


    }
    @Test
    public void testgetsensfalse(){
        ship.setSens(false);
        assertFalse(ship.getSens());
    }

    @Test
    public void testSetShipParts() {
        ship.setShipParts(gameStageModel);
        assertNotNull(ship.getshippart());
        assertEquals(3, ship.getshippart().length);
    }

    @Test
    public void testSetAndGetCoordinates() {
        ship.setShipParts(gameStageModel);
        ship.setCordonnerShip(1, 1, 'H');

        assertEquals(1, ship.getPartCordonneX(0));
        assertEquals(1, ship.getPartCordonneY(0));
        assertEquals(2, ship.getPartCordonneX(1));
        assertEquals(1, ship.getPartCordonneY(1));
        assertEquals(3, ship.getPartCordonneX(2));
        assertEquals(1, ship.getPartCordonneY(2));
    }


    @Test
    public void testSetAndGetCoordinatesVertical() {
        ship.setShipParts(gameStageModel);
        ship.setCordonnerShip(4, 1, 'V');

        assertEquals(1, ship.getPartCordonneX(0));
        assertEquals(4, ship.getPartCordonneY(0));
        assertEquals(1, ship.getPartCordonneX(1));
        assertEquals(5, ship.getPartCordonneY(1));
        assertEquals(1, ship.getPartCordonneX(2));
        assertEquals(6, ship.getPartCordonneY(2));
    }

    @Test
    public void testVerifCouler() {
        ship.setShipParts(gameStageModel);
        ship.verifcouler();
        assertFalse(ship.getcouler());

        for (shipPart part : ship.getshippart()) {
            part.setToucher(true);
        }
        ship.verifcouler();
        assertTrue(ship.getcouler());
    }

    @Test
    public void testNbDepartCouler() {
        ship.setShipParts(gameStageModel);

        ship.getshippart()[0].setToucher(true);
        assertEquals(1, ship.nbdepartcouler());

        ship.getshippart()[1].setToucher(true);
        assertEquals(2, ship.nbdepartcouler());

        ship.getshippart()[2].setToucher(true);
        assertEquals(3, ship.nbdepartcouler());
    }

    @Test
    public void testestcouler() {
        ship.setShipParts(gameStageModel);
        assertFalse( ship.getcouler());
        ship.verifcouler();
        assertFalse( ship.getcouler());
        ship.getshippart()[0].setToucher(true);
        ship.verifcouler();
        assertFalse( ship.getcouler());
        ship.getshippart()[1].setToucher(true);
        ship.verifcouler();
        assertFalse( ship.getcouler());
        ship.getshippart()[2].setToucher(true);
        ship.verifcouler();
        assertTrue( ship.getcouler());
    }


    @Test
    public void testSetAndGetPlayerID() {
        ship.setidplayer(10);
        assertEquals(10, ship.getPlayerID());
    }

    @Test
    public void testGetShipID() {
        assertEquals(1, ship.getShipID());
        Ship anotherShip = new Ship(0, 0, 2, gameStageModel);
        assertEquals(2, anotherShip.getShipID());
    }

    @Test
    public void testSetCordonnerShipPlaceinvalidePossitif() {
        ship.setShipParts(gameStageModel);
        assertFalse(ship.setCordonnerShip(8, 8, 'H'));
    }
    @Test
    public void testSetCordonnerShipPlaceinvalideNégatif() {
        ship.setShipParts(gameStageModel);
        assertFalse(ship.setCordonnerShip(-8, -8, 'H'));
    }

    @Test
    public void testSetCordonnerShipPlaceinvalidenauvaisselettre() {
        ship.setShipParts(gameStageModel);
        assertFalse(ship.setCordonnerShip(5, 5, 'c'));
    }
}
