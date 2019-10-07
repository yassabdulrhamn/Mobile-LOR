package lorteam.mobilelor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class listFAQ extends AppCompatActivity {
    ListView listview;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ImageView home;


    @SuppressLint("RestrictedApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
        home=(ImageView)findViewById(R.id.imageHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h= new Intent(listFAQ.this, MainActivity.class);
                startActivity(h);
            }
        });



        listview = (ListView) findViewById(R.id.days);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(listFAQ.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ArrayOuestions));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(listFAQ.this, FAQsActivity.class);
                intent.putExtra("SelectedIteamPosition", position);
                startActivity(intent);
            }
        });
        listview.setAdapter(mAdapter);
    }
}
