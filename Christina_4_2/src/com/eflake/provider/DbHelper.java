package com.eflake.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "eflake_db";
	public static final int DATABASE_VERSION = 1;
	public static final String ITEMS_TABLE_NAME = "item";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + ITEMS_TABLE_NAME + " (" + Model._ID
				+ " INTEGER PRIMARY KEY," + Model.DIARY_ID + " INTEGER,"
				+ Model.DIARY_TITLE + " TEXT," + Model.DIARY_DESCRIPTION
				+ " TEXT," + Model.DIARY_PIC_URL + " TEXT,"
				+ Model.DIARY_CONTENT + " TEXT" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXITS item";
		db.execSQL(sql);
	}

	// ²éÑ¯²Ù×÷
	public Cursor query(String sql, String[] args) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, args);
		return cursor;
	}

}
