package pl.dawidfiruzek.snooker;

public class Game {

	public enum Turn{
		PLAYER1,
		PLAYER2,
		NOBODY
	}
	
	private int scorePlayer1;
	private int scorePlayer2;
	
	private int currentBreak;
	private int lastMove;
	
	private boolean foulFlag;
	
	public Game(){
		scorePlayer1 = 0;
		scorePlayer2 = 0;
		currentBreak = 0;
		lastMove = 0;
		foulFlag = false;
	}
	
	public void addScore(int points, Turn turn){
		
		switch (turn){
			case PLAYER1:
				scorePlayer1 += points;
				currentBreak += points;
				lastMove = points;
				foulFlag = false;
				break;
			case PLAYER2:
				scorePlayer2 += points;
				currentBreak += points;
				lastMove = points;
				foulFlag = false;
				break;
			case NOBODY:
				break;
		}
	}
	
	public void foul(int points, Turn turn){

		switch (turn){
		case PLAYER1:
			scorePlayer2 += points;
			lastMove = points;
			foulFlag = true;
			break;
		case PLAYER2:
			scorePlayer1 += points;
			lastMove = points;
			foulFlag = true;
			break;
		case NOBODY:
			break;
		}
	}
	
	public int getScorePlayer1(){
		return scorePlayer1;
	}
	
	public int getScorePlayer2(){
		return scorePlayer2;
	}
	
	public int getBreak(){
		return currentBreak;
	}
	
	public void resetBreak(){
		currentBreak = 0;
	}
	
	public void setFoulFlag(boolean flag){
		foulFlag = flag;
	}
	
	public void undo(Turn turn){
		if(foulFlag){
			switch (turn){
				case PLAYER1:
					scorePlayer2 -= lastMove;
					break;
				case PLAYER2:
					scorePlayer1 -= lastMove;
					break;
				case NOBODY:
					break;
			}
		}
		else{
			switch (turn){
			case PLAYER1:
				scorePlayer1 -= lastMove;
				currentBreak -= lastMove;
				break;
			case PLAYER2:
				scorePlayer2 -= lastMove;
				currentBreak -= lastMove;
				break;
			case NOBODY:
				break;
		}
			
		}
	}
}
