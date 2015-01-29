package com.example.tictactoe2015;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ToastResultGameActivity extends Activity implements OnClickListener {

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
			this.player = this.playerSecond;
			currentPlayer.setText(this.player + " Turn : "
					+ this.playerSecondMark);
		} else {
			v.setText(this.playerSecondMark);
			this.player = this.playerFirst;
			currentPlayer.setText(this.player + " Turn : "
					+ this.playerFirstMark);
		}
	}

	private void checkSuccess(String b1, String b2, String b3, String b4,
			String b5, String b6, String b7, String b8, String b9) {

		String[] result = {b1,b2,b3,b4,b5,b6,b7,b8,b9};

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
		View layout = inflater.inflate(R.layout.toast,
				(ViewGroup) findViewById(R.id.toast_root));
		TextView text1 = (TextView) layout.findViewById(R.id.text1);
		text1.setText(result[0].toString());
		text1.setBackgroundResource(R.drawable.simple);
		TextView text2 = (TextView) layout.findViewById(R.id.text2);
		text2.setText(result[1].toString());
		text2.setBackgroundResource(R.drawable.simple);
		TextView text3 = (TextView) layout.findViewById(R.id.text3);
		text3.setText(result[2].toString());
		text3.setBackgroundResource(R.drawable.simple);
		TextView text4 = (TextView) layout.findViewById(R.id.text4);
		text4.setText(result[3].toString());
		text4.setBackgroundResource(R.drawable.simple);
		TextView text5 = (TextView) layout.findViewById(R.id.text5);
		text5.setText(result[4].toString());
		text5.setBackgroundResource(R.drawable.simple);
		TextView text6 = (TextView) layout.findViewById(R.id.text6);
		text6.setText(result[5].toString());
		text6.setBackgroundResource(R.drawable.simple);
		TextView text7 = (TextView) layout.findViewById(R.id.text7);
		text7.setText(result[6].toString());
		text7.setBackgroundResource(R.drawable.simple);
		TextView text8 = (TextView) layout.findViewById(R.id.text8);
		text8.setText(result[7].toString());
		text8.setBackgroundResource(R.drawable.simple);
		TextView text9 = (TextView) layout.findViewById(R.id.text9);
		text9.setText(result[8].toString());
		text9.setBackgroundResource(R.drawable.simple);

		switch (type) {
		case 1:
			text1.setBackgroundResource(R.drawable.lineh);
			text2.setBackgroundResource(R.drawable.lineh);
			text3.setBackgroundResource(R.drawable.lineh);
			break;
		case 2:
			text1.setBackgroundResource(R.drawable.crosslr);
			text5.setBackgroundResource(R.drawable.crosslr);
			text9.setBackgroundResource(R.drawable.crosslr);
			break;
		case 3:
			text1.setBackgroundResource(R.drawable.linev);
			text4.setBackgroundResource(R.drawable.linev);
			text7.setBackgroundResource(R.drawable.linev);
			break;
		case 4:
			text2.setBackgroundResource(R.drawable.linev);
			text5.setBackgroundResource(R.drawable.linev);
			text8.setBackgroundResource(R.drawable.linev);
			break;
		case 5:
			text3.setBackgroundResource(R.drawable.crossrl);
			text5.setBackgroundResource(R.drawable.crossrl);
			text7.setBackgroundResource(R.drawable.crossrl);
			break;
		case 6:
			text3.setBackgroundResource(R.drawable.linev);
			text6.setBackgroundResource(R.drawable.linev);
			text9.setBackgroundResource(R.drawable.linev);
			break;
		case 7:
			text4.setBackgroundResource(R.drawable.lineh);
			text5.setBackgroundResource(R.drawable.lineh);
			text6.setBackgroundResource(R.drawable.lineh);
			break;
		case 8:
			text7.setBackgroundResource(R.drawable.lineh);
			text8.setBackgroundResource(R.drawable.lineh);
			text9.setBackgroundResource(R.drawable.lineh);
			break;
		default:
			break;
		}

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				ToastResultGameActivity.this);

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
								ToastResultGameActivity.this.finish();
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
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

		Toast toast = new Toast(getApplicationContext());
		// toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();

		// Toast.makeText(GameActivity.this, result, Toast.LENGTH_LONG).show();
	}
}
