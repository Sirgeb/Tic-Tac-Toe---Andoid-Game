/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mrgabriel.tic_tac_toe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button[][] buttons = new Button[3][3];
    private int roundCount;
    private boolean player1Turn = true;
    private int player1Points;
    private int player2Points;
    private TextView player1TextView;
    private TextView player2TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // grab players textView
        player1TextView = (TextView) findViewById(R.id.player1_text_view);
        player2TextView = (TextView) findViewById(R.id.player2_text_view);

        // iterate through button ID(s) and grab resourceID(s)
        for (int x = 0; x < 3; x++) {

            for (int y = 0; y <3; y++) {
                String buttonID = "button_" + x + y;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[x][y] = (Button) findViewById(resourceID);
                buttons[x][y].setOnClickListener(this);
            }
        }

        // grab the reset button (New Game)
        // set an onclickListener on the resetButton (New Game)
        Button resetButton = (Button) findViewById(R.id.new_game_button);
        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    @Override
    public void onClick(View v) {

        // check if the clicked button has an empty string
        if (!((Button) v).getText().toString().equals("") ) {
            return;
        }

        // Assign each player a letter
        // player1 = X
        // player2 = O
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;


        if (aWinner()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    // check if there is a winner
    private boolean aWinner() {
        String[][] field = new String[3][3];

        for (int x = 0; x < 3; x++) {

            for (int y = 0; y < 3; y++) {
                field[x][y] = buttons[x][y].getText().toString();
            }
        }

        for (int x = 0; x < 3; x++) {
            if (field[x][0].equals(field[x][1]) && !field[x][0].equals(field[x][2]) && field[x][0].equals("")) {
                return true;
            }
        }

        for (int x = 0; x < 3; x++) {
            if (field[0][x].equals(field[1][x]) && !field[0][x].equals(field[2][x]) && field[0][x].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && !field[0][0].equals(field[2][2]) && field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && !field[0][2].equals(field[2][0]) && field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    // Increment point(s) when player 1 wins
    // display a toast message
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    // Increment point(s) when player 2 wins
    // display a toast message
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }

    // display a toast message on Draw
    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();

    }


    private void updatePointsText() {
        player1TextView.setText("Player 1: " + player1Points);
        player2TextView.setText("Player 2: " + player2Points);
    }

    // iterate through the buttons and set text to an empty string
    // start afresh with player 1
    private void resetBoard() {
        for (int x = 0; x < 3; x++) {

            for (int y = 0; y < 3; y++) {
                buttons[x][y].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    // save data on screen rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    // restore data saved on screen rotation
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }

    // exit from activity_main layout when the android app back button is pressed
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.quit);
        builder.setMessage(R.string.quit_message);

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });

        AlertDialog dialog = builder.show();


    }
}
