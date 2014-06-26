package pl.dawidfiruzek;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import pl.dawidfiruzek.snooker.MainActivity;
import pl.dawidfiruzek.snooker.R;
import android.widget.Button;
import android.widget.TextView;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
	
	MainActivity activity;
	TextView textStatusBar;
	Button buttonPlayer1;
	Button buttonPlayer2;
	TextView textScorePlayer1;
	TextView textBreak;
	TextView textScorePlayer2;
	Button buttonRed;
	Button buttonYellow;
	Button buttonGreen;
	Button buttonBrown;
	Button buttonBlue;
	Button buttonPink;
	Button buttonBlack;
	Button buttonFoul;

    @Before
    public void setup()
    {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
        textStatusBar = (TextView) activity.findViewById(R.id.textStatusBar);
        buttonPlayer1 = (Button) activity.findViewById(R.id.buttonPlayer1);
        buttonPlayer2 = (Button) activity.findViewById(R.id.buttonPlayer2);
        textScorePlayer1 = (TextView) activity.findViewById(R.id.textPlayer1Score);
        textBreak = (TextView) activity.findViewById(R.id.textPlayerBreak);
        textScorePlayer2 = (TextView) activity.findViewById(R.id.textPlayer2Score);
        buttonRed = (Button) activity.findViewById(R.id.buttonRed);
        buttonYellow = (Button) activity.findViewById(R.id.buttonYellow);
        buttonGreen = (Button) activity.findViewById(R.id.buttonGreen);
        buttonBrown = (Button) activity.findViewById(R.id.buttonBrown);
        buttonBlue = (Button) activity.findViewById(R.id.buttonBlue);
        buttonPink = (Button) activity.findViewById(R.id.buttonPink);
        buttonBlack = (Button) activity.findViewById(R.id.buttonBlack);
        buttonFoul = (Button) activity.findViewById(R.id.buttonFoul);
    }

    @Test
    public void shouldHaveHappySmiles() throws Exception 
    {
        String testText = textBreak.getText().toString();
        assertThat(testText, equalTo("Break"));
        
        activity.clickPlayer1(null);
        testText = textBreak.getText().toString();
        assertThat(testText, equalTo("0"));
    }
}
