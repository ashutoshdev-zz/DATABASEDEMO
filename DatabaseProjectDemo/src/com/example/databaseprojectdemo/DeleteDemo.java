package com.example.databaseprojectdemo;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteDemo extends Activity {

	EditText txt;
	Button b1, b2;
	private SQLiteDatabase db;
	private SQLiteStatement stm;
	private static final String DBTABLE = "Student";
	long id;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);

		DBHelper hp = new DBHelper(this);
		db = hp.getWritableDatabase();

		txt = (EditText) findViewById(R.id.editText1);
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					long t = Long.parseLong(txt.getText().toString());
					db.delete(DBTABLE, id+" = "+t, null);
					Toast.makeText(getBaseContext(),
							"Record remove successfully..", Toast.LENGTH_SHORT)
							.show();
					txt.setText("");
				} catch (SQLiteException sq) {
					sq.printStackTrace();
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
				txt.setText("");
			}
		});
	}
}
