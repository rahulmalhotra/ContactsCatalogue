package com.codeitnow.contactscatalogue;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codeitnow.contactscatalogue.ContactObject.Contact;

public class EditContact extends AppCompatActivity {

    DatabaseHandler db;
    TextView taskname;
    int contactid;
    Contact c;
    Button savecontact;
    EditText contactname,contactphone,contactother;

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
        setTitle("Edit Contact");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#A02C2C"));
        actionBar.setBackgroundDrawable(colorDrawable);

        taskname = (TextView) findViewById(R.id.taskname);
        contactname = (EditText) findViewById(R.id.contactname);
        contactphone = (EditText) findViewById(R.id.contactno);
        contactother = (EditText) findViewById(R.id.contactadd);
        savecontact = (Button) findViewById(R.id.savecontact);
        taskname.setText("Edit Contact");
        Bundle bundle = getIntent().getExtras();
        contactid = bundle.getInt("id");
        db = new DatabaseAdapter().getDatabase();
        c = db.getContact(contactid);
        contactname.setText(c.name);
        contactphone.setText(c.phoneno);
        contactother.setText(c.otherdata);
        savecontact.setTextColor(Color.WHITE);
        savecontact.setBackgroundColor(Color.parseColor("#A02C2C"));
        savecontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact c1 = new Contact();
                c1.id = contactid;
                c1.name = contactname.getText().toString();
                c1.phoneno = contactphone.getText().toString();
                c1.otherdata = contactother.getText().toString();
                if(c1.name.equals("")||c1.phoneno.equals(""))
                {
                    Toast.makeText(EditContact.this, "Please enter name and phone number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(db.updateContact(c1))
                    {
                        Toast.makeText(EditContact.this, "Contact Updated", Toast.LENGTH_SHORT).show();
                        EditContact.this.finish();
                    }
                    else
                    {
                        Toast.makeText(EditContact.this, "Error Updating Contact", Toast.LENGTH_SHORT).show();
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
