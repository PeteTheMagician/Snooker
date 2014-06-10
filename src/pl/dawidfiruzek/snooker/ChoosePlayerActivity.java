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

public class ChoosePlayerActivity extends Activity {

	private EditText playerNameText;
	private Button buttonOk;
	private String playerName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_player);
		
		playerNameText = (EditText)findViewById(R.id.editText1);
		buttonOk = (Button)findViewById(R.id.buttonOk);
		
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
		
		buttonOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(playerName != null){
//					buttonOk.setText(playerName);
					Intent output = new Intent();
					output.putExtra("RESULT_STRING", playerName);
					setResult(RESULT_OK, output);
					finish();
				}
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_player, menu);
		return true;
	}

}
