package com.codeitnow.contactscatalogue;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codeitnow.contactscatalogue.ContactObject.Contact;

public class AddContact extends AppCompatActivity {

    Contact c;
    EditText econtactname,econtactno,econtactadd;
    Button savecontact;
    DatabaseHandler db;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        db = new DatabaseAdapter().getDatabase();
        c = new Contact();
        setTitle("Add New Contact");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#A02C2C"));
        actionBar.setBackgroundDrawable(colorDrawable);

        econtactname = (EditText) findViewById(R.id.contactname);
        econtactno = (EditText) findViewById(R.id.contactno);
        econtactadd = (EditText) findViewById(R.id.contactadd);
        savecontact = (Button) findViewById(R.id.savecontact);

        savecontact.setTextColor(Color.WHITE);
        savecontact.setBackgroundColor(Color.parseColor("#A02C2C"));
        savecontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.name = econtactname.getText().toString();
                c.phoneno = econtactno.getText().toString();
                c.otherdata = econtactadd.getText().toString();
                if(c.name.equals("")||c.phoneno.equals(""))
                {
                    Toast.makeText(AddContact.this, "Please enter name and phone number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int i = db.addcontact(c);
                    if(i==1)
                    {
                        Toast.makeText(AddContact.this, "Contact Saved", Toast.LENGTH_SHORT).show();
                        AddContact.this.finish();
                    }
                    else if(i==0)
                    {
                        Toast.makeText(AddContact.this, "Error Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
        //super.onBackPressed();
    }
}
