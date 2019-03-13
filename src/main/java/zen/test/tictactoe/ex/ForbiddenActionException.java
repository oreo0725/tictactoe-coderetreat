package zen.test.tictactoe.ex;

/**
 * @author zen
 * @since 2019/03/12
 */
public class ForbiddenActionException extends RuntimeException {

    public ForbiddenActionException(String msg) {
        super(msg);
    }
}
