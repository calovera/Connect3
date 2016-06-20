package com.carloslovera.connect3;

import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 0 = yellow player, 1 = red player
    int activePlayer = 0;
    boolean activeGame = true;

    // starting board, -1 for unplayed
    int[] gameState = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        //System.out.println(counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString()); // get the position

        if(gameState[tappedCounter] == -1 && activeGame) { // check if no one has played this position.
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(600);

            // check if a player wins:
            for(int[] winningPosition:winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != -1) {
                    // if a player wins, prompt the user to play again and display the winner
                    TextView winnerString = (TextView) findViewById(R.id.winnerText);

                    String winner = "Yellow";
                    if (gameState[winningPosition[0]] == 1) {
                        winner = "Red";
                    }

                    winnerString.setText(winner + " win!");

                    LinearLayout gameOverTable = (LinearLayout) findViewById(R.id.playAgainLayout);
                    gameOverTable.setAlpha(0f);
                    gameOverTable.setVisibility(View.VISIBLE);
                    gameOverTable.animate().alphaBy(1f).setDuration(600);
                    activeGame = false;
                } else { // check if there is a tie
                    boolean gameOver = true;
                    for (int position : gameState) {
                        if (position == -1) {
                            gameOver = false;
                        }
                    }
                    if (gameOver) {
                        TextView winnerString = (TextView) findViewById(R.id.winnerText);
                        winnerString.setText(" Its a draw!");
                        LinearLayout gameOverTable = (LinearLayout) findViewById(R.id.playAgainLayout);
                        gameOverTable.setAlpha(0f);
                        gameOverTable.setVisibility(View.VISIBLE);
                        gameOverTable.animate().alphaBy(1f).setDuration(600);
                        activeGame = false;
                    }
                } // end else
            } // end checking for winner or draw
        } // end for adding player
    } // end [dropIn]


    // play again function
    public void playAgain(View view){
        LinearLayout gameOverTable = (LinearLayout) findViewById(R.id.playAgainLayout);
        gameOverTable.setVisibility(View.INVISIBLE);
        activePlayer = 0;

        for(int i = 0;i < gameState.length;i++){
            gameState[i] = -1;
        }
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++){ // iterate for the views inside the grid
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
        activeGame = true;
    } // end of [playAgain]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
