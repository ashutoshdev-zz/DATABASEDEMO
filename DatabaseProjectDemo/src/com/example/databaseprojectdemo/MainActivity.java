package com.example.databaseprojectdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

	private String name,address,course;
	private SQLiteDatabase db;
	private SQLiteStatement stm;
	private static final String DBTABLE="Student";
	EditText txt1,txt2,txt3;
	Button b1,b2,b3,b4,b5;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        DBHelper hp=new DBHelper(this);
        db=hp.getWritableDatabase();
        stm=db.compileStatement("insert into  " + "Student" + " (name,address,course) values (?, ?, ?)");
        
        txt1=(EditText)findViewById(R.id.editText1);
        txt2=(EditText)findViewById(R.id.editText2);
        txt3=(EditText)findViewById(R.id.editText3);
        
        b1=(Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				name=txt1.getText().toString().trim();
				address=txt2.getText().toString().trim();
				course=txt3.getText().toString().trim();
				
				stm.bindString(1,name);
				stm.bindString(2,address);
				stm.bindString(3,course);
				
				stm.executeInsert();
				Toast.makeText(getBaseContext(), "Record saved Successfully...",Toast.LENGTH_SHORT).show();
				txt1.setText("");
				txt2.setText("");
				txt3.setText("");
			}
		});
        b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in=new Intent(MainActivity.this,ShowDemo.class);
				startActivity(in);
			}
		});
        b3=(Button)findViewById(R.id.button3);
        b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in=new Intent(MainActivity.this,DeleteDemo.class);
				startActivity(in);
			}
		});
        
        b4=(Button)findViewById(R.id.button4);
        b4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in=new Intent(MainActivity.this,UpdateDemo.class);
				startActivity(in);
			}
		});
        b5=(Button)findViewById(R.id.button5);
        b5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in=new Intent(MainActivity.this,SearchDemo.class);
				startActivity(in);
			}
		});
    }

}
