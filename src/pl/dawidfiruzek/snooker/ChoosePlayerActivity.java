package pl.dawidfiruzek.snooker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ChoosePlayerActivity extends Activity {

	private EditText playerNameText;
	private String playerName;
	private String warning;
	private TextView testDbText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_player);
		
		
		playerNameText = (EditText)findViewById(R.id.editTextPlayerName);
		warning = getResources().getString(R.string.warning);
		testDbText = (TextView)findViewById(R.id.testDbOutput);
		
		playerNameText.setOnEditorActionListener(new OnEditorActionListener() {		
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				boolean handled = false;
				if(actionId == EditorInfo.IME_ACTION_DONE){
					playerName = playerNameText.getText().toString();
					
					InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.hideSoftInputFromWindow(playerNameText.getWindowToken(), 0);
					handled = true;
				}
				return handled;
			}
		});		
		
		PlayersDataBase dataBase = new PlayersDataBase(this);
		testDbText.setText(null);
//		dataBase.addPlayer("TestPlayer1");
//		dataBase.addPlayer("TestPlayer2");
		Cursor cursor = dataBase.getAll();

		dataBase.deleteAllPlayers();
		
		while(cursor.moveToNext()){
			int number = cursor.getInt(0);
			String name = cursor.getString(1);
			int fWon = cursor.getInt(2);
			int fLost = cursor.getInt(3);
			int maxBreak = cursor.getInt(4);
			int points = cursor.getInt(5);
//			DB_TITLE + " table players (" +
//			DB_INDEX + " integer primary key autoincrement," +
//			DB_NAME + " text," +
//			DB_FRAMES_WON + " integer," +
//			DB_FRAMES_LOST + " integer," +
//			DB_MAX_BREAK + " integer," +
//			DB_POINTS + " integer);" +
			testDbText.setText(testDbText.getText() + "\n" +number+ " " +name+ " " + points);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_player, menu);
		return true;
	}
	
	public void clickButtonOk(View v){
		if(playerName != null){
			Intent output = new Intent();
			output.putExtra("RESULT_STRING", playerName);
			setResult(RESULT_OK, output);
			finish();
		}
		else{
			Toast toast = Toast.makeText(getApplicationContext(), warning, Toast.LENGTH_LONG);
			toast.show();
		}
	}
	

}
