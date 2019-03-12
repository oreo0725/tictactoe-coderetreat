package zen.test.tictactoe;

import java.util.stream.Stream;

/**
 * @author zen
 * @since 2019/03/11
 */
public class GamePad {

    private final SYMBOL[][] symbols = new SYMBOL[3][3];

    public void setSlot(int slotNum,
                        SYMBOL symbol) {
        int row = (slotNum - 1) / 3;
        int col = (slotNum - 1) % 3;
        symbols[row][col] = symbol;
        System.out.println(toString());
    }

    enum SYMBOL {
        O,  //first player use
        X
    }

    public SYMBOL[][] getPadSlots() {
        return symbols;
    }

    @Override
    public String toString() {
        String s = "";
        for (int row = 0, n = symbols.length; row < n; row++) {
            s += Stream.of(symbols[row])
                       .map(symbol -> symbol == null ? "." : symbol.name())
                       .reduce("|", (i1, i2) -> i1 + " " + i2) + "|\n";
        }
        return s;
    }

}
