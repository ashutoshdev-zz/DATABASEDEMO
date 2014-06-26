package com.example.databaseprojectdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDemo extends Activity {

	private SQLiteDatabase db;
	private static final String DBTABLE = "Student";
	private String name, address, course;
	private long id;
	EditText txt1, txt2, txt3, txt4;
	Button b1, b2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);

		DBHelper hp = new DBHelper(this);
		db = hp.getWritableDatabase();

		txt1 = (EditText) findViewById(R.id.editText1);
		txt2 = (EditText) findViewById(R.id.editText2);
		txt3 = (EditText) findViewById(R.id.editText3);
		txt4 = (EditText) findViewById(R.id.editText4);

		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					id = Long.parseLong(txt1.getText().toString());
					name = txt2.getText().toString();
					address = txt3.getText().toString();
					course = txt4.getText().toString();

					ContentValues cv = new ContentValues();
					cv.put("id", id);
					cv.put("name", name);
					cv.put("address", address);
					cv.put("course", course);

					db.update(DBTABLE, cv, "id " + "=" + id, null);
					Toast.makeText(getBaseContext(),
							"Record updated successfully..", Toast.LENGTH_SHORT)
							.show();
					txt1.setText("");
					txt2.setText("");
					txt3.setText("");
					txt4.setText("");
				} catch (SQLiteException es) {
					es.printStackTrace();
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
				txt1.setText("");
				txt2.setText("");
				txt3.setText("");
				txt4.setText("");
			}
		});
	}
}
