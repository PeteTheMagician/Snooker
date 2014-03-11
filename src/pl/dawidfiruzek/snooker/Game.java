package pl.dawidfiruzek.snooker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import android.R.integer;
import android.util.SparseBooleanArray;

public class Game {

	public enum Turn{
		PLAYER1,
		PLAYER2,
		NOBODY
	}
	
	private int scorePlayer1;
	private int scorePlayer2;
	
	private int currentBreak;
	//private int lastMove;
	ArrayList<Integer> lastMovePoints = new ArrayList<Integer>();
	ArrayList<Turn> whoseMoveThisIs = new ArrayList<Turn>();
	SparseBooleanArray wasItFoul = new SparseBooleanArray();
	int iterator;
	
//	private boolean foulFlag;
	
	public Game(){
		scorePlayer1 = 0;
		scorePlayer2 = 0;
		currentBreak = 0;
		lastMovePoints.add(0);
		whoseMoveThisIs.add(Turn.NOBODY);
		//foulFlag = false;
		iterator = 0;
		wasItFoul.put(iterator, false);
	}
	
	public void addScore(int points, Turn turn){
		
		switch (turn){
			case PLAYER1:
				scorePlayer1 += points;
				currentBreak += points;
				lastMovePoints.add(points);
				whoseMoveThisIs.add(Turn.PLAYER1);
				++iterator;
				wasItFoul.put(iterator, false);
				//foulFlag = false;
				break;
			case PLAYER2:
				scorePlayer2 += points;
				currentBreak += points;
				lastMovePoints.add(points);
				whoseMoveThisIs.add(Turn.PLAYER2);
				++iterator;
				wasItFoul.put(iterator, false);
				//foulFlag = false;
				break;
			case NOBODY:
				break;
		}
	}
	
	public void foul(int points, Turn turn){

		switch (turn){
		case PLAYER1:
			scorePlayer2 += points;
			lastMovePoints.add(points);
			whoseMoveThisIs.add(Turn.PLAYER1);
			++iterator;
			wasItFoul.put(iterator, true);
			//foulFlag = true;
			break;
		case PLAYER2:
			scorePlayer1 += points;
			lastMovePoints.add(points);
			whoseMoveThisIs.add(Turn.PLAYER2);
			++iterator;
			wasItFoul.put(iterator, true);

			//foulFlag = true;
			break;
		case NOBODY:
			break;
		}
	}
	
	public void undo(Turn turn){
		if(wasItFoul.get(iterator)){
			switch (whoseMoveThisIs.get(iterator)){
			case PLAYER1:
				scorePlayer2 -= lastMovePoints.get(iterator);
				deleteUnusedValues();
				break;
			case PLAYER2:
				scorePlayer1 -= lastMovePoints.get(iterator);
				deleteUnusedValues();
				break;
			case NOBODY:
				break;
			}
		}
		else{
			switch (whoseMoveThisIs.get(iterator)){
			case PLAYER1:
				scorePlayer1 -= lastMovePoints.get(iterator);
				currentBreak -= lastMovePoints.get(iterator);
				deleteUnusedValues();
				break;
			case PLAYER2:
				scorePlayer2 -= lastMovePoints.get(iterator);
				currentBreak -= lastMovePoints.get(iterator);
				deleteUnusedValues();
				break;
			case NOBODY:
				break;
			}
			
		}
	}
	
	private void deleteUnusedValues(){
		whoseMoveThisIs.remove(iterator);
		lastMovePoints.remove(iterator);
		wasItFoul.delete(iterator);
		--iterator;
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
	
//	public void setFoulFlag(boolean flag){
//		foulFlag = flag;
//	}
	
}
