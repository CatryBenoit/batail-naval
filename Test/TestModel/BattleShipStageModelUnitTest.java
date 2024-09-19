package TestModel;


import boardifier.model.Model;
import boardifier.model.TextElement;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class BattleShipStageModelUnitTest {

    private BattleShipStageModel battleShipStageModel;
    private Model model;
    private BattleBoard boardPlayer1;
    private BattleBoard boardPlayer2;
    private Ship[] shipsPlayer1;
    private Ship[] shipsPlayer2;
    private TextElement player1Name;
    private TextElement player2Name;
    private StockMissile stockMissileJ1;
    private StockMissile stockMissileJ2;
    private Missille missille;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        battleShipStageModel = new BattleShipStageModel("TestStage", model);
        boardPlayer1 = mock(BattleBoard.class);
        boardPlayer2 = mock(BattleBoard.class);
        missille = mock(Missille.class);
        shipsPlayer1 = new Ship[5];
        shipsPlayer2 = new Ship[5];
        for (int i = 0; i < 5; i++) {
            shipsPlayer1[i] = mock(Ship.class);
            shipsPlayer2[i] = mock(Ship.class);
        }
        battleShipStageModel.setBoardPlayer1(boardPlayer1);
        battleShipStageModel.setBoardPlayer2(boardPlayer2);
        battleShipStageModel.setShipsPlayer1(shipsPlayer1);
        battleShipStageModel.setShipsPlayer2(shipsPlayer2);
    }


    @Test
    public void testSetStockMissileJ1() {
        battleShipStageModel.setStockMissileJ1(stockMissileJ1);
        assertEquals(stockMissileJ1, battleShipStageModel.getStockMissileJ1());
    }

    @Test
    public void testSetStockMissileJ2() {
        battleShipStageModel.setStockMissileJ2(stockMissileJ2);
        assertEquals(stockMissileJ2, battleShipStageModel.getStockMissileJ2());
    }

    @Test
    public void testSetShipsPlayer1() {
        battleShipStageModel.setShipsPlayer1(shipsPlayer1);
        assertArrayEquals(shipsPlayer1, battleShipStageModel.getShipsPlayer1());
    }

    @Test
    public void testSetShipsPlayer2() {
        battleShipStageModel.setShipsPlayer2(shipsPlayer2);
        assertArrayEquals(shipsPlayer2, battleShipStageModel.getShipsPlayer2());
    }

    @Test
    public void testSetPlayer1Name() {
        battleShipStageModel.setPlayer1Name(player1Name);
        assertEquals(player1Name, battleShipStageModel.getPlayer1Name());
    }

    @Test
    public void testSetPlayer2Name() {
        battleShipStageModel.setPlayer2Name(player2Name);
        assertEquals(player2Name, battleShipStageModel.getPlayer2Name());
    }


    @Test
    public void testVerifpeutetreposer() {

        when(shipsPlayer1[0].getPartCordonneX(anyInt())).thenReturn(1);
        when(shipsPlayer1[0].getPartCordonneY(anyInt())).thenReturn(1);
        when(shipsPlayer1[0].getTaille()).thenReturn(3);

        boolean result = battleShipStageModel.Verifpeutetreposer(shipsPlayer1, 4, 4, 3, 'H');
        assertTrue(result);

        result = battleShipStageModel.Verifpeutetreposer(shipsPlayer1, 1, 1, 3, 'H');
        assertFalse(result);
    }

    //a fix
    /*
    @Test
    public void testToucherOuPas() {
        shipPart part = mock(shipPart.class);
        when(shipsPlayer1[0].shipParts).thenReturn(mock(shipPart.class));
        when(part.getcordonneX()).thenReturn(2);
        when(part.getcordonneY()).thenReturn(3);
        when(part.esttoucher()).thenReturn(false);

        boolean result = battleShipStageModel.toucheroupas(shipsPlayer1, 2, 3);
        assertTrue(result);
        verify(part).setToucher(true);
    }
*/
    @Test
    public void testToutShipCouler() {
        when(shipsPlayer1[0].getcouler()).thenReturn(true);
        when(shipsPlayer1[1].getcouler()).thenReturn(true);
        when(shipsPlayer1[2].getcouler()).thenReturn(true);
        when(shipsPlayer1[3].getcouler()).thenReturn(true);
        when(shipsPlayer1[4].getcouler()).thenReturn(true);

        boolean result = battleShipStageModel.toutShipCouler(shipsPlayer1);
        assertTrue(result);

        when(shipsPlayer1[4].getcouler()).thenReturn(false);
        result = battleShipStageModel.toutShipCouler(shipsPlayer1);
        assertFalse(result);
    }

    @Test
    public void testSetupCallbacks_Player1Missille() {
        when(missille.getIdjoueur()).thenReturn(1);

        // Trigger the callback
        battleShipStageModel.setupCallbacks();
        battleShipStageModel.onPutInContainer();

        assertEquals(49, battleShipStageModel.getPlayer1ToPlay());
        verify(missille, times(1)).getIdjoueur();
    }

    @Test
    public void testSetupCallbacks_Player2Missille() {
        when(missille.getIdjoueur()).thenReturn(2);

        // Trigger the callback
        battleShipStageModel.setupCallbacks();
        battleShipStageModel.onPutInContainer(missille, boardPlayer2, 0, 0);

        assertEquals(49, battleShipStageModel.getPlayer2ToPlay());
        verify(missille, times(1)).getIdjoueur();
    }

    @Test
    public void testSetupCallbacks_ComputePartyResult() {
        when(missille.getIdjoueur()).thenReturn(1);
        battleShipStageModel.setPlayer1ToPlay(1);
        battleShipStageModel.setPlayer2ToPlay(0);

        // Mock toutShipCouler to return true
        when(battleShipStageModel.toutShipCouler(any())).thenReturn(true);

        // Spy on the battleShipStageModel to verify computePartyResult call
        BattleShipStageModel spyBattleShipStageModel = spy(battleShipStageModel);

        // Trigger the callback
        spyBattleShipStageModel.setupCallbacks();
        spyBattleShipStageModel.onPutInContainer(missille, boardPlayer1, 0, 0);

        assertEquals(0, spyBattleShipStageModel.getPlayer1ToPlay());
        verify(spyBattleShipStageModel, times(1)).computePartyResult();
    }

    @Test
    public void testSetupCallbacks_NotComputePartyResult() {
        when(missille.getIdjoueur()).thenReturn(1);
        battleShipStageModel.setPlayer1ToPlay(1);
        battleShipStageModel.setPlayer2ToPlay(1);

        // Mock toutShipCouler to return false
        when(battleShipStageModel.toutShipCouler(any())).thenReturn(false);

        // Spy on the battleShipStageModel to verify computePartyResult call
        BattleShipStageModel spyBattleShipStageModel = spy(battleShipStageModel);

        // Trigger the callback
        spyBattleShipStageModel.setupCallbacks();
        spyBattleShipStageModel.onPutInContainer(missille, boardPlayer1, 0, 0);

        assertEquals(0, spyBattleShipStageModel.getPlayer1ToPlay());
        verify(spyBattleShipStageModel, times(0)).computePartyResult();
    }

}
