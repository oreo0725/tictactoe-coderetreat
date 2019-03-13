package zen.test.tictactoe.ex;

/**
 * @author zen
 * @since 2019/03/13
 */
public class GameOverException extends RuntimeException {

    public GameOverException() {
        super("Game is already over ");
    }
}
