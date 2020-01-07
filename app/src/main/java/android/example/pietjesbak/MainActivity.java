package android.example.pietjesbak;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    //constante aanmaken voor string
        public static final String PLAYER1 = "player1";
        public static final String PLAYER2 = "player2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.startGame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Als er geklikt wordt op de button met id startGame
                //dan wordt de functie startGame opgeroepen
                startGame();

            }
        });
    }

    public void startGame(){
        //inhoud van inputvelden opvragen
            EditText addPlayer1 = (EditText) findViewById(R.id.addPlayer1);
            EditText addPlayer2 = (EditText) findViewById(R.id.addPlayer2);
        //opgehaalde tekst omzetten in string
            String player1 = addPlayer1.getText().toString();
            String player2 = addPlayer2.getText().toString();



        //intent maken die we de content 'this' doorgeven
        //en de klasse doorgeven van de activity waar je naartoe wilt
            Intent intent = new Intent(getApplicationContext(),Gamescreen.class);

        //value van inputvelden meegeven
            intent.putExtra("player1",player1);
            intent.putExtra("player2",player2);

        //activity opstarten met de intent
            startActivity(intent);
    }
}
