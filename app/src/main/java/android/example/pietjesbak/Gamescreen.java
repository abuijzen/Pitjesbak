package android.example.pietjesbak;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.example.pietjesbak.constant.Dices;
import android.example.pietjesbak.utils.DiceRoller;

public class Gamescreen extends AppCompatActivity {

    //diceroller gebruiken in deze activity
    private DiceRoller diceRoller;

    //array van image views
    ImageView[] diceViews;

    //imageView instantie, toegang tot de imageviews vanuit de layout
    ImageView dieOneView;
    ImageView dieTwoView;
    ImageView dieThreeView;

    //button instantie
    Button rollButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamescreen);


    //spelernamen invullen en meenemen naar de gamescreen

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


    //dobbelstenen implementeren
        //initialiseren van diceroller
        diceRoller = new DiceRoller();
        diceViews = new ImageView[Dices.NUMBER_DICE];

        //methods
        setupButtons();
        setupImageViews();

    }

    private void setupButtons(){
        rollButton = findViewById(R.id.rollButton);

            //als de knop om te dobbelen wordt ingeklikt gebeurd er dit...
            rollButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View arg0){

                        //diceroller moet de dobbelstenen doen rollen
                        diceRoller.rollDice();

                        //afbeelding van de dobbelsteen veranderen
                        changeDiceImages();

                    }
            });
    }

    //instellen van imageviews
    private void setupImageViews(){

        //dieoneview = view van eerste dobbelsteen
        //linken aan de id van het layout-item
        dieOneView = findViewById(R.id.dieOneView);
        dieTwoView = findViewById(R.id.dieTwoView);
        dieThreeView = findViewById(R.id.dieThreeView);

        //array invullen
        diceViews[0] = dieOneView;
        diceViews[1] = dieTwoView;
        diceViews[2] = dieThreeView;
    }

    private int getDiceId(int number){
        //krijg de juiste image die gelinkt is aan het getal dat gegooid is
        String name = "die" + number;

        //naam van de image = die + nummer die we aan de method geven
        return getResources().getIdentifier(name, "drawable", getPackageName());

    }

    //verander de image van elke dobbelsteen die wordt gegooid
    private void changeDiceImages(){
        for (int i=0; i < Dices.NUMBER_DICE; i++){
            diceViews[i].setImageResource(getDiceId(
                    //krijg de dice id, uit functie die al geschreven was
                    diceRoller.getDice()[i].getDieResult()
            ));
        }
    }
}
