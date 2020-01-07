package android.example.pietjesbak;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.widget.TextView;

public class Gamescreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamescreen);


        //vraag de waarden op van de inputvelden (players)
        Intent intent = getIntent();
        String player1 = intent.getStringExtra("player1");
        String player2 = intent.getStringExtra("player2");

        //tekstvelden van bestemming opvragen
        TextView getPlayer1 = (TextView) findViewById(R.id.addPlayer1);
        TextView getPlayer2 = (TextView) findViewById(R.id.addPlayer2);

        //bestemde tekstvelden aanpassen met de spelers
        getPlayer1.setText(player1);
        getPlayer2.setText(player2);
    }
}
