package com.tushar.connect3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean isRedPlayerTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        TextView playerTurnValueLabel = (TextView)findViewById(R.id.playerTurnValueLabel);
        ImageView playerToken = (ImageView) findViewById(R.id.playerToken);

        if(isRedPlayerTurn) {
//            playerTurnValueLabel.setTextColor(Color.RED);
            //playerTurnValueLabel.setBackgroundColor(Color.RED);
            playerToken.setImageResource(R.drawable.red);
            playerTurnValueLabel.setText("RED");
        }else{
//            playerTurnValueLabel.setTextColor(Color.YELLOW);
            //playerTurnValueLabel.setBackgroundColor(Color.YELLOW);
            playerToken.setImageResource(R.drawable.yellow);
            playerTurnValueLabel.setText("YELLOW");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        TextView playerTurnValueLabel = (TextView)findViewById(R.id.playerTurnValueLabel);
        ImageView playerToken = (ImageView) findViewById(R.id.playerToken);
        counter.setTranslationY(-1000f);
        counter.setAlpha(0.0f);
        int imageResource = -1;
        if(isRedPlayerTurn){
//            playerTurnValueLabel.setTextColor(Color.YELLOW);
            //playerTurnValueLabel.setBackgroundColor(Color.YELLOW);
            playerToken.setImageResource(R.drawable.yellow);
            playerTurnValueLabel.setText("YELLOW");
            imageResource = R.drawable.red;
            isRedPlayerTurn = false;
        }else{
//            playerTurnValueLabel.setTextColor(Color.RED);
            //playerTurnValueLabel.setBackgroundColor(Color.RED);
            playerToken.setImageResource(R.drawable.red);
            playerTurnValueLabel.setText("RED");
            imageResource = R.drawable.yellow;
            isRedPlayerTurn = true;
        }
        counter.setImageResource(imageResource);
        counter.animate().alpha(1.0f).translationYBy(1000f).rotation(180).setDuration(400);
    }
}
