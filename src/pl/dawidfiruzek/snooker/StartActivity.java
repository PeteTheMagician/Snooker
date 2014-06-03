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
	private static Button buttonStartGame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		buttonStartGame = (Button) findViewById(R.id.button3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data){
//		if(resultCode == REQUEST_CANCELLED){
//			
//		}
//	}
	public void clickButtonStart(View v){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void clickButtonPlayer1(View v){
		Intent intent = new Intent(this, ChoosePlayerActivity.class);
		startActivityForResult(intent, PLAYER1_NAME_REQUEST);
	}
	
	public void clickButtonPlayer2(View v){
		Intent intent = new Intent(this, ChoosePlayerActivity.class);
		startActivityForResult(intent, PLAYER2_NAME_REQUEST);
	}
}
