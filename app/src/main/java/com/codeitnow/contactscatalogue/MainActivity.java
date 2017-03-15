package com.codeitnow.contactscatalogue;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codeitnow.contactscatalogue.ContactObject.Contact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHandler db;
    DatabaseAdapter db1;
    List<Contact> contactList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setDrawingCacheBackgroundColor(Color.parseColor("#A02C2C"));
        //fab.setBackgroundColor(Color.parseColor("#A02C2C"));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddContact.class);
                startActivity(intent);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#A02C2C"));
        actionBar.setBackgroundDrawable(colorDrawable);

        db1 = new DatabaseAdapter();
        db1.setDatabaseAdapter(this);
        db = db1.getDatabase();
        ArrayList<String> contactnames = new ArrayList<String>();
        final ArrayList<Integer> contactid = new ArrayList<Integer>();
        contactList = db.getAllContacts();
        Contact c[] = new Contact[contactList.size()];
        for(int i=0;i<contactList.size();i++)
        {
            c[i] = contactList.get(i);
            //contactid.add(c[i].id);
            //contactnames.add(c[i].name);
        }
        Arrays.sort(c, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return contact.name.compareTo(t1.name);
            }
        });
        for(int i=0;i<contactList.size();i++)
        {
            contactid.add(c[i].id);
            contactnames.add(c[i].name);
        }
        Object[] objDays = contactnames.toArray();
        //Object[] obj1 = contactid.toArray();
        final String[] strcontactnames = Arrays.copyOf(objDays, objDays.length, String[].class);
        //final int[] intcontactnames = Arrays.copyOf(obj1, obj1.length, int[].class);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.contactlist,strcontactnames);
        listView = (ListView) findViewById(R.id.contactlist);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",contactid.get(i));
                Intent intent = new Intent(MainActivity.this,ViewContact.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, AboutMe.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
