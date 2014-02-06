package pl.dawidfiruzek.snooker;

import pl.dawidfiruzek.snooker.Game.Turn;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Game game = new Game();
	Game.Turn turn;
	private static final String TAG = "MyActivity";
	private static TextView score1; 
	private static TextView score2;
	private static TextView currentBreak;
	private static TextView name1;
	private static TextView name2;


	//PowerManager manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Button buttonPlayer1 = (Button)findViewById(R.id.buttonPlayer1);
		final Button buttonPlayer2 = (Button)findViewById(R.id.buttonPlayer2);

		turn = Turn.NOBODY;
		score1 = (TextView) findViewById(R.id.textPlayer1Score);
		score2 = (TextView) findViewById(R.id.textPlayer2Score);
		currentBreak = (TextView) findViewById(R.id.textPlayerBreak);
		name1 = (TextView) findViewById(R.id.textPlayer1Name);
		name2 = (TextView) findViewById(R.id.textPlayer2Name);
		
		buttonPlayer1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonPlayer1.setText("Turn"); // setVisibility(View.INVISIBLE);
				buttonPlayer1.setClickable(false);
				buttonPlayer1.setBackgroundColor(Color.GREEN);
				buttonPlayer2.setText("Stop"); //setVisibility(View.VISIBLE);	
				buttonPlayer2.setClickable(true);
				buttonPlayer2.setBackgroundColor(Color.GRAY);
				
				turn = Turn.PLAYER1;
				game.resetBreak();
				updateScore();
				Log.i(TAG, "player 1 clicked");
			}
		});
		
		buttonPlayer2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonPlayer2.setText("Turn"); //setVisibility(View.INVISIBLE);
				buttonPlayer2.setClickable(false);
				buttonPlayer2.setBackgroundColor(Color.GREEN);
				buttonPlayer1.setText("Stop"); //setVisibility(View.VISIBLE);
				buttonPlayer1.setClickable(true);
				buttonPlayer1.setBackgroundColor(Color.GRAY);
				
				turn = Turn.PLAYER2;
				game.resetBreak();
				updateScore();
				Log.i(TAG, "player 2 clicked");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(1, Menu.FIRST, Menu.FIRST, "back");
		menu.add(1, Menu.FIRST+1, Menu.FIRST+1, "reset");
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
			case 1:
				game.undo(turn);
				Toast toast = Toast.makeText(getApplicationContext(), "This works only one move back (for now)", Toast.LENGTH_SHORT);
				toast.show();
				updateScore();
				break;
			case 2:
				break;
		}
		return true;
	}
	
	private void enterName(final TextView name){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Please enter your name");
		
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);
		
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				name.setText(input.getText().toString());
				
			}
		});
			
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
			
		builder.show();
	}
	
	private void enterFaul(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Enter faul points");
		
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		builder.setView(input);
		
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {				
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				if(Integer.parseInt(input.getText().toString()) > 3 & Integer.parseInt(input.getText().toString()) < 8)
					game.foul(Integer.parseInt(input.getText().toString()), turn);
				else{
					Toast toast = Toast.makeText(getApplicationContext(), "Incorrect value. Try again", Toast.LENGTH_SHORT);
					toast.show();
				}
				updateScore();
			}
		});
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		builder.show();
	}
	
	private void updateScore(){
		if(turn != Turn.NOBODY){
		currentBreak.setText(Integer.toString(game.getBreak()));
		score1.setText(Integer.toString(game.getScorePlayer1()));
		score2.setText(Integer.toString(game.getScorePlayer2()));
		}
	}

	
	public void clickPlayer1Name(View v){
		enterName(name1);
	}
	
	public void clickPlayer2Name(View v){
		enterName(name2);
	}
	
	public void clickRed(View v){
		Log.i(TAG, "red is clicked");		
		game.addScore(1, turn);
		updateScore();
	}
	public void clickYellow(View v){
		Log.i(TAG, "yellow is clicked");		
		game.addScore(2, turn);
		updateScore();
	}
	public void clickGreen(View v){
		Log.i(TAG, "green is clicked");
		game.addScore(3, turn);
		updateScore();
	}
	public void clickBrown(View v){
		Log.i(TAG, "brown is clicked");
		game.addScore(4, turn);
		updateScore();
	}
	public void clickBlue(View v){
		Log.i(TAG, "blue is clicked");
		game.addScore(5, turn);
		updateScore();
	}
	public void clickPink(View v){
		Log.i(TAG, "pink is clicked");
		game.addScore(6, turn);
		updateScore();
	}
	public void clickBlack(View v){
		Log.i(TAG, "black is clicked");
		game.addScore(7, turn);
		updateScore();
	}
	public void clickFoul(View v){
		Log.i(TAG, "foul is clicked");
		enterFaul();
	}

}
