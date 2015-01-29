package com.example.tictactoe2015;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToe extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tic_tac_toe);

		final TextView tvplayerone = (TextView) findViewById(R.id.tvplayerone);
		final TextView tvplayertwo = (TextView) findViewById(R.id.tvplayertwo);

		Button btn_gamestart = (Button) findViewById(R.id.btnstartgame);

		btn_gamestart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String playerone_value = tvplayerone.getText().toString()
						.trim();
				String playertwo_value = tvplayertwo.getText().toString()
						.trim();
				if (playerone_value.isEmpty() || playertwo_value.isEmpty()
						|| (playerone_value.equals(playertwo_value))) {
					Toast.makeText(TicTacToe.this,
							"Please enter valid names to start game.\nPlayer one and two can not be same.",
							Toast.LENGTH_SHORT).show();
				} else {

					Intent intent = new Intent(TicTacToe.this,
							GameActivity.class);
					intent.putExtra("PlayerOne", playerone_value);
					intent.putExtra("PlayerTwo", playertwo_value);
					startActivity(intent);
					finish();
				}
			}
		});
	}
}