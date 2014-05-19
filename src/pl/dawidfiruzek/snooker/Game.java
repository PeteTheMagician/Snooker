package pl.dawidfiruzek.snooker;

import java.util.Stack;

public class Game {

	private int scorePlayer1;
	private int scorePlayer2;
	private Stack<lastTurn> currentTurn = new Stack<lastTurn>();

	public enum Turn{
		PLAYER1,
		PLAYER2,
		NOBODY
	}
	
	private class lastTurn{

		public int currentPoints;
		public int currentBreak;
		boolean isItFoul;
		Turn whoseMoveThisIs;
		
		public lastTurn(){
			scorePlayer1 = 0;
			scorePlayer2 = 0;
			currentBreak = 0;
			currentPoints = 0;
			whoseMoveThisIs = Turn.NOBODY;
			isItFoul = false;
		}
		
		public lastTurn(int score1, int score2, int cBreak, int cPoints, Turn who, boolean foul){
			scorePlayer1 += score1;
			scorePlayer2 += score2;
			currentBreak = cBreak;
			currentPoints = cPoints;
			whoseMoveThisIs = who;
			isItFoul = foul;
		}
		
//		constructor which is used to reseting break on stack during switching players
		public lastTurn(Turn who){
			scorePlayer1 += 0;
			scorePlayer2 += 0;
			currentBreak = 0;
			currentPoints = 0;
			whoseMoveThisIs = who;
			isItFoul = false;
		}
	}
	
	
	public Game(){
		
		currentTurn.push(new lastTurn());
	}
	
	public void addScore(int points, Turn turn){
		
		switch (turn){
			case PLAYER1:
				currentTurn.push(new lastTurn(points, 0, currentTurn.peek().currentBreak + points, points, turn, false));
				break;
			case PLAYER2:
				currentTurn.push(new lastTurn(0, points, currentTurn.peek().currentBreak + points, points, turn, false));
				break;
			case NOBODY:
				break;
		}
	}
	
	public void foul(int points, Turn turn){

		switch (turn){
		case PLAYER1:
			currentTurn.push(new lastTurn(0, points, 0, points, turn, true));
			break;
		case PLAYER2:
			currentTurn.push(new lastTurn(points, 0, 0, points, turn, true));
			break;
		case NOBODY:
			break;
		}
	}
	
	public void undo(){

		if(currentTurn.peek().isItFoul){
			switch (currentTurn.peek().whoseMoveThisIs){
			case PLAYER1:
				scorePlayer2 -= currentTurn.pop().currentPoints;
				break;
			case PLAYER2:
				scorePlayer1 -= currentTurn.pop().currentPoints;
				break;
			case NOBODY:
				break;
			}
		}
		else{
			switch (currentTurn.peek().whoseMoveThisIs){
			case PLAYER1:
				scorePlayer1 -= currentTurn.pop().currentPoints;
				break;
			case PLAYER2:
				scorePlayer2 -= currentTurn.pop().currentPoints;
				break;
			case NOBODY:
				break;
			}
		}
	}
	
	public int getScorePlayer1(){
		return scorePlayer1;
	}
	
	public int getScorePlayer2(){
		return scorePlayer2;
	}
	
	public int getBreak(){
		return currentTurn.peek().currentBreak;
	}
	
//	operation during switching players
	public void resetBreak(Turn turn){
		currentTurn.push(new lastTurn(turn));
	}
}
