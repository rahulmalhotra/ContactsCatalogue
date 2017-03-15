package com.codeitnow.contactscatalogue;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codeitnow.contactscatalogue.ContactObject.Contact;

public class ViewContact extends AppCompatActivity {

    DatabaseHandler db;
    ListView contactdet;
    ArrayAdapter<String> arrayAdapter;
    Button editcontact,deletecontact,sms;
    int contactid;
    Contact c;

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
        setContentView(R.layout.activity_view_contact);
        setTitle("View Contact");

        db = new DatabaseAdapter().getDatabase();
        contactdet = (ListView) findViewById(R.id.contactdet);
        editcontact = (Button) findViewById(R.id.editcontact);
        deletecontact = (Button) findViewById(R.id.deletecontact);
        sms = (Button) findViewById(R.id.sms);

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"+c.phoneno));
                ViewContact.this.finish();
                startActivity(sendIntent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#A02C2C"));
        actionBar.setBackgroundDrawable(colorDrawable);

        editcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",contactid);
                Intent intent = new Intent(ViewContact.this, EditContact.class);
                intent.putExtras(bundle);
                //ViewContact.this.finish();
                startActivity(intent);
            }
        });
        sms.setTextColor(Color.WHITE);
        sms.setBackgroundColor(Color.parseColor("#A02C2C"));
        editcontact.setTextColor(Color.WHITE);
        editcontact.setBackgroundColor(Color.parseColor("#A02C2C"));
        deletecontact.setTextColor(Color.WHITE);
        deletecontact.setBackgroundColor(Color.parseColor("#A02C2C"));
        deletecontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.deleteContact(contactid))
                {
                    Toast.makeText(ViewContact.this, "Contact Deleted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ViewContact.this, "Error Try Again", Toast.LENGTH_SHORT).show();
                }
                ViewContact.this.finish();
            }
        });


        Bundle bundle = getIntent().getExtras();
        contactid = bundle.getInt("id");
        //Toast.makeText(this, ""+contactid, Toast.LENGTH_SHORT).show();
        c = db.getContact(contactid);
        String []contact1 = new String[3];
        contact1[0] = c.name;
        contact1[1] = c.phoneno;
        contact1[2] = c.otherdata;
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.contactlist,contact1);
        contactdet.setAdapter(arrayAdapter);
        contactdet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+c.phoneno));
                if (ActivityCompat.checkSelfPermission(ViewContact.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                ViewContact.this.finish();
                startActivity(callIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
        //super.onBackPressed();
    }
}
