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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Player currentPlayer;
    boolean isGameActive = true;
    Player gameState[] = new Player[9];
    Integer [][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

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

        //initialize game state to undefined
        resetGameState();

        currentPlayer = Player.RED;
        playerToken.setImageResource(R.drawable.red);
        playerTurnValueLabel.setText("RED");


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
        if(isGameActive) {
            Player previousPlayer;
            previousPlayer = currentPlayer;
            ImageView counter = (ImageView) view;
            int counterTag = Integer.parseInt(counter.getTag().toString());
            if (gameState[counterTag] != Player.UNDEFINED) {
                Toast.makeText(getApplicationContext(), "Dont cheat!!! that place is already taken :P", Toast.LENGTH_LONG).show();
            } else {

                gameState[counterTag] = currentPlayer;
                //check if player won
                ArrayList<Integer> exisitingPlayerPositions = getPositions(currentPlayer);

                TextView playerTurnValueLabel = (TextView) findViewById(R.id.playerTurnValueLabel);
                ImageView playerToken = (ImageView) findViewById(R.id.playerToken);
                //counter.setTranslationY(-1000f);
                counter.setAlpha(0.0f);
                int imageResource = -1;
                if (currentPlayer == Player.RED) {
                    playerToken.setImageResource(R.drawable.yellow);
                    playerTurnValueLabel.setText("YELLOW");
                    imageResource = R.drawable.red;
                    currentPlayer = Player.YELLOW;
                } else {
                    playerToken.setImageResource(R.drawable.red);
                    playerTurnValueLabel.setText("RED");
                    imageResource = R.drawable.yellow;
                    currentPlayer = Player.RED;
                }
                counter.setImageResource(imageResource);
                counter.animate().
                        alpha(1.0f).
                        //translationYBy(1000f).
                        rotation(180).
                        setDuration(400);
                if (isWinner(exisitingPlayerPositions)) {
                    LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    TextView winningMessage = (TextView) findViewById(R.id.winningMessage);
                    winningMessage.setText(previousPlayer + " wins!!!");
                    playAgainLayout.setVisibility(View.VISIBLE);
                    isGameActive = false;
                }else if(isDraw()){
                    LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    TextView winningMessage = (TextView) findViewById(R.id.winningMessage);
                    winningMessage.setText("Game Drawn");
                    playAgainLayout.setVisibility(View.VISIBLE);
                    isGameActive = false;
                }
                ;
            }
        }

    }
    private ArrayList<Integer> getPositions(Player player){
        ArrayList<Integer> playerPositions = new ArrayList<Integer>();
        for(int i=0; i<gameState.length;i++){
            if(gameState[i] == player){
                playerPositions.add(i);
            }
        }
        return playerPositions;
    }
    private boolean isWinner(ArrayList<Integer> playerPositions){
        //System.out.println("winningPositions.length : " + winningPositions.length);
        boolean winnerFlag = false;
        for(int i=0; i < winningPositions.length; i++){
            if(playerPositions.containsAll(Arrays.asList(winningPositions[i]))){
                winnerFlag = true;
            }
        }
        return winnerFlag;
    }

    private boolean isDraw(){
        boolean drawFlag = true;
        for(int i=0; i < gameState.length; i++){
            //there is atleast one cell which is undefined where player can plase a coin
            if(gameState[i] == Player.UNDEFINED){
                drawFlag = false;
            }
        }
        return drawFlag;
    }

    public void playAgain(View view){
        resetGameState();
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        ((LinearLayout) findViewById(R.id.playAgainLayout)).setVisibility(View.INVISIBLE);
        for (int i=0; i<gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
        isGameActive = true;
    }

    private void resetGameState() {
        for (int i=0; i<gameState.length;i++){
            gameState[i] = Player.UNDEFINED;
        }
    }

    public void exit(View view){
        moveTaskToBack(true);
        finish();
    }
}
