package pl.dawidfiruzek.snooker;

import pl.dawidfiruzek.snooker.Game.Turn;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Game game;
	private Game.Turn turn;
	private static final String TAG = "MyActivity";
	private TextView textScore1; 
	private TextView textScore2;
	private TextView textCurrentBreak;
	private TextView textFrameScore;
	private TextView textStatusBar;
	private Button buttonPlayer1;
	private Button buttonPlayer2;
	private String player1Name;
	private String player2Name;
	private int player1FrameScore;
	private int player2FrameScore;
	private final int pointsRed = 1;
	private final int pointsYellow = 2;
	private final int pointsGreen = 3;
	private final int pointsBrown = 4;
	private final int pointsBlue = 5;
	private final int pointsPink = 6;
	private final int pointsBlack = 7;
	private final int pointsFoulFour = 4;
	private final int pointsFoulFive = 5;
	private final int pointsFoulSix = 6;
	private final int pointsFoulSeven = 7;
	private boolean isItBeginOfFirstFrame;
	private Turn whoStartsFrame;
	Animation animationStatusBarToTheLeft;
	Animation animationStatusBarFromRight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		player1Name = intent.getStringExtra("PLAYER1_NAME");
		player2Name = intent.getStringExtra("PLAYER2_NAME");
		player1FrameScore = intent.getIntExtra("PLAYER1_FRAME_SCORE", 0);
		player2FrameScore = intent.getIntExtra("PLAYER2_FRAME_SCORE", 0);
		
		isItBeginOfFirstFrame = intent.getBooleanExtra("IS_IT_BEGIN", true);
		whoStartsFrame = (Turn) intent.getSerializableExtra("WHO_STARTS");
		if(whoStartsFrame == null){
			whoStartsFrame = Turn.NOBODY;
		}
		turn = whoStartsFrame;

		buttonPlayer1 = (Button)findViewById(R.id.buttonPlayer1);
		buttonPlayer2 = (Button)findViewById(R.id.buttonPlayer2);
		
		game = new Game(turn);
		
		textScore1 = (TextView) findViewById(R.id.textPlayer1Score);
		textScore2 = (TextView) findViewById(R.id.textPlayer2Score);
		textCurrentBreak = (TextView) findViewById(R.id.textPlayerBreak);
		textFrameScore = (TextView) findViewById(R.id.textFrameScore);
		textStatusBar = (TextView) findViewById(R.id.textStatusBar);
		
		buttonPlayer1.setText(player1Name);
		buttonPlayer2.setText(player2Name);
		
		animationStatusBarToTheLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.status_bar_to_the_left_animation);
		animationStatusBarFromRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.status_bar_from_right_animation);
		
		textFrameScore.setText(player1FrameScore + " : " + player2FrameScore);
		updateVisualEffects();

	}

	protected void updateVisualEffects() {
		if(isItBeginOfFirstFrame){
			whoStartsFrame = turn;
			isItBeginOfFirstFrame = false;
		}
		else{
//			textStatusBar.startAnimation(animationStatusBarToTheLeft);
			textStatusBar.startAnimation(animationStatusBarFromRight);
		}
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
			isItBeginOfFirstFrame = true; //in case of pressing back button to beggining of the game
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void onBackPressed(){
		if(turn == Turn.NOBODY){ //&& it is first frame!
			super.onBackPressed();
		}
		else{
			game.undo();
			shortToast("(i) Undo move");
			updateScore();
			turn = game.getCurrentTurn();
			updateVisualEffects();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(1, Menu.FIRST, Menu.FIRST, R.string.game_back);
		menu.add(1, Menu.FIRST+1, Menu.FIRST+1, R.string.game_end_frame);
		menu.add(1, Menu.FIRST+2, Menu.FIRST+2, R.string.game_end_game);
		menu.add(1, Menu.FIRST+3, Menu.FIRST+3, R.string.game_reset_frame);
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Intent intent = getIntent();
		switch (item.getItemId()) {
			case 1: //back
				game.undo();
				shortToast("(i) Undo move");
				updateScore();
				turn = game.getCurrentTurn();
				updateVisualEffects();
				break;
			case 2: //end frame
				finish();
				intent.putExtra("IS_IT_BEGIN", false);
				
				switch(whoStartsFrame){
				case PLAYER1:
					intent.putExtra("WHO_STARTS", Turn.PLAYER2);
					break;
				case PLAYER2:
					intent.putExtra("WHO_STARTS", Turn.PLAYER1);
					break;
				default:
					intent.putExtra("WHO_STARTS", Turn.NOBODY);
					break;
				}
				
//				add +1 to player's frame score
				if(game.getScorePlayer1() > game.getScorePlayer2()){
					intent.putExtra("PLAYER1_FRAME_SCORE", ++player1FrameScore);
				}
				else if(game.getScorePlayer1() < game.getScorePlayer2()){
					intent.putExtra("PLAYER2_FRAME_SCORE", ++player2FrameScore);
				}
//				else{
//					there is no reason for executing else, because previous values are from existing intent :)
//				}					
				startActivity(intent);
				break;
			case 3:
				finish(); //end game
//				after creating db we have to write some values there
				break;
			case 4:
				finish(); //reset frame
				startActivity(intent);
				break;
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
			textCurrentBreak.setText(Integer.toString(game.getBreak()));
			textScore1.setText(Integer.toString(game.getScorePlayer1()));
			textScore2.setText(Integer.toString(game.getScorePlayer2()));
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
