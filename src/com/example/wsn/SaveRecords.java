package com.example.wsn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class SaveRecords extends Activity implements OnClickListener {

	private Button addTodoBtn;
	private EditText subjectEditText;
	static String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setTitle("Add Record");
        
        setContentView(R.layout.save_record);
        
        subjectEditText = (EditText) findViewById(R.id.subject_edittext);
        
        addTodoBtn = (Button) findViewById(R.id.add_record);
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.add_record:
        	
            name = subjectEditText.getText().toString();
        
            
            Intent main = new Intent(SaveRecords.this, Data.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            
            startActivity(main);
            break;
        }
    }

}
