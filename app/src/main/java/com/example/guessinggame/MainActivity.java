package com.example.guessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonOk;
    private EditText editTextNumber;
    private TextView textViewIndication;
    private ProgressBar progressBarTentatives;
    private TextView textViewTentatives;
    private TextView textViewScore;
    private ListView listViewHistorique;
    private List<String> listHistorique=new ArrayList<>();
    private int score;
    private int counter;
    private int secret;
    private int maxTentative=10;
    private ArrayAdapter<String> model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOk=findViewById(R.id.buttonOk);
        editTextNumber=findViewById(R.id.editTextNumber);
        textViewIndication=findViewById(R.id.textViewIndication);
        progressBarTentatives=findViewById(R.id.progressBarTentatives);
        textViewTentatives=findViewById(R.id.textViewTentatives);
        listViewHistorique=findViewById(R.id.listViewHistorique);
        textViewScore=findViewById(R.id.textViewScoreVlue);

        model = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listHistorique);
        listViewHistorique.setAdapter(model);
        initialisation();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number=Integer.parseInt(editTextNumber.getText().toString());
                if(number>secret){
                          textViewIndication.setText("too big");
                          editTextNumber.setText("");
                }else if (number<secret){
                    textViewIndication.setText("too small");
                    editTextNumber.setText("");
                }else {

                    score+=5;
                    textViewScore.setText(String.valueOf(score));
                    rejouer();
                }
                listHistorique.add(counter+"=>"+number);
                model.notifyDataSetChanged();
                ++counter;
                textViewTentatives.setText(String.valueOf(counter));
                progressBarTentatives.setProgress(counter);
                if (counter>maxTentative){
                    rejouer();
                }
            }
        });
    }

    private void rejouer() {
        editTextNumber.setText("");
        textViewIndication.setText("");
        AlertDialog alertDialog= new AlertDialog.Builder(this).create();
        alertDialog.setTitle("BRAVO!! rejouer?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initialisation();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }

    private void initialisation() {
        secret=1+((int)(Math.random()*100));
        counter=1;
        listHistorique.clear();model.notifyDataSetChanged();
        textViewTentatives.setText(String.valueOf(counter));
        progressBarTentatives.setProgress(counter);
        progressBarTentatives.setMax(maxTentative);
        textViewScore.setText(String.valueOf(score));
    }
}
