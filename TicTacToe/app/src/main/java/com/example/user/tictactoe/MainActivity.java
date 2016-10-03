package com.example.user.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  TicTacToeGame mGame = new TicTacToeGame(this);
    private TextView mGameStateTextView;
    private Button[][] mTicTacToeButtons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGame.resetGame();

        mGameStateTextView = (TextView) findViewById(R.id.message_text);


        Button newGameButton = (Button) findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(this);

        mTicTacToeButtons = new Button[TicTacToeGame.NUM_ROWS][TicTacToeGame.NUM_COLUMNS];
        //mTicTacToeButtons[0][0] = (Button) findViewById(R.id.button00); and other 8 buttons Or do this:

        for (int row = 0; row < TicTacToeGame.NUM_ROWS; row++) {
            for (int col = 0; col < TicTacToeGame.NUM_COLUMNS; col++) {
                int id = getResources().getIdentifier("button" + row + col, "id", getPackageName());
                mTicTacToeButtons[row][col] = (Button)findViewById(id);
                mTicTacToeButtons[row][col].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.new_game_button) {
            mGame.resetGame();
        }

        for (int row = 0; row < TicTacToeGame.NUM_ROWS; row++) {
            for (int col = 0; col < TicTacToeGame.NUM_COLUMNS; col++) {
                if(v.getId() == mTicTacToeButtons[row][col].getId()) {
                    Log.d("TTT", "Button presed at" + row + " " + col);
                    mGame.pressedButtonAtLocation(row, col);
                }
                mTicTacToeButtons[row][col].setText(mGame.stringForButtonAtLocation(row, col));
            }
        }

                mGameStateTextView.setText(mGame.stringForGameState());

    }
}
