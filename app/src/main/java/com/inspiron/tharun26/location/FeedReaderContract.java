package com.inspiron.tharun26.location;

import android.provider.BaseColumns;

/**
 * Created by tharun26 on 13/1/15.
 */
public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_SNO = "enty_sno";
        public static final String COLUMN_NAME_DATE = "enty_date";
        public static final String COLUMN_NAME_TIME = "enty_time";
        public static final String COLUMN_NAME_PLACE = "entry_place";

    }
}