package android.example.pietjesbak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


    public class Winner extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_winner);


            Intent intent = getIntent();
            String nameWinner = intent.getStringExtra("winner");

            TextView winner = (TextView) findViewById(R.id.winner);
            winner.setText(nameWinner);


            Button again = (Button) findViewById(R.id.Again);

            again.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }
            });

        }
    }