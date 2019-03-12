package zen.test.tictactoe;

import zen.test.tictactoe.ex.ForbiddenActionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static zen.test.tictactoe.GamePad.SYMBOL.O;
import static zen.test.tictactoe.GamePad.SYMBOL.X;

/**
 * @author zen
 * @since 2019/03/11
 */
public class Game {
    private final GamePad gamePad;
    private final Player player1;
    private final Player player2;
    private int round = 0;

    public Game(GamePad gamePad,
                Player player1,
                Player player2) {
        this.gamePad = gamePad;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Player getActivePlayer() {
        if (isRoundForPlayer1())
            return player1;
        return player2;
    }

    public void activePlayerStepAt(int slotNum) {
        GamePad.SYMBOL s = isRoundForPlayer1() ? O : X;
        if(gamePad.isSlotEmpty(slotNum)) {
            gamePad.setSlot(slotNum, s);
            round++;
        }
        else {
            throw new ForbiddenActionException(slotNum+" is placed by "+ gamePad.getSlot(slotNum));
        }
    }

    private boolean isRoundForPlayer1() {
        return round % 2 == 0;
    }

    public boolean isEnd() {
        return round >= 9 || getWinner() != null;
    }

    public Player getWinner() {
        List<int[]> rowWin = Arrays.asList(new int[] {4, 5, 6},
                                           new int[] {1, 2, 3},
                                           new int[] {7, 8, 9},
                                           new int[] {1, 4, 7},
                                           new int[] {2, 5, 8},
                                           new int[] {3, 6, 9},
                                           new int[] {1, 5, 9},
                                           new int[] {3, 5, 7});

        List<Integer> oSlot = new ArrayList<>();
        List<Integer> xSlot = new ArrayList<>();

        GamePad.SYMBOL[][] pad = gamePad.getPadSlots();
        for (int row = 0, m = pad.length; row < m; row++) {
            for (int col = 0, n = pad[row].length; col < n; col++) {
                GamePad.SYMBOL s = pad[row][col];
                int num = row * 3 + col + 1;
                if (O == s) {
                    oSlot.add(num);
                }
                else if (X == s) {
                    xSlot.add(num);
                }
            }
        }
        for (int[] rowWinPattern : rowWin) {
            List<Integer> p = Arrays.stream(rowWinPattern)
                                    .boxed()
                                    .collect(Collectors.toList());
            if (oSlot.containsAll(p))
                return player1;
            else if (xSlot.containsAll(p)) {
                return player2;
            }
        }

        return null;

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("What's your name, player1?");
        String player1Name = sc.next();
        System.out.println("What's your name, player2?");
        String player2Name = sc.next();

        Player p1 = new Player(player1Name);
        Player p2 = new Player(player2Name);

        Game game = new Game(new GamePad(), p1, p2);

        while (!game.isEnd()) {
            Player player = game.getActivePlayer();
            System.out.println(
                    player.getName() + ", what's your next step? please input like: \n| 1 2 3 |\n| 4 5 6 |\n| 7 8 9 |");
            int slotNum = sc.nextInt();
            game.activePlayerStepAt(slotNum);
        }
        Player winner = game.getWinner();
        if (winner != null) {
            System.out.println("Congratuation! " + winner.getName() + ", You wins !!");
        }
        else {
            System.out.println("Draw game ...QQ");
        }
    }
}
