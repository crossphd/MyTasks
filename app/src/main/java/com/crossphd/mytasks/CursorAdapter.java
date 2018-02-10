package com.crossphd.mytasks;

/**
 * Created by chris on 2/8/2018.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This CustomCursorAdapter creates and binds ViewHolders, that hold the description and priority of a task,
 * to a RecyclerView to efficiently display data.
 */
public class CursorAdapter extends RecyclerView.Adapter<CursorAdapter.TaskViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;

    public CursorAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(TaskContract.TaskEntry._ID);
        int descriptionIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION);
        int priorityIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY);
        int completedIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_COMPLETED);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        final String idString = Integer.toString(id);
        final String description = mCursor.getString(descriptionIndex);
        final int priority = mCursor.getInt(priorityIndex);
        boolean completed = mCursor.getInt(completedIndex) != 0;

        //Set values
        holder.itemView.setTag(id);
        holder.taskDescriptionView.setText(description);
        holder.completedView.setChecked(completed);

        holder.completedView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Uri uri = (TaskContract.TaskEntry.CONTENT_URI).buildUpon().appendPath(idString).build();

                ContentValues contentValues = new ContentValues();
                contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, description);
                contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, priority);
                contentValues.put(TaskContract.TaskEntry.COLUMN_COMPLETED, isChecked);
                Context context = buttonView.getContext();
                int returned = context.getContentResolver().update(uri, contentValues, null, null);
            }
        });


        // Programmatically set the text and color for the priority TextView
        String priorityString = "" + priority; // converts int to String
        holder.priorityView.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);

    }


    /**
    Helper method for selecting the correct priority circle color.
    P1 = red, P2 = orange, P3 = yellow
    */
    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch(priority) {
            case 1: priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2: priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3: priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default: break;
        }
        return priorityColor;
    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView taskDescriptionView;
        TextView priorityView;
        CheckBox completedView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskDescriptionView = (TextView) itemView.findViewById(R.id.taskDescription);
            priorityView = (TextView) itemView.findViewById(R.id.priorityTextView);
            completedView = (CheckBox) itemView.findViewById(R.id.completed);
        }


    }
}
