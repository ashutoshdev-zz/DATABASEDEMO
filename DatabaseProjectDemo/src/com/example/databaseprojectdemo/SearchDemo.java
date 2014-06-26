package com.example.databaseprojectdemo;

import java.util.ArrayList;
import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchDemo extends Activity {

	private SQLiteDatabase db;
	private static final String DBTABLE = "Student";
	TextView t1, t2, t3;
	EditText txt;
	Button b1, b2;
	ArrayList<String> adapter = new ArrayList<String>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		DBHelper hp = new DBHelper(this);
		db = hp.getWritableDatabase();

		t1 = (TextView) findViewById(R.id.textView2);
		t2 = (TextView) findViewById(R.id.textView3);
		t3 = (TextView) findViewById(R.id.textView4);

		txt = (EditText) findViewById(R.id.editText1);

		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				long id = Long.parseLong(txt.getText().toString());
				Cursor c = db.rawQuery("select name,address,course from "
						+ DBTABLE + " where id= " + id, null);
				try {
					if (c.moveToFirst()) {
						do {
							t1.setText("Name :"
									+ c.getString(c.getColumnIndex("name"))
									+ "\n");
							t2.setText("Address :"
									+ c.getString(c.getColumnIndex("address"))
									+ "\n");
							t3.setText("Course :"
									+ c.getString(c.getColumnIndex("course")));

						} while (c.moveToNext());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (db != null) {
						db.close();
					}
				}
			}
		});
		b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
