package pl.dawidfiruzek.snooker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ChoosePlayerActivity extends Activity {

	private EditText playerNameText;
	private String playerName;
	private String warning;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_player);
		
		
		playerNameText = (EditText)findViewById(R.id.editTextPlayerName);
		warning = getResources().getString(R.string.warning);
		
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
