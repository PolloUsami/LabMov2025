package net.example.tictac

import android.text.InputType
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameEngine(val app: AppCompatActivity) {

    // The existence of this function its a bad practice, but idk.
    fun show(view: Int) {
        when(view) {
            R.layout.activity_main -> {
                this.app.setContentView(view);

                val start = app.findViewById<Button>(R.id.main_start);

                // Change to player select view button.
                start.setOnClickListener { show(R.layout.activity_player_select) };

            }
            R.layout.activity_player_select -> {
                this.app.setContentView(view);

                val back = app.findViewById<Button>(R.id.main_back);
                val playerInputX = app.findViewById<TextView>(R.id.player_name_x);
                val playerInputO = app.findViewById<TextView>(R.id.player_name_o);
                val start = app.findViewById<Button>(R.id.game_start);

                // Starts the game only if the names are valid.
                start.setOnClickListener {
                    val X: String = playerInputX.text.toString().trim();
                    val O: String = playerInputO.text.toString().trim();

                    if(X.isEmpty() || O.isEmpty() || X.contentEquals(O, true)) {
                        val toastText: String = "Ingrese los nombres correctamente";

                        playerInputX.text = "";
                        playerInputO.text = "";

                        Toast.makeText(app.applicationContext, toastText, Toast.LENGTH_SHORT).show();
                    } else {

                        TicTacToe.playerO = O;
                        TicTacToe.playerX = X;

                        TicTacToe.reset();

                        show(R.layout.activity_game);

                    }

                }

                back.setOnClickListener { show(R.layout.activity_main) }

            }
            R.layout.activity_game -> {
                this.app.setContentView(view);

                val back = app.findViewById<Button>(R.id.main_back);
                back.setOnClickListener { show(R.layout.activity_player_select) }

                val resetButton = app.findViewById<Button>(R.id.reset_button);
                resetButton.setOnClickListener { handleGameUpdate(resetButton) }

                val cell00 = app.findViewById<Button>(R.id.cell_0_0);
                val cell01 = app.findViewById<Button>(R.id.cell_0_1);
                val cell02 = app.findViewById<Button>(R.id.cell_0_2);
                val cell10 = app.findViewById<Button>(R.id.cell_1_0);
                val cell11 = app.findViewById<Button>(R.id.cell_1_1);
                val cell12 = app.findViewById<Button>(R.id.cell_1_2);
                val cell20 = app.findViewById<Button>(R.id.cell_2_0);
                val cell21 = app.findViewById<Button>(R.id.cell_2_1);
                val cell22 = app.findViewById<Button>(R.id.cell_2_2);

                cell00.setOnClickListener { handleGameUpdate(cell00) }
                cell01.setOnClickListener { handleGameUpdate(cell01) }
                cell02.setOnClickListener { handleGameUpdate(cell02) }
                cell10.setOnClickListener { handleGameUpdate(cell10) }
                cell11.setOnClickListener { handleGameUpdate(cell11) }
                cell12.setOnClickListener { handleGameUpdate(cell12) }
                cell20.setOnClickListener { handleGameUpdate(cell20) }
                cell21.setOnClickListener { handleGameUpdate(cell21) }
                cell22.setOnClickListener { handleGameUpdate(cell22) }

                handleGameUpdate(resetButton);

            }
        }
    }

    fun handleGameUpdate(view: Button) {

        val scoreX = this.app.findViewById<TextView>(R.id.score_x);
        val scoreO = this.app.findViewById<TextView>(R.id.score_o);
        val status = this.app.findViewById<TextView>(R.id.status);

        if(view.id == R.id.reset_button) {
            TicTacToe.currentMoveIcon = if(TicTacToe.currentMoveIcon == TicTacToe.ICON_X) TicTacToe.ICON_O else TicTacToe.ICON_X;

            status.text = "Turno de ${if(TicTacToe.currentMoveIcon == TicTacToe.ICON_X) TicTacToe.playerX else TicTacToe.playerO}";

            TicTacToe.reset();
        }

        val move = checkGridButton(view);

        if(TicTacToe.getWinIcon() == TicTacToe.ICON && move != null) {
            val row = move.first;
            val col = move.second;

            if(TicTacToe.setIconAt(row, col, TicTacToe.currentMoveIcon)) {
                if(TicTacToe.getWinIcon() == TicTacToe.ICON) {
                    TicTacToe.currentMoveIcon = if(TicTacToe.currentMoveIcon == TicTacToe.ICON_X) TicTacToe.ICON_O else TicTacToe.ICON_X;

                    status.text = "Turno de ${if(TicTacToe.currentMoveIcon == TicTacToe.ICON_X) TicTacToe.playerX else TicTacToe.playerO}";

                } else {
                    if(TicTacToe.getWinIcon() == TicTacToe.ICON_X) {
                        TicTacToe.playerWinsX++;
                    } else {
                        TicTacToe.playerWinsO++;
                    }
                }
            }

        }

        if(TicTacToe.getWinIcon() != TicTacToe.ICON) {
            val playerName = if(TicTacToe.getWinIcon() == TicTacToe.ICON_X) TicTacToe.playerX else TicTacToe.playerO;

            status.text = "Gano ${playerName}";
        }

        scoreX.text = "${TicTacToe.playerX} (X): ${TicTacToe.playerWinsX}";
        scoreO.text = "${TicTacToe.playerO} (O): ${TicTacToe.playerWinsO}";

        // Updates the visible grid to match the game buttons
        updateGridButton(R.id.cell_0_0, 0, 0);
        updateGridButton(R.id.cell_0_1, 0, 1);
        updateGridButton(R.id.cell_0_2, 0, 2);
        updateGridButton(R.id.cell_1_0, 1, 0);
        updateGridButton(R.id.cell_1_1, 1, 1);
        updateGridButton(R.id.cell_1_2, 1, 2);
        updateGridButton(R.id.cell_2_0, 2, 0);
        updateGridButton(R.id.cell_2_1, 2, 1);
        updateGridButton(R.id.cell_2_2, 2, 2);

    }

    fun updateGridButton(id: Int, row: Int, col: Int) {
        val button = app.findViewById<Button>(id);
        val currentIcon = TicTacToe.getIconAt(row, col);
        var iconText = "";

        if(currentIcon != TicTacToe.ICON) {
            iconText = if(currentIcon == TicTacToe.ICON_X) "X" else "O";
        }

        button.text = iconText;
    }

    fun checkGridButton(view: Button): Pair<Int, Int>? {
        return when(view.id) {
            R.id.cell_0_0 -> Pair(0,0)
            R.id.cell_0_1 -> Pair(0,1)
            R.id.cell_0_2 -> Pair(0,2)
            R.id.cell_1_0 -> Pair(1,0)
            R.id.cell_1_1 -> Pair(1,1)
            R.id.cell_1_2 -> Pair(1,2)
            R.id.cell_2_0 -> Pair(2,0)
            R.id.cell_2_1 -> Pair(2,1)
            R.id.cell_2_2 -> Pair(2,2)

            else -> null
        }
    }


}