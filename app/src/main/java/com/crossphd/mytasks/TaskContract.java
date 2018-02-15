package com.crossphd.mytasks;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by chris on 2/8/2018.
 */

class TaskContract {

    static final String AUTHORITY = "com.crossphd.mytasks";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    static final String PATH_TASKS = "tasks";
    static final class TaskEntry implements BaseColumns {

        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();
        static final String TABLE_NAME = "tasks";
        static final String COLUMN_DESCRIPTION = "description";
        static final String COLUMN_PRIORITY = "priority";
        static final String COLUMN_COMPLETED = "completed";

    }
}
