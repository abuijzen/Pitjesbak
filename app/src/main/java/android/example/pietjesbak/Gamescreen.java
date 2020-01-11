package android.example.pietjesbak;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button rollButton2;
    Button ready1;
    Button ready2;


    //aantal keer gegooid
    int click = 0;
    int clickready = 0;


    //tellen van aantal ogen + beschrijving van speciale scores
    int Points;
    String Score;


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


        //score boodschappen
        TextView lose1 = (TextView) findViewById(R.id.Lose1);
        TextView lose2 = (TextView) findViewById(R.id.Lose2);
        TextView won1 = (TextView) findViewById(R.id.Won1);
        TextView won2 = (TextView) findViewById(R.id.Won2);
        TextView even1 = (TextView) findViewById(R.id.Even1);
        TextView even2 = (TextView) findViewById(R.id.Even2);

        won1.setVisibility(View.GONE);
        won2.setVisibility(View.GONE);
        lose1.setVisibility(View.GONE);
        lose2.setVisibility(View.GONE);
        even1.setVisibility(View.GONE);
        even2.setVisibility(View.GONE);


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
        rollButton2 = findViewById(R.id.rollButton2);
        ready1 = findViewById(R.id.ready1);
        ready2 = findViewById(R.id.ready2);

        //zichtbaarheid knoppen bij start van het spel
        rollButton2.setVisibility(View.GONE);
        ready1.setVisibility(View.GONE);
        ready2.setVisibility(View.GONE);



            //SPELER 1
            //als de knop om te dobbelen wordt ingeklikt gebeurd er dit...
            rollButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View arg0){


                        click = click + 1;

                        if (click == 1){

                            //alles terug leeg maken voor het volgende spelletje
                            ready1.setVisibility(View.VISIBLE);
                            TextView lose1 = (TextView) findViewById(R.id.Lose1);
                            TextView lose2 = (TextView) findViewById(R.id.Lose2);
                            TextView won1 = (TextView) findViewById(R.id.Won1);
                            TextView won2 = (TextView) findViewById(R.id.Won2);
                            TextView even1 = (TextView) findViewById(R.id.Even1);
                            TextView even2 = (TextView) findViewById(R.id.Even2);
                            TextView scorePlayer1 = (TextView) findViewById(R.id.scorePlayer1);
                            TextView scorePlayer2 = (TextView) findViewById(R.id.scorePlayer2);


                            won2.setVisibility(View.GONE);
                            lose1.setVisibility(View.GONE);
                            won1.setVisibility(View.GONE);
                            lose2.setVisibility(View.GONE);
                            even1.setVisibility(View.GONE);
                            even2.setVisibility(View.GONE);
                            scorePlayer1.setText("");
                            scorePlayer2.setText("");
                        }


                        if(click == 3){
                            rollButton.setVisibility(View.GONE);
                            rollButton2.setVisibility(View.VISIBLE);
                            ready1.setVisibility(View.GONE);
                            click= 0;
                        }

                        //diceroller moet de dobbelstenen doen rollen
                        diceRoller.rollDice();

                        //afbeelding van de dobbelsteen veranderen
                        changeDiceImages();

                        scorePlayer1();

                    }
            });

        //SPELER 1
        //als speler 1 kiest om eerder te stoppen met dobbelen
        ready1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){

                rollButton.setVisibility(View.GONE);
                ready1.setVisibility(View.GONE);
                rollButton2.setVisibility(View.VISIBLE);


                //als de speler na 1x gooien stopt, krijgt de andere speler MAX 1 kans
                if (click == 1){
                    click = 2;
                }

                //ales de speler na 2x gooien stopt, krijgt de andere speler MAX 2 kansen
                else if (click == 2){
                    click = 1;

                }

                scorePlayer1();

            }
        });




            //SPELER 2
            //als de knop om te dobbelen wordt ingeklikt gebeurd er dit...
            rollButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){


                click = click + 1;

                if(click == 1){
                    ready2.setVisibility(View.VISIBLE);
                }

                if (click ==2){
                    ready2.setVisibility(View.VISIBLE);
                }

                if(click ==3){
                    rollButton2.setVisibility(View.GONE);
                    rollButton.setVisibility(View.VISIBLE);
                    ready2.setVisibility(View.GONE);
                    click = 0;
                    stageScore();

                }


                //diceroller moet de dobbelstenen doen rollen
                diceRoller.rollDice();

                //afbeelding van de dobbelsteen veranderen
                changeDiceImages();

                scorePlayer2();


            }
        });

        //SPELER 2
        //als speler 1 kiest om eerder te stoppen met dobbelen
        ready2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){


                clickready = clickready + 1;
                if(clickready == 1){
                    rollButton2.setVisibility(View.GONE);
                    ready2.setVisibility(View.GONE);
                    rollButton.setVisibility(View.VISIBLE);
                    //ready1.setVisibility(View.VISIBLE);

                }
                scorePlayer2();
                click = 0;
                clickready = 0;
                stageScore();

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


        //**** SPELREGELS IMPLEMENTEREN  *****

            //dobbelstenen ophalen per id met de gegooide waarde
            int dice1 = diceRoller.getDice()[0].getDieResult();
            int dice2 = diceRoller.getDice()[1].getDieResult();
            int dice3 = diceRoller.getDice()[2].getDieResult();


            //AAP
            if(dice1 == 1 && dice2 == 1 && dice3== 1){

                Points = 300;
                Score = "3 Azen";

            }

            //ZAND
            else if(dice1 == dice2 && dice2 == dice3){
                Points = dice1 + dice2 + dice3;
                Score = "Zand ";
            }

            //SOIXANTE NEUF
            else if (dice1 == 4 && dice2 == 5 && dice3 == 6
                    || dice1 == 6 && dice2 == 4 && dice3 == 5
                    || dice1 == 5 && dice2 == 6 && dice3 == 4
                    || dice1 == 6 && dice2 == 5 && dice3 == 4
                    || dice1 == 5 && dice2 == 4 && dice3 == 6
                    || dice1 == 4 && dice2 == 6 && dice3 == 5){
                Points = 69;
                Score = "SOIXANTE NEUF ";

            }
            //ZEVEN
            else if (dice1 == 2 && dice2 == 2 && dice3 == 3
                    || dice1 == 3 && dice2 == 2 && dice3 == 2
                    || dice1 == 2 && dice2 == 3 && dice3 == 2){
                Points = dice1 + dice2 + dice3;
                Score = "Zeven ";

            }
            //gewone punttelling
            else{

                //1 = 100 punten
                if(dice1 ==1){
                    dice1=100;
                }

                if(dice2 == 1){
                    dice2 = 100;
                }

                if(dice3 == 1){
                    dice3 =100;
                }

                //6 = 60punten
                if(dice1 == 6){
                    dice1 =60;
                }

                if(dice2 == 6){
                    dice2 = 60;
                }

                if(dice3 == 6){
                    dice3 =60;
                }

                Points = dice1 + dice2 + dice3;
                Score = "Score: ";

            }

    }


    private String scorePlayer1() {


        TextView scorePlayer1 = (TextView) findViewById(R.id.scorePlayer1);

        scorePlayer1.setText(""+Points);

        String score1 = scorePlayer1.getText().toString();

        Integer.parseInt(score1);


        TextView scoreType1 = (TextView) findViewById(R.id.scoreType1);
        scoreType1.setText(""+Score);


        Log.i("speler1",score1);

        return score1;

    }

    private String scorePlayer2() {

        TextView scorePlayer2 = (TextView) findViewById(R.id.scorePlayer2);

        scorePlayer2.setText(""+Points);

        String score2 = scorePlayer2.getText().toString();

        Integer.parseInt(score2);

        Log.i("speler2",score2);




        TextView scoreType2 = (TextView) findViewById(R.id.scoreType2);
        scoreType2.setText(""+Score);

        return score2;

    }



    private void stageScore(){

        TextView scorePlayer1 = (TextView) findViewById(R.id.scorePlayer1);
        String score1 = scorePlayer1.getText().toString();
        int score1int = Integer.parseInt(score1);


        TextView scorePlayer2 = (TextView) findViewById(R.id.scorePlayer2);
        String score2 = scorePlayer2.getText().toString();
        int score2int = Integer.parseInt(score2);


        if(score1int < score2int){

            TextView lose1 = (TextView) findViewById(R.id.Lose1);
            TextView won2 = (TextView) findViewById(R.id.Won2);

            won2.setVisibility(View.VISIBLE);
            lose1.setVisibility(View.VISIBLE);
        }

        else if(score1int > score2int){

            TextView lose2 = (TextView) findViewById(R.id.Lose2);
            TextView won1 = (TextView) findViewById(R.id.Won1);

            won1.setVisibility(View.VISIBLE);
            lose2.setVisibility(View.VISIBLE);
        }

        else if(score1int == score2int ){

            TextView even2 = (TextView) findViewById(R.id.Even2);
            TextView even1 = (TextView) findViewById(R.id.Even1);

            even1.setVisibility(View.VISIBLE);
            even2.setVisibility(View.VISIBLE);
        }




    }

}
