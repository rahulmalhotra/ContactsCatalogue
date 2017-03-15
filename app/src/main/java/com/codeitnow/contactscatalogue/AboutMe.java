package com.codeitnow.contactscatalogue;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class AboutMe extends AppCompatActivity {

    Button blog,linkedin,github;

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
        setContentView(R.layout.activity_about_me);
        setTitle("About the Developer");

        blog =(Button)findViewById(R.id.blog);
        blog.setClickable(true);
        blog.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://rahulmalhotraofficial.blogspot.in'> My Blog </a>";
        blog.setText(Html.fromHtml(text));
        blog.setTextColor(Color.WHITE);
        blog.setBackgroundColor(Color.parseColor("#A02C2C"));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#A02C2C"));
        actionBar.setBackgroundDrawable(colorDrawable);

        linkedin =(Button)findViewById(R.id.linkedin);
        linkedin.setClickable(true);
        linkedin.setMovementMethod(LinkMovementMethod.getInstance());
        String text1 = "<a href='http://www.linkedin.com/in/rahulmalhotra9620'> Connect with me on LinkedIn </a>";
        linkedin.setText(Html.fromHtml(text1));
        linkedin.setTextColor(Color.WHITE);
        linkedin.setBackgroundColor(Color.parseColor("#A02C2C"));

        github =(Button)findViewById(R.id.github);
        github.setClickable(true);
        github.setMovementMethod(LinkMovementMethod.getInstance());
        String text2 = "<a href='http://www.github.com/rahulmalhotra9620'> My Github Repo. </a>";
        github.setText(Html.fromHtml(text2));
        github.setTextColor(Color.WHITE);
        github.setBackgroundColor(Color.parseColor("#A02C2C"));

    }

    @Override
    public void onBackPressed() {
        this.finish();
        //super.onBackPressed();
    }
}
