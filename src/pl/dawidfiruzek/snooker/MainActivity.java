package pl.dawidfiruzek.snooker;

import pl.dawidfiruzek.snooker.Game.Turn;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
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
	private Game.Turn turn;
	private static final String TAG = "MyActivity";
	private TextView score1; 
	private TextView score2;
	private TextView currentBreak;
//	private TextView name1;
//	private TextView name2;
	private Button buttonPlayer1;
	private Button buttonPlayer2;
	private String player1Name;
	private String player2Name;
	private final int pointsRed = 1;
	private final int pointsYellow = 2;
	private final int pointsGreen = 3;
	private final int pointsBrown = 4;
	private final int pointsBlue = 5;
	private final int pointsPink = 6;
	private final int pointsBlack = 7;
	private final int pointsFoulFour = 4;
	private int pointsFoulFive = 5;
	private int pointsFoulSix = 6;
	private int pointsFoulSeven = 7;

	//PowerManager manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		player1Name = intent.getStringExtra("PLAYER1_NAME");
		player2Name = intent.getStringExtra("PLAYER2_NAME");

		buttonPlayer1 = (Button)findViewById(R.id.buttonPlayer1);
		buttonPlayer2 = (Button)findViewById(R.id.buttonPlayer2);

		turn = Turn.NOBODY;
		score1 = (TextView) findViewById(R.id.textPlayer1Score);
		score2 = (TextView) findViewById(R.id.textPlayer2Score);
		currentBreak = (TextView) findViewById(R.id.textPlayerBreak);
//		name1 = (TextView) findViewById(R.id.textPlayer1Name);
//		name2 = (TextView) findViewById(R.id.textPlayer2Name);
		
		buttonPlayer1.setText(player1Name);
		buttonPlayer2.setText(player2Name);

	}

	protected void updateVisualEffects() {
		switch(turn){
		case PLAYER1:
			buttonPlayer1.setEnabled(false);
			buttonPlayer2.setEnabled(true);
			break;
		case PLAYER2:
			buttonPlayer2.setEnabled(false);
			buttonPlayer1.setEnabled(true);
			break;
		case NOBODY:
			buttonPlayer1.setEnabled(true);
			buttonPlayer2.setEnabled(true);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void onBackPressed(){
		if(turn != Turn.NOBODY){
			game.undo();
			shortToast("(i) Undo move");
			updateScore();
			turn = game.getCurrentTurn();
			updateVisualEffects();
		}
		else{
			super.onBackPressed();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(1, Menu.FIRST, Menu.FIRST, R.string.game_back);
		menu.add(1, Menu.FIRST+1, Menu.FIRST+1, R.string.game_end_frame);
		menu.add(1, Menu.FIRST+2, Menu.FIRST+2, R.string.game_end_game);
//		menu.add(1, Menu.FIRST+3, Menu.FIRST+3, R.string.game_reset_frame);
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Intent intent = getIntent();
		switch (item.getItemId()) {
			case 1:
				game.undo();
				shortToast("(i) Undo move");
				updateScore();
				turn = game.getCurrentTurn();
				updateVisualEffects();
				break;
			case 2:
				finish();
//				add +1 to player's frame score
				startActivity(intent);
				break;
			case 3:
				finish();
				break;
//			case 4:
//				finish();
////				e.g. adding frame score to intent
//				startActivity(intent);
//				break;
		}
		return true;
	}
	
	private void enterFaul(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.title_fouls);
		
		builder.setItems(R.array.foul_points_array, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				case 0:
					game.foul(pointsFoulFour, turn);
					break;
				case 1:
					game.foul(pointsFoulFive, turn);
					break;
				case 2:
					game.foul(pointsFoulSix, turn);
					break;
				case 3:
					game.foul(pointsFoulSeven, turn);
					break;
				default:
						break;
				}
				updateScore();
			}
		});
		
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {			
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
	
	public void shortToast(String string){
		if(turn != Turn.NOBODY){
			Toast toast = Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	public void clickPlayer1(View v){
		turn = Turn.PLAYER1;
		game.resetBreak(turn);
		updateScore();
		updateVisualEffects();
		Log.i(TAG, "player 1 clicked");
	}
	
	public void clickPlayer2(View v){
		turn = Turn.PLAYER2;
		game.resetBreak(turn);
		updateScore();
		updateVisualEffects();
		Log.i(TAG, "player 2 clicked");
	}
	
	public void clickRed(View v){
		Log.i(TAG, "red is clicked");		
		game.addScore(pointsRed, turn);
		shortToast("+" + pointsRed);
		updateScore();
	}
	public void clickYellow(View v){
		Log.i(TAG, "yellow is clicked");		
		game.addScore(pointsYellow, turn);
		shortToast("+" + pointsYellow);
		updateScore();
	}
	public void clickGreen(View v){
		Log.i(TAG, "green is clicked");
		game.addScore(pointsGreen, turn);
		shortToast("+" + pointsGreen);
		updateScore();
	}
	public void clickBrown(View v){
		Log.i(TAG, "brown is clicked");
		game.addScore(pointsBrown, turn);
		shortToast("+" + pointsBrown);
		updateScore();
	}
	public void clickBlue(View v){
		Log.i(TAG, "blue is clicked");
		game.addScore(pointsBlue, turn);
		shortToast("+" + pointsBlue);
		updateScore();
	}
	public void clickPink(View v){
		Log.i(TAG, "pink is clicked");
		game.addScore(pointsPink, turn);
		shortToast("+" + pointsPink);
		updateScore();
	}
	public void clickBlack(View v){
		Log.i(TAG, "black is clicked");
		game.addScore(pointsBlack, turn);
		shortToast("+" + pointsBlack);
		updateScore();
	}
	public void clickFoul(View v){
		Log.i(TAG, "foul is clicked");
		enterFaul();
	}

}
