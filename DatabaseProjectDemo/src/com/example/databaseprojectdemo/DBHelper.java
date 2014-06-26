package com.example.databaseprojectdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DBNAME = "MYDB";
	public static final String DBTABLE = "Student";
	public static final int version = 1;

	public DBHelper(Context context) {
		super(context, "MYDB", null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL("create table  "
				+ "Student"
				+ " (id integer primary key autoincrement,name text not null,address text,course text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
