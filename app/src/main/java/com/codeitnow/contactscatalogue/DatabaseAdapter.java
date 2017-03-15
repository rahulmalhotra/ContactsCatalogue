package com.codeitnow.contactscatalogue;

import android.content.Context;

/**
 * Created by Rahul Malhotra on 12/22/2016.
 */

public class DatabaseAdapter {

    private static DatabaseHandler db;
    public void setDatabaseAdapter(Context c)
    {
        db = new DatabaseHandler(c);
    }
    public DatabaseHandler getDatabase()
    {
        return db;
    }

}
