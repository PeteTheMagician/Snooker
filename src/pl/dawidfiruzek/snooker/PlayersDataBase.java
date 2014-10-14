package pl.dawidfiruzek.snooker;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayersDataBase extends SQLiteOpenHelper{

	private final String DB_TITLE = "players";
	private final String DB_NUMBER = "number";
	private final String DB_NAME = "name";
	private final String DB_FRAMES_WON = "framesWon";
	private final String DB_FRAMES_LOST = "framesLost";
	private final String DB_MAX_BREAK = "maxBreak";
	private final String DB_POINTS = "points";
	
	public PlayersDataBase (Context context){
		super(context, "players.db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
				"create table " + DB_TITLE + "(" +
				DB_NUMBER + " integer primary key autoincrement," +
				DB_NAME + " text," +
				DB_FRAMES_WON + " integer," +
				DB_FRAMES_LOST + " integer," +
				DB_MAX_BREAK + " integer," +
				DB_POINTS + " integer);" +
				"");		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void addPlayer(String name){
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DB_NAME, name);
		values.put(DB_FRAMES_WON, 0);
		values.put(DB_FRAMES_LOST, 0);
		values.put(DB_MAX_BREAK, 0);
		values.put(DB_POINTS, 0);
		db.insertOrThrow(DB_TITLE, null, values);
	}

	public Cursor getAll(){
		String[] columns = {DB_NUMBER, DB_NAME, DB_FRAMES_WON, DB_FRAMES_LOST, DB_MAX_BREAK, DB_POINTS};
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(DB_TITLE, columns, null, null, null, null, null);		
		return cursor;
	}
	
	public void deletePlayer(int id){
		SQLiteDatabase db = getWritableDatabase();
		String[] arg = {Integer.toString(id)};
		db.delete(DB_TITLE, DB_NUMBER+"=?", arg);
	}
	
	public void deleteAllPlayers(){
		SQLiteDatabase db = getWritableDatabase();
		db.delete(DB_TITLE, null, null);
	}
	
	
	//TODO read from db and update!
	public void updatePlayer(String name, int framesWon, int framesLost, int maxBreak, int points){
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(DB_FRAMES_WON, framesWon);
		values.put(DB_FRAMES_LOST, framesLost);
		values.put(DB_MAX_BREAK, maxBreak);
		values.put(DB_POINTS, points);
		String[] args = {name};
		db.update(DB_TITLE, values, DB_NAME+"=?", args);
	}
}
