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
import android.widget.RatingBar;
import android.widget.TextView;

import android.example.pietjesbak.constant.Dices;
import android.example.pietjesbak.utils.DiceRoller;

import org.w3c.dom.Text;

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
    public String Score;


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

                    TextView scorePlayer1 = (TextView) findViewById(R.id.scorePlayer1);
                    TextView scorePlayer2 = (TextView) findViewById(R.id.scorePlayer2);
                    TextView scoreType1 = (TextView) findViewById(R.id.scoreType1);
                    TextView scoreType2 = (TextView) findViewById(R.id.scoreType2);
                    scorePlayer1.setText("");
                    scorePlayer2.setText("");
                    scoreType1.setText("");
                    scoreType2.setText("");

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


                            won2.setVisibility(View.GONE);
                            lose1.setVisibility(View.GONE);
                            won1.setVisibility(View.GONE);
                            lose2.setVisibility(View.GONE);
                            even1.setVisibility(View.GONE);
                            even2.setVisibility(View.GONE);
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
                    scorePlayer1();
                }

                //ales de speler na 2x gooien stopt, krijgt de andere speler MAX 2 kansen
                else if (click == 2){
                    click = 1;
                    scorePlayer1();

                }

                //scorePlayer1();

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
                    Log.i("click",""+click);
                    scorePlayer2();
                }

                if (click ==2){
                    ready2.setVisibility(View.VISIBLE);
                    Log.i("click",""+click);
                    scorePlayer2();
                }

                if(click ==3){
                    rollButton2.setVisibility(View.GONE);
                    rollButton.setVisibility(View.VISIBLE);
                    ready2.setVisibility(View.GONE);
                    Log.i("click",""+click);
                    click = 0;
                    Log.i("click",""+click);
                    scorePlayer2();
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
                    scorePlayer2();

                }

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
                Score = "AZEN";
            }

            //ZAND
            else if(dice1 == dice2 && dice2 == dice3){
                Points = dice1 + dice2 + dice3;
                Score = "ZAND";
            }

            //SOIXANTE NEUF
            else if (dice1 == 4 && dice2 == 5 && dice3 == 6
                    || dice1 == 6 && dice2 == 4 && dice3 == 5
                    || dice1 == 5 && dice2 == 6 && dice3 == 4
                    || dice1 == 6 && dice2 == 5 && dice3 == 4
                    || dice1 == 5 && dice2 == 4 && dice3 == 6
                    || dice1 == 4 && dice2 == 6 && dice3 == 5){
                //Points = ;
                Score = "SOIXANTE-NEUF";

            }
            //ZEVEN
            else if (dice1 == 2 && dice2 == 2 && dice3 == 3
                    || dice1 == 3 && dice2 == 2 && dice3 == 2
                    || dice1 == 2 && dice2 == 3 && dice3 == 2){
                Points = dice1 + dice2 + dice3;
                Score = "ZEVEN";

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
                Score = "";

            }

    }


    private void scorePlayer1() {


        //score in textview steken
            TextView scorePlayer1 = (TextView) findViewById(R.id.scorePlayer1);
            scorePlayer1.setText(""+Points);
            String score1 = scorePlayer1.getText().toString();
            Integer.parseInt(score1);

        //benaming van score wegschrijven
            TextView scoreType1 = (TextView) findViewById(R.id.scoreType1);
            scoreType1.setText(""+Score);

    }

    private void scorePlayer2() {

        //score in textview steken
            TextView scorePlayer2 = (TextView) findViewById(R.id.scorePlayer2);
            scorePlayer2.setText(""+Points);
            String score2 = scorePlayer2.getText().toString();
            Integer.parseInt(score2);

        //benaming van score wegschrijven
            TextView scoreType2 = (TextView) findViewById(R.id.scoreType2);
            scoreType2.setText(""+Score);

    }


    private void winPlayer1(){

        //win en verliezersboodschap
        TextView won1 = (TextView) findViewById(R.id.Won1);
        TextView lose2 = (TextView) findViewById(R.id.Lose2);

        won1.setVisibility(View.VISIBLE);
        lose2.setVisibility(View.VISIBLE);

        //ster aftrekken bij speler 1
        RatingBar scoreBar1 = (RatingBar) findViewById(R.id.score1);
        Float scoreBar1a = scoreBar1.getRating();
        Float newScore = scoreBar1a - 1;
        scoreBar1.setRating(newScore);


        //Sterren speler 2


        if(newScore == 0.0){

            TextView player1 = (TextView) findViewById(R.id.addPlayer1);
            String player1text = player1.getText().toString();;

            TextView winner = (TextView) findViewById(R.id.winner);

            Intent intent = new Intent(getApplicationContext(), Winner.class);
            intent.putExtra("winner", player1text);
            startActivity(intent);
        }

    }


    private void winPlayer2(){

        //win en verliezersboodschap
            TextView lose1 = (TextView) findViewById(R.id.Lose1);
            TextView won2 = (TextView) findViewById(R.id.Won2);

        // win voor speler 2, verloren voor speler 1
            won2.setVisibility(View.VISIBLE);
            lose1.setVisibility(View.VISIBLE);

        //ster aftrekken bij speler 2
            RatingBar scoreBar2 = (RatingBar) findViewById(R.id.score2);
            Float scoreBar1a = scoreBar2.getRating();
            Float newScore = scoreBar1a - 1;
            scoreBar2.setRating(newScore);

        //Sterren speler 2


            if(newScore == 0.0) {

                TextView player2 = (TextView) findViewById(R.id.addPlayer2);
                String player2text = player2.getText().toString();
                ;

                TextView winner = (TextView) findViewById(R.id.winner);

                Intent intent = new Intent(getApplicationContext(), Winner.class);
                intent.putExtra("winner", player2text);
                startActivity(intent);
            }
    }

    private void winNoOne(){
        TextView even2 = (TextView) findViewById(R.id.Even2);
        TextView even1 = (TextView) findViewById(R.id.Even1);

        even1.setVisibility(View.VISIBLE);
        even2.setVisibility(View.VISIBLE);
    }


    private void stageScore(){
     //speler 1 --------
        //score speler 1 ophalen + omzetten naar integer
            TextView scorePlayer1 = (TextView) findViewById(R.id.scorePlayer1);
            String score1 = scorePlayer1.getText().toString();
            int score1int = Integer.parseInt(score1);
            Log.i("score speler 1",""+ score1int);

        //benaming score speler 1 ophalen
            TextView scoreType1 = (TextView) findViewById(R.id.scoreType1);
            String scoreType1String = scoreType1.getText().toString();
            Log.i("naam score speler 1",""+ scoreType1String);

    //speler 2 --------
        //score speler 2 ophalen + omzetten naar integer
            TextView scorePlayer2 = (TextView) findViewById(R.id.scorePlayer2);
            String score2 = scorePlayer2.getText().toString();
            int score2int = Integer.parseInt(score2);
            Log.i("score speler 2",""+ score2int);

        //benaming score speler 2 ophalen
            TextView scoreType2 = (TextView) findViewById(R.id.scoreType2);
            String scoreType2String = scoreType1.getText().toString();

        Log.i("naam score speler 2",""+ scoreType2String);


     //AZEN
        if(scoreType1String.equals("AZEN") || scoreType2String.equals("AZEN")){


                //speler1 heeft azen, speler 2 niet
                    if(scoreType1String.equals("AZEN")
                            && !scoreType2String.equals("AZEN")){

                        winPlayer1();
                    }

                //speler 2 heeft azen, speler 1 niet
                    if(scoreType2String.equals("AZEN")
                            && !scoreType1String.equals("AZEN")){

                        winPlayer2();
                    }

                    if(scoreType1String.equals("AZEN") && scoreType2String.equals("AZEN")){

                        winNoOne();

                    }



        }

     //SOIXANTE-NEUF
        else if(scoreType1String.equals("SOIXANTE-NEUF") || scoreType2String.equals("SOIXANTE-NEUF")){


            if(scoreType1String.equals("SOIXANTE-NEUF") && scoreType2String.equals("SOIXANTE-NEUF")){
                winNoOne();
            }

            if(scoreType1String.equals("SOIXANTE-NEUF")
                    && !scoreType2String.equals("SOIXANTE-NEUF")
                    ||
                    scoreType1String.equals("SOIXANTE-NEUF")
                    && !scoreType2String.equals("AZEN")){

                winPlayer1();

            }


            if(scoreType2String.equals("SOIXANTE-NEUF")
                    && !scoreType1String.equals("SOIXANTE-NEUF")
                    && !scoreType1String.equals("AZEN")){

                winPlayer2();
            }

        }

    //ZAND
       else if(scoreType1String.equals("ZAND") || scoreType2String.equals("ZAND")){


                    if(scoreType1String.equals("ZAND")
                            && !scoreType2String.equals("ZAND")
                            && !scoreType2String.equals("SOIXANTE-NEUF")){

                        winPlayer1();
                    }

                    if(scoreType2String.equals("ZAND")
                            && scoreType1String != "AZEN"
                            && !scoreType1String.equals("SOIXANTE-NEUF")
                            && !scoreType1String.equals("ZAND")){

                        winPlayer2();
                    }


                    if(scoreType1String.equals("ZAND")
                            && scoreType2String.equals("ZAND")
                            && score1int > score2int){

                        winPlayer1();

                    }

                    if(scoreType1String.equals("ZAND")
                            && scoreType2String.equals("ZAND")
                            && score1int < score2int){

                        winPlayer2();

                    }

                    if(scoreType1String.equals("ZAND")
                        && scoreType2String.equals("ZAND")
                        && score1int == score2int){

                        winNoOne();

                    }
        }


    if (scoreType1String.equals("") && scoreType2String.equals("") ){

            if (score1int < score2int) {

                //win en verliezersboodschap
                TextView lose1 = (TextView) findViewById(R.id.Lose1);
                TextView won2 = (TextView) findViewById(R.id.Won2);

                // win voor speler 2, verloren voor speler 1
                won2.setVisibility(View.VISIBLE);
                lose1.setVisibility(View.VISIBLE);

                //bij speler 2 gaat er een ster weg
                RatingBar scoreBar2 = (RatingBar) findViewById(R.id.score2);
                Float scoreBar2a = scoreBar2.getRating();
                Float newScore = scoreBar2a - 1;

                //nieuw steraantal meegeven
                scoreBar2.setRating(newScore);


                if (newScore == 0.0) {

                    TextView player2 = (TextView) findViewById(R.id.addPlayer2);
                    String player2text = player2.getText().toString();
                    ;

                    TextView winner = (TextView) findViewById(R.id.winner);

                    Intent intent = new Intent(getApplicationContext(), Winner.class);
                    intent.putExtra("winner", player2text);
                    startActivity(intent);
                }
            } else if (score1int > score2int) {

                TextView lose2 = (TextView) findViewById(R.id.Lose2);
                TextView won1 = (TextView) findViewById(R.id.Won1);

                won1.setVisibility(View.VISIBLE);
                lose2.setVisibility(View.VISIBLE);


                //ster aftrekken bij speler 1
                RatingBar scoreBar1 = (RatingBar) findViewById(R.id.score1);
                Float scoreBar1a = scoreBar1.getRating();
                Float newScore = scoreBar1a - 1;
                scoreBar1.setRating(newScore);

            } else if (score1int == score2int) {

                winNoOne();
            }

        }

    }


}
