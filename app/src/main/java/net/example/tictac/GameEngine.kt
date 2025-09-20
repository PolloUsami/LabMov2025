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


                        // TODO: iniciar el juego en si (chamba de franklin)

                    }

                }

                back.setOnClickListener { show(R.layout.activity_main) }

            }
        }
    }

}