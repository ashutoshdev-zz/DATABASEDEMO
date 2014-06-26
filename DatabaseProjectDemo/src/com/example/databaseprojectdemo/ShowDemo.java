package com.example.databaseprojectdemo;

import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ShowDemo extends ListActivity {

	private ArrayList<String> list = new ArrayList<String>();
	private static final String DBTABLE = "Student";
	private SQLiteDatabase db;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setResult();
		showResult();

	}

	public void showResult() {
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, list));
	}

	public void setResult() {
		try {
			DBHelper hp = new DBHelper(this);
			db = hp.getWritableDatabase();
			Cursor c = db.rawQuery(" select id,name,address,course from "
					+ DBTABLE, null);
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						int id = c.getInt(c.getColumnIndex("id"));
						String name = c.getString(c.getColumnIndex("name"));
						String address = c.getString(c
								.getColumnIndex("address"));
						String course = c.getString(c.getColumnIndex("course"));

						list.add(id + " " + name + "  " + address + "  "
								+ course);
					} while (c.moveToNext());
				}
			}
		} catch (SQLiteException s) {
			s.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}
}
