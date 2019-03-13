package zen.test.tictactoe;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import zen.test.tictactoe.ex.ForbiddenActionException;
import zen.test.tictactoe.ex.GameOverException;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static zen.test.tictactoe.GamePad.SYMBOL;
import static zen.test.tictactoe.GamePad.SYMBOL.O;
import static zen.test.tictactoe.GamePad.SYMBOL.X;

/**
 * @author zen
 * @since 2019/03/11
 */
public class GamePadTest {

    private final Player p1 = new Player("Ben");
    private final Player p2 = new Player("Sarah");
    private final GamePad gamePad = new GamePad();
    private final Game game = new Game(gamePad, p1, p2);

    @Test
    public void test_getPadSlots_GIVEN_round0_THEN_slots_are_all_null() throws Exception {
        // GIVEN
        // WHEN
        SYMBOL[][] padMove = gamePad.getPadSlots();
        // THEN
        assertThat(padMove).containsExactly(new SYMBOL[3], new SYMBOL[3], new SYMBOL[3]);
    }

    @Test
    public void test_getActivePlayer_GIVEN_round1_THEN_get_player1() throws Exception {
        // GIVEN

        // WHEN
        Player currentPlayer = game.getActivePlayer();
        // THEN
        assertThat(currentPlayer).isEqualTo(p1);
    }

    @Test
    public void test_getPadSlots_GIVEN_p1_take5_at_round1() throws Exception {
        // GIVEN
        playTakeTurns(5);
        // WHEN
        Player currentPlayer = game.getActivePlayer();
        // THEN
        assertThat(currentPlayer).isEqualTo(p2);
        assertThat(gamePad.getPadSlots()).containsExactly(new SYMBOL[3], new SYMBOL[] {null, O, null}, new SYMBOL[3]);
    }

    @Test
    public void test_getPadSlots_GIVEN_p1S5_p2S1() throws Exception {
        // GIVEN
        playTakeTurns(5, 1);
        // WHEN
        Player currentPlayer = game.getActivePlayer();
        // THEN
        assertThat(currentPlayer).isEqualTo(p1);
        assertThat(gamePad.getPadSlots()).containsExactly(new SYMBOL[] {X, null, null},
                                                          new SYMBOL[] {null, O, null},
                                                          new SYMBOL[3]);
    }

    @Test
    public void test_getPadSlots_GIVEN_p1S5_p2S1_p1S2() throws Exception {
        // GIVEN
        playTakeTurns(5, 1, 2);
        // WHEN
        Player currentPlayer = game.getActivePlayer();
        // THEN
        assertThat(game.isGameOver()).isFalse();
        assertThat(currentPlayer).isEqualTo(p2);
        assertThat(gamePad.getPadSlots()).containsExactly(new SYMBOL[] {X, O, null},
                                                          new SYMBOL[] {null, O, null},
                                                          new SYMBOL[3]);
    }

    @Test
    public void test_getPadSlots_GIVEN_o5x1o2x4o8_THEN_p1_win() throws Exception {
        // GIVEN
        // WHEN
        playTakeTurns(5, 1, 4, 2, 6);
        // THEN
        assertThat(game.isGameOver()).isTrue();
        assertThat(game.getWinner()).isEqualTo(p1);
        assertThat(gamePad.getPadSlots()).containsExactly(new SYMBOL[] {X, X, null},
                                                          new SYMBOL[] {O, O, O},
                                                          new SYMBOL[] {null, null, null});
    }

    @Test
    public void test_getPadSlots_GIVEN_o5x1o4x2o7x3_THEN_p2_win() throws Exception {
        // GIVEN
        // WHEN
        playTakeTurns(5, 1, 4, 2, 7, 3);
        // THEN
        assertThat(game.isGameOver()).isTrue();
        assertThat(game.getWinner()).isEqualTo(p2);
        assertThat(gamePad.getPadSlots()).containsExactly(new SYMBOL[] {X, X, X},
                                                          new SYMBOL[] {O, O, null},
                                                          new SYMBOL[] {O, null, null});
    }

    @Test
    public void test_getPadSlots_GIVEN_o1x2o4x5o7_THEN_p1_win() throws Exception {
        // GIVEN
        // WHEN
        playTakeTurns(1, 2, 4, 5, 7);
        // THEN
        assertThat(game.isGameOver()).isTrue();
        assertThat(game.getWinner()).isEqualTo(p1);
        assertThat(gamePad.getPadSlots()).containsExactly(new SYMBOL[] {O, X, null},
                                                          new SYMBOL[] {O, X, null},
                                                          new SYMBOL[] {O, null, null});
    }

    @Test
    public void test_getPadSlots_GIVEN_o1x2o5x4o9_THEN_p1_win() throws Exception {
        // GIVEN
        // WHEN
        playTakeTurns(1, 2, 5, 4, 9);
        // THEN
        assertThat(game.isGameOver()).isTrue();
        assertThat(game.getWinner()).isEqualTo(p1);
        assertThat(gamePad.getPadSlots()).containsExactly(new SYMBOL[] {O, X, null},
                                                          new SYMBOL[] {X, O, null},
                                                          new SYMBOL[] {null, null, O});
    }

    @Test
    public void test_getPadSlots_GIVEN_o1x1_THEN_throw_ForbiddenActionException() throws Exception {
        // GIVEN
        // WHEN
        try {
            playTakeTurns(1, 1);

            Assertions.fail("Cannot be here");
        } catch (ForbiddenActionException e) {

            System.out.println(e);
            assertThat(game.isGameOver()).isFalse();
            assertThat(game.getWinner()).isNull();
            assertThat(game.getActivePlayer()).isEqualTo(p2);
            assertThat(gamePad.getPadSlots()).containsExactly(new SYMBOL[] {O, null, null},
                                                              new SYMBOL[] {null, null, null},
                                                              new SYMBOL[] {null, null, null});
        }

    }

    @Test
    public void test_getPadSlots_GIVEN_o5x1o3x7o4x6o8x2o9x1_THEN_throw_GameOverException() throws Exception {
        // GIVEN
        // WHEN
        try {
            playTakeTurns(5, 1, 3, 7, 4, 6, 8, 2, 9, 1);

            Assertions.fail("Cannot be here");
        } catch (GameOverException e) {

            System.out.println(e);
            assertThat(game.isGameOver()).isTrue();
            assertThat(game.getWinner()).isNull();
            assertThat(gamePad.getPadSlots()).containsExactly(new SYMBOL[] {X, X, O},
                                                              new SYMBOL[] {O, O, X},
                                                              new SYMBOL[] {X, O, O});
        }

    }

    @Test
    public void test_getPadSlots_GIVEN_o5x1o4x2o6x3_THEN_throw_GameOverException() throws Exception {
        // GIVEN
        // WHEN
        try {
            playTakeTurns(5, 1, 4, 2, 6, 3);

            Assertions.fail("Cannot be here");
        } catch (GameOverException e) {

            System.out.println(e);
            assertThat(game.isGameOver()).isTrue();
            assertThat(game.getWinner()).isEqualTo(p1);
            assertThat(gamePad.getPadSlots()).containsExactly(new SYMBOL[] {X, X, null},
                                                              new SYMBOL[] {O, O, O},
                                                              new SYMBOL[] {null, null, null});
        }

    }

    private void playTakeTurns(int... slots) {
        Arrays.stream(slots)
              .forEach(game::activePlayerStepAt);
    }

}
