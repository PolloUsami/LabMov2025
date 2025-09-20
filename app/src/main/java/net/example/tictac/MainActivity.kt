package net.example.tictac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        val engine: GameEngine = GameEngine(this);


        engine.show(R.layout.activity_main);


    }
}