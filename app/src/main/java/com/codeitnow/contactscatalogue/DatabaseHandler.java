package com.codeitnow.contactscatalogue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codeitnow.contactscatalogue.ContactObject.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Malhotra on 12/22/2016.
 */
    public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int Database_version = 1;
    private static final String Database_name = "contactscatalogue";

    public DatabaseHandler(Context context) {
        super(context, Database_name, null, Database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Contacts ( id INTEGER PRIMARY KEY, name TEXT, phone TEXT, otherdetail TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Contacts");
        onCreate(sqLiteDatabase);
    }

    public int addcontact(Contact c)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", c.name);
        values.put("phone", c.phoneno);
        values.put("otherdetail",c.otherdata);
        db.insert("Contacts", null, values);
        db.close();
        return 1;
    }

    public Contact getContact(int id)
    {
        Contact contact = new Contact();
        String selectQuery = "SELECT  * FROM Contacts where id = "+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            contact.name = cursor.getString(1);
            contact.phoneno = cursor.getString(2);
            contact.otherdata = cursor.getString(3);
        }
        return contact;
    }

    public boolean updateContact(Contact c)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",c.name);
        values.put("phone",c.phoneno);
        values.put("otherdetail",c.otherdata);
        return db.update("Contacts",values,"id ="+c.id,null)>0;
    }

    public boolean deleteContact(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Contacts","id = "+id,null)>0;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM Contacts";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.id = Integer.parseInt(cursor.getString(0));
                contact.name = cursor.getString(1);
                contact.phoneno = cursor.getString(2);
                contact.otherdata = cursor.getString(3);
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
}
