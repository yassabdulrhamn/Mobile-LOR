package lorteam.mobilelor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FAQsActivity extends AppCompatActivity {
    TextView oQuestion,textViewAnswer;
    int question_num;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
   private ImageView home;





    @SuppressLint("RestrictedApi")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        home=(ImageView)findViewById(R.id.imageHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(FAQsActivity.this, MainActivity.class);
                startActivity(h);
            }
        });
            oQuestion = (TextView) findViewById(R.id.textView);
        textViewAnswer = (TextView) findViewById(R.id.textViewAnswer);



        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            oQuestion.setText("Q:" + getResources().getStringArray(R.array.ArrayOuestions)[bundle.getInt("SelectedIteamPosition")]);
            textViewAnswer.setText("A:" + getResources().getStringArray(R.array.ArrayAnswers)[bundle.getInt("SelectedIteamPosition")]);
            question_num = bundle.getInt("SelectedIteamPosition");
        }
        textViewAnswer.setMovementMethod(new ScrollingMovementMethod());


        //Sliding menu code:
       // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        //mDrawerLayout.addDrawerListener(mToggle);
        //mToggle.setDrawerIndicatorEnabled(false);

       // mToggle.syncState();

        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);



        //This code is to make the page listen to the left side menu
        //1- Define the left side menu in the JAVA so can work with it in here
       // NavigationView navigationView = (NavigationView) findViewById(R.id.mNavigationView);
        //2- We make this page listen to what happen in the left slide menu
        //navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
         //   @Override
            //3- What to do when an action happen in the left slide menu
           // public boolean onNavigationItemSelected(MenuItem menuItem) {
                //(menuItem) is the item that have be selected by the user
                //menuItem.setChecked(true); === This code to color the selected item, no need for now.

                //4- close the left slide menu
               // mDrawerLayout.closeDrawers();
                //5- also print me a toast that shows what we have select
               // Toast.makeText(FAQsActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                //6-  define an empty intent so it can fill  later on to jump into another page
               // Intent intent;
                //7- switch that will have the ID of the select iteam from the left slide menu (Example: nav_account)
               // switch (menuItem.getItemId())
               // {
                    //if select ""Account" which has (nav_account) as an ID then do what is next:
                   // case R.id.nav_account:
                        //8- fill our empty intent with a page that we are going to intent
                      //  intent = new Intent(FAQsActivity.this, Myprofile.class);
                        //startActivity(intent);
                        //return true;
                    //case R.id.nav_settings:
                       // intent = new Intent(FAQsActivity.this, SettingsActivity.class);
                        //startActivity(intent);
                        //return true;
                    //case R.id.nav_FAQs:
                      //  intent = new Intent(FAQsActivity.this, listFAQ.class);
                        //startActivity(intent);
                        //return true;

                }
               // return true;
           // }
        //});

   // }
    //public boolean onOptionsItemSelected(MenuItem item)
    //{
      //  if (mToggle.onOptionsItemSelected(item)){
        //    return true;
        //}
        //return super.onOptionsItemSelected(item);
    //}

    public  void NextOuestion (View view){
        if(question_num != getResources().getStringArray(R.array.ArrayAnswers).length-1) {
            Intent intent = new Intent(FAQsActivity.this, FAQsActivity.class);
            intent.putExtra("SelectedIteamPosition", question_num + 1);
            startActivity(intent);
        }
        else
            Toast.makeText(FAQsActivity.this, "This is the last Question", Toast.LENGTH_LONG).show();
    }

    public  void PreviewsOuestion (View view){
        if(question_num != 0) {
            Intent intent = new Intent(FAQsActivity.this, FAQsActivity.class);
            intent.putExtra("SelectedIteamPosition", question_num - 1);
            startActivity(intent);
        }
        else
            Toast.makeText(FAQsActivity.this, "This is the first Question", Toast.LENGTH_LONG).show();
    }


    //To control what the back button  should do, And it is going to the SecondActivity

}

