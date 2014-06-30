package pl.dawidfiruzek;

import org.junit.Assert;
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
	
	private MainActivity activity;
	private TextView textStatusBar;
	private Button buttonPlayer1;
	private TextView textFrameScore;
	private Button buttonPlayer2;
	private TextView textScorePlayer1;
	private TextView textBreak;
	private TextView textScorePlayer2;
	private Button buttonRed;
	private Button buttonYellow;
	private Button buttonGreen;
	private Button buttonBrown;
	private Button buttonBlue;
	private Button buttonPink;
	private Button buttonBlack;
	private Button buttonFoul;

    @Before
    public void setup()
    {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
        textStatusBar = (TextView) activity.findViewById(R.id.textStatusBar);
        buttonPlayer1 = (Button) activity.findViewById(R.id.buttonPlayer1);
        textFrameScore = (TextView) activity.findViewById(R.id.textFrameScore);
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
    public void checkStartParameters() throws Exception{
    	
    	Assert.assertEquals(textStatusBar.getText(), "there will be status bar");
    	
    	Assert.assertTrue(buttonPlayer1.isClickable());
    	Assert.assertTrue(buttonPlayer1.isEnabled());
    	Assert.assertTrue(buttonPlayer2.isClickable());
    	Assert.assertTrue(buttonPlayer2.isEnabled());
    	
    	Assert.assertEquals(buttonPlayer1.getText(), "");
    	Assert.assertEquals(buttonPlayer2.getText(), "");
    	
    	Assert.assertEquals(textFrameScore.getText(), "0 : 0");
    	
    	Assert.assertEquals(textScorePlayer1.getText(), "Score");
    	Assert.assertEquals(textScorePlayer2.getText(), "Score");
    	
    	Assert.assertEquals(textBreak.getText(), "Break");
    	     
    	Assert.assertTrue(buttonRed.isClickable());
        Assert.assertTrue(buttonRed.isEnabled());
        Assert.assertTrue(buttonYellow.isClickable());
        Assert.assertTrue(buttonYellow.isEnabled());
        Assert.assertTrue(buttonGreen.isClickable());
        Assert.assertTrue(buttonGreen.isEnabled());
        Assert.assertTrue(buttonBrown.isClickable());
        Assert.assertTrue(buttonBrown.isEnabled());
        Assert.assertTrue(buttonBlue.isClickable());
        Assert.assertTrue(buttonBlue.isEnabled());
        Assert.assertTrue(buttonPink.isClickable());
        Assert.assertTrue(buttonPink.isEnabled());
        Assert.assertTrue(buttonBlack.isClickable());
        Assert.assertTrue(buttonBlack.isEnabled());
        Assert.assertTrue(buttonFoul.isClickable());
        Assert.assertTrue(buttonFoul.isEnabled());
        
    }
    
    @Test
    public void checkPlayersScore() throws Exception{
    	
		buttonPlayer1.performClick();
		Assert.assertFalse(buttonPlayer1.isEnabled());
		Assert.assertTrue(buttonPlayer2.isEnabled());

		Assert.assertEquals(textScorePlayer1.getText(), "0");
		Assert.assertEquals(textScorePlayer2.getText(), "0");
		Assert.assertEquals(textBreak.getText(), "0");

		clickAllColors();
		
		Assert.assertEquals(textScorePlayer1.getText(), "28");
		Assert.assertEquals(textScorePlayer2.getText(), "0");
		Assert.assertEquals(textBreak.getText(), "28");
		
		buttonPlayer2.performClick();
		Assert.assertFalse(buttonPlayer2.isEnabled());
		Assert.assertTrue(buttonPlayer1.isEnabled());
		
		Assert.assertEquals(textScorePlayer1.getText(), "28");
		Assert.assertEquals(textScorePlayer2.getText(), "0");
		Assert.assertEquals(textBreak.getText(), "0");
		
		clickAllColors();
		
		Assert.assertEquals(textScorePlayer1.getText(), "28");
		Assert.assertEquals(textScorePlayer2.getText(), "28");
		Assert.assertEquals(textBreak.getText(), "28");
		
		buttonPlayer1.performClick();
		Assert.assertEquals(textScorePlayer1.getText(), "28");
		Assert.assertEquals(textScorePlayer2.getText(), "28");
		Assert.assertEquals(textBreak.getText(), "0");		
				
    }
    
    public void clickAllColors(){
    	buttonRed.performClick();
		buttonYellow.performClick();
		buttonGreen.performClick();
		buttonBrown.performClick();
		buttonBlue.performClick();
		buttonPink.performClick();
		buttonBlack.performClick();
    }
}
