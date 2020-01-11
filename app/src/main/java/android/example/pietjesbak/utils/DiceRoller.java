package android.example.pietjesbak.utils;

import android.example.pietjesbak.constant.Dices;
import android.example.pietjesbak.models.Die;
import android.util.Log;

import java.util.Random;

import static android.util.Log.w;

public class DiceRoller {
    //houd een array van dice bij
    private Die[] dice;

    //rolt de dobbelstenen random
    private Random random;

    //constructor maken
    public DiceRoller(){
        //aantal dobbelstenen (ingegeven bij dices klasse)
        dice = new Die[Dices.NUMBER_DICE];

        //instantie van alle dobbelstenen
        dice[0] = new Die();
        dice[1] = new Die();
        dice[2] = new Die();

        //random class initialiseren
        random = new Random();

        //methode die uitgevoerd wordt
        rollDice();
    }


        //random nummer gereren voor dobbelstenen tussen 1 en 6
        public int generateDiceEyes(){

            //verander het return getal in 1 voor een aap!
            //return 1;

            //Zand
            //return 2;

            //soixante neuf
            //return random.nextInt((6-4)+1)+4;

            //zeven
            //return random.nextInt((3-2)+1)+2;

            //random
            return random.nextInt(6) + 1;

        }


        public void rollDice(){

            //cijfers genereren totdat alle dobbelstenen een nummer hebben
            for (int i = 0; i < Dices.NUMBER_DICE; i++){
                //cijfers moeten een random getal krijgen
                //gebruik maken van de bestaande functie generateDiceEyes()
                dice[i].setDieResult(generateDiceEyes());

            }
        }

    public Die[] getDice() {
        return dice;
    }
}
