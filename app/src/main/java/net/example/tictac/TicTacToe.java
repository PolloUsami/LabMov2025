package net.example.tictac;

import java.util.Arrays;

public class TicTacToe {

    public static String playerX, playerO;
    public static int playerWinsX, playerWinsO;

    // Options.
    public static final int ICON = 0;
    public static final int ICON_X = 1;
    public static final int ICON_O = 2;

    public static int currentMoveIcon = ICON_X;

    public static final int[] GAME_BOARD = new int[3 * 3];

    public static void reset() {
        Arrays.fill(GAME_BOARD, ICON);
    }

    public static boolean setIconAt(int row, int col, int icon) {
        if(row < 0 || row >= 3 || col < 0 || col >= 3 || icon <= 0 || icon >= 3)
            throw new IllegalStateException();

        if(GAME_BOARD[row + (col * 3)] != ICON)
            return false;
        else {
            GAME_BOARD[row + (col * 3)] = icon;

            return true;
        }
    }

    public static int getIconAt(int row, int col) {
        if(row < 0 || row >= 3 || col < 0 || col >= 3)
            throw new IllegalStateException();


        return GAME_BOARD[row + (col * 3)];
    }

    public static int getWinIcon() {
        if(checkIfWins(ICON_X))
            return ICON_X;
        else if(checkIfWins(ICON_O))
            return ICON_O;
        else
            return ICON;
    }

    private static boolean checkIfWins(int icon) {
        int a, b, c;

        for(int i = 0; i < 3; i++) {

            // Checks vertical wins.
            a = getIconAt(0, i);
            b = getIconAt(1, i);
            c = getIconAt(2, i);

            if(a == icon && b == icon && c == icon) return true;

            // Checks horizontal wins.
            a = getIconAt(i, 0);
            b = getIconAt(i, 1);
            c = getIconAt(i, 2);

            if(a == icon && b == icon && c == icon) return true;

        }

        // Check diagonal win conditions.
        if(getIconAt(1, 1) == icon) {
            a = getIconAt(0, 0);
            b = getIconAt(2, 2);

            if(a != icon || b != icon) {
                a = getIconAt(0, 2);
                b = getIconAt(2, 0);
            }


            return a == icon && b == icon;
        }


        return false;
    }

}
