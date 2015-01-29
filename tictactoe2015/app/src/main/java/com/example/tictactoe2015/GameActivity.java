package com.example.tictactoe2015;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener {

	private String player;
	private String playerFirst;
	private String playerSecond;
	private String playerFirstMark = "X";
	private String playerSecondMark = "O";
	private String playerDefaultMark = " ";
	private Button buttonOne;
	private Button buttonTwo;
	private Button buttonThree;
	private Button buttonFour;
	private Button buttonFive;
	private Button buttonSix;
	private Button buttonSeven;
	private Button buttonEight;
	private Button buttonNine;
	private TextView playersinfo;
	private TextView currentPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		initializeGame();

	}

	private void initializeGame() {
		this.currentPlayer = (TextView) findViewById(R.id.playername);
		this.playersinfo = (TextView) findViewById(R.id.playersinfo);
		this.buttonOne = initializeGame(buttonOne, R.id.btn_one);
		this.buttonTwo = initializeGame(buttonTwo, R.id.btn_two);
		this.buttonThree = initializeGame(buttonThree, R.id.btn_three);
		this.buttonFour = initializeGame(buttonFour, R.id.btn_four);
		this.buttonFive = initializeGame(buttonFive, R.id.btn_five);
		this.buttonSix = initializeGame(buttonSix, R.id.btn_six);
		this.buttonSeven = initializeGame(buttonSeven, R.id.btn_seven);
		this.buttonEight = initializeGame(buttonEight, R.id.btn_eight);
		this.buttonNine = initializeGame(buttonNine, R.id.btn_nine);

		this.playerFirst = getIntent().getExtras().getString("PlayerOne");
		this.playerSecond = getIntent().getExtras().getString("PlayerTwo");
		this.player = this.playerFirst;

		this.currentPlayer.setText(this.playerFirst + " Turn : "
				+ this.playerFirstMark);

		this.playersinfo.setText(this.playerFirst + " [" + this.playerFirstMark
				+ "]   vs  " + this.playerSecond + " [" + this.playerSecondMark
				+ "] ");
	}

	private Button initializeGame(Button btn, int id) {
		btn = (Button) findViewById(id);
		btn.setText(this.playerDefaultMark);
		btn.setOnClickListener(this);
		return btn;
	}

	@Override
	public void onClick(View v) {
		if (notAlreadyChecked((Button) v)) {
			mark((Button) v);
			checkSuccess(this.buttonOne.getText().toString(), this.buttonTwo
					.getText().toString(), this.buttonThree.getText()
					.toString(), this.buttonFour.getText().toString(),
					this.buttonFive.getText().toString(), this.buttonSix
							.getText().toString(), this.buttonSeven.getText()
							.toString(), this.buttonEight.getText().toString(),
					this.buttonNine.getText().toString());
		} else {
			Toast t = Toast.makeText(this, "Already Selected",
					Toast.LENGTH_SHORT);
			t.show();
		}
	}

	private boolean notAlreadyChecked(Button v) {
		if (v.getText().toString().equals(this.playerDefaultMark)) {
			return true;
		} else {
			return false;
		}
	}

	private void mark(Button v) {
		if (this.player.equals(this.playerFirst)) {
			v.setText(this.playerFirstMark);
			v.setBackgroundResource(R.drawable.x);
			this.player = this.playerSecond;
			currentPlayer.setText(this.player + " Turn : "
					+ this.playerSecondMark);
		} else {
			v.setText(this.playerSecondMark);
			this.player = this.playerFirst;
			v.setBackgroundResource(R.drawable.o);
			v.setTextColor(Color.WHITE);
			currentPlayer.setText(this.player + " Turn : "
					+ this.playerFirstMark);
		}
	}

	private void checkSuccess(String b1, String b2, String b3, String b4,
			String b5, String b6, String b7, String b8, String b9) {

		String[] result = { b1, b2, b3, b4, b5, b6, b7, b8, b9 };

		if (b1.equals(b2) && b2.equals(b3)
				&& (!b1.equals(this.playerDefaultMark))) {
			declareSuccess(b1, result, 1);
		} else if (b1.equals(b5) && b5.equals(b9)
				&& (!b1.equals(this.playerDefaultMark))) {
			declareSuccess(b1, result, 2);
		} else if (b1.equals(b4) && b4.equals(b7)
				&& (!b1.equals(this.playerDefaultMark))) {
			declareSuccess(b1, result, 3);
		} else if (b2.equals(b5) && b5.equals(b8)
				&& (!b2.equals(this.playerDefaultMark))) {
			declareSuccess(b2, result, 4);
		} else if (b3.equals(b5) && b5.equals(b7)
				&& (!b3.equals(this.playerDefaultMark))) {
			declareSuccess(b3, result, 5);
		} else if (b3.equals(b6) && b6.equals(b9)
				&& (!b3.equals(this.playerDefaultMark))) {
			declareSuccess(b3, result, 6);
		} else if (b4.equals(b5) && b5.equals(b6)
				&& (!b4.equals(this.playerDefaultMark))) {
			declareSuccess(b4, result, 7);
		} else if (b7.equals(b8) && b8.equals(b9)
				&& (!b7.equals(this.playerDefaultMark))) {
			declareSuccess(b7, result, 8);
		} else {
			if (!(b1.equals(this.playerDefaultMark)
					|| b2.equals(this.playerDefaultMark)
					|| b3.equals(this.playerDefaultMark)
					|| b4.equals(this.playerDefaultMark)
					|| b5.equals(this.playerDefaultMark)
					|| b6.equals(this.playerDefaultMark)
					|| b7.equals(this.playerDefaultMark)
					|| b8.equals(this.playerDefaultMark) || b9
						.equals(this.playerDefaultMark))) {
				declareSuccess("*", result, 0);
			}
		}

	}

	private void declareSuccess(String win, String[] result, int type) {
		String result_message = "";
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast, null);

		TextView text1 = (TextView) layout.findViewById(R.id.text1);
		text1 = setbackground(text1, result[0].toString(), "no-line");
		TextView text2 = (TextView) layout.findViewById(R.id.text2);
		text2 = setbackground(text2, result[1].toString(), "no-line");
		TextView text3 = (TextView) layout.findViewById(R.id.text3);
		text3 = setbackground(text3, result[2].toString(), "no-line");
		TextView text4 = (TextView) layout.findViewById(R.id.text4);
		text4 = setbackground(text4, result[3].toString(), "no-line");
		TextView text5 = (TextView) layout.findViewById(R.id.text5);
		text5 = setbackground(text5, result[4].toString(), "no-line");
		TextView text6 = (TextView) layout.findViewById(R.id.text6);
		text6 = setbackground(text6, result[5].toString(), "no-line");
		TextView text7 = (TextView) layout.findViewById(R.id.text7);
		text7 = setbackground(text7, result[6].toString(), "no-line");
		TextView text8 = (TextView) layout.findViewById(R.id.text8);
		text8 = setbackground(text8, result[7].toString(), "no-line");
		TextView text9 = (TextView) layout.findViewById(R.id.text9);
		text9 = setbackground(text9, result[8].toString(), "no-line");

		switch (type) {
		case 1:
			text1 = setbackground(text1, result[0].toString(), "h");
			text2 = setbackground(text2, result[1].toString(), "h");
			text3 = setbackground(text3, result[2].toString(), "h");
			break;
		case 2:
			text1 = setbackground(text1, result[0].toString(), "lr");
			text5 = setbackground(text5, result[4].toString(), "lr");
			text9 = setbackground(text9, result[8].toString(), "lr");
			break;
		case 3:
			text1 = setbackground(text1, result[0].toString(), "v");
			text4 = setbackground(text4, result[3].toString(), "v");
			text7 = setbackground(text7, result[6].toString(), "v");
			break;
		case 4:
			text2 = setbackground(text2, result[1].toString(), "v");
			text5 = setbackground(text5, result[4].toString(), "v");
			text8 = setbackground(text8, result[7].toString(), "v");
			break;
		case 5:
			text3 = setbackground(text3, result[2].toString(), "rl");
			text5 = setbackground(text5, result[4].toString(), "rl");
			text7 = setbackground(text7, result[6].toString(), "rl");
			break;
		case 6:
			text3 = setbackground(text3, result[2].toString(), "v");
			text6 = setbackground(text6, result[5].toString(), "v");
			text9 = setbackground(text9, result[8].toString(), "v");
			break;
		case 7:
			text4 = setbackground(text4, result[3].toString(), "h");
			text5 = setbackground(text5, result[4].toString(), "h");
			text6 = setbackground(text6, result[5].toString(), "h");
			break;
		case 8:
			text7 = setbackground(text7, result[6].toString(), "h");
			text8 = setbackground(text8, result[7].toString(), "h");
			text9 = setbackground(text9, result[8].toString(), "h");
			break;
		default:
			break;
		}

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				GameActivity.this);

		// set title
		alertDialogBuilder.setTitle("Game end");

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Restart",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent intent = getIntent();
								finish();
								startActivity(intent);
							}
						})
				.setNegativeButton("Exit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// dialog.cancel();
								GameActivity.this.finish();
							}
						});
		// create alert dialog

		if (win.equals(this.playerFirstMark)) {
			result_message = this.playerFirst + " wins";
		} else if (win.equals(this.playerSecondMark)) {
			result_message = this.playerSecond + " wins";
			// } else if (win.equals("*")) {
		} else {
			result_message = "Game draw";
		}
		alertDialogBuilder.setMessage(result_message);
		alertDialogBuilder.setView(layout);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

		// Toast.makeText(GameActivity.this, result, Toast.LENGTH_LONG).show();
	}

	public TextView setbackground(TextView view, String Check, String Success) {
		if (Check == this.playerFirstMark) {
			if (Success.equals("no-line")) {
				view.setBackgroundResource(R.drawable.x);
			} else if (Success.equals("h")) {
				view.setBackgroundResource(R.drawable.xlineh);
			} else if (Success.equals("v")) {
				view.setBackgroundResource(R.drawable.xlinev);
			} else if (Success.equals("rl")) {
				view.setBackgroundResource(R.drawable.xcrossrl);
			} else if (Success.equals("lr")) {
				view.setBackgroundResource(R.drawable.xcrosslr);
			} else {
				view.setBackgroundResource(R.drawable.x);
			}
		} else if (Check == playerSecondMark) {
			if (Success.equals("no-line")) {
				view.setBackgroundResource(R.drawable.o);
			} else if (Success.equals("h")) {
				view.setBackgroundResource(R.drawable.olineh);
			} else if (Success.equals("v")) {
				view.setBackgroundResource(R.drawable.olinev);
			} else if (Success.equals("rl")) {
				view.setBackgroundResource(R.drawable.ocrossrl);
			} else if (Success.equals("lr")) {
				view.setBackgroundResource(R.drawable.ocrosslr);
			} else {
				view.setBackgroundResource(R.drawable.o);
			}
		} else {
			view.setBackgroundResource(R.drawable.simple);
		}
		return view;
	}
}
