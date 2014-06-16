package pl.dawidfiruzek.snooker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {

	public static final int PLAYER1_NAME_REQUEST = 1;
	public static final int PLAYER2_NAME_REQUEST = 2;
	private String player1Name;
	private String player2Name;
	private Button buttonChoosePlayer1Name;
	private Button buttonChoosePlayer2Name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		buttonChoosePlayer1Name = (Button) findViewById(R.id.buttonChoosePlayer1Name);
		buttonChoosePlayer2Name = (Button) findViewById(R.id.buttonChoosePlayer2Name);
		
		player1Name = player2Name = getResources().getString(R.string.player_name);
		buttonChoosePlayer1Name.setText(player1Name);
		buttonChoosePlayer2Name.setText(player2Name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == PLAYER1_NAME_REQUEST){
			if (resultCode == RESULT_OK) {
			player1Name = data.getStringExtra("RESULT_STRING");
			buttonChoosePlayer1Name.setText(player1Name);
			}
		}
		if(requestCode == PLAYER2_NAME_REQUEST){
			if (resultCode == RESULT_OK) {
			player2Name = data.getStringExtra("RESULT_STRING");
			buttonChoosePlayer2Name.setText(player2Name);
			}
		}
	}
	
	public void clickButtonPlayer1Name(View v){
		Intent intent = new Intent(this, ChoosePlayerActivity.class);
		startActivityForResult(intent, PLAYER1_NAME_REQUEST);
	}
	
	public void clickButtonPlayer2Name(View v){
		Intent intent = new Intent(this, ChoosePlayerActivity.class);
		startActivityForResult(intent, PLAYER2_NAME_REQUEST);
	}
	
	public void clickButtonStart(View v){
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("PLAYER1_NAME", player1Name);
		intent.putExtra("PLAYER2_NAME", player2Name);
		startActivity(intent);
	}
	
}
