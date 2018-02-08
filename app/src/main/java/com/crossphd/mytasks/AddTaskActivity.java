package com.crossphd.mytasks;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by chris on 2/8/2018.
 */

public class AddTaskActivity extends AppCompatActivity {

    // Declare a member variable to keep track of a task's selected mPriority
    private int mPriority;
    private boolean mCompleted;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize to highest mPriority by default (mPriority = 2)
        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
        mPriority = 2;
        mCompleted = false;
    }


    /**
     * onClickAddTask is called when the "ADD" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onClickAddTask(View view) {

        String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();
        if (input.length() == 0) {
            return;
        }

        // Insert new task data via a ContentResolver
        // Create new empty ContentValues object
        ContentValues contentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input);
        contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, mPriority);
        contentValues.put(TaskContract.TaskEntry.COLUMN_COMPLETED, mCompleted);
        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);


//        if(uri != null) {
//            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
//        }

        // Finish activity (this returns back to MainActivity)
        finish();

    }


    /**
     * onPrioritySelected is called whenever a priority button is clicked.
     * It changes the value of mPriority based on the selected button.
     */
    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radButton1)).isChecked()) {
            mPriority = 1;
        } else if (((RadioButton) findViewById(R.id.radButton2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radButton3)).isChecked()) {
            mPriority = 3;
        }
    }
}
