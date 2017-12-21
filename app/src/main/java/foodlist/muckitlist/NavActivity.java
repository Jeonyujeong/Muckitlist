package foodlist.muckitlist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import foodlist.muckitlist.Activity.LoginActivity;
import foodlist.muckitlist.Fragment.FriendFragment;
import foodlist.muckitlist.Fragment.MenuFragment;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView nameTextView;
    private TextView emailTextView;
    private ImageButton settingButton;
    private FirebaseAuth auth;
    private EditText searchEditText;
    Fragment mapFragment ;
    FragmentManager manager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("googlmap", "Nav: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);

        nameTextView = (TextView)view.findViewById(R.id.header_name_textView);
        emailTextView = (TextView)view.findViewById(R.id.header_email_textView);

        nameTextView.setText(auth.getCurrentUser().getDisplayName());
        emailTextView.setText(auth.getCurrentUser().getEmail());

        settingButton = (ImageButton)view.findViewById(R.id.setting);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick", "ok");

            //    Intent intent = new Intent();
            //    startActivity(intent);
            }
        });

        mapFragment = new MapFragment();
        manager.beginTransaction().add(R.id.content_nav, mapFragment).commit();

        searchEditText= (EditText) findViewById(R.id.search_text);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.search) {
            String search = searchEditText.getText().toString();
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("search", search);
            startActivity(intent);
        } else if(id == R.id.setting) {
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
        }

    return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction manager = getFragmentManager().beginTransaction();

        if (id == R.id.nav_map) {
            manager.replace(R.id.content_nav, new MapFragment());
            manager.addToBackStack(null);
            manager.commit();
            Toast.makeText(this, "map", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.nav_menu) {
            manager.replace(R.id.content_nav, new MenuFragment());
            manager.addToBackStack(null);
            manager.commit();
            Toast.makeText(getApplicationContext(), "menu", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_friend) {
            manager.replace(R.id.content_nav, new FriendFragment());
            manager.addToBackStack(null);
            manager.commit();
            Toast.makeText(getApplicationContext(), "friend", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_logout) {
            auth.signOut();
            finish();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        } else if(id == R.id.setting) {
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("googlmap", "Nav: onNewIntent");
        super.onNewIntent(intent);
        Double title_lat = intent.getDoubleExtra("lat", -1);
        Double title_lng = intent.getDoubleExtra("lng", -1);
        String title = intent.getStringExtra("title");
        String address = intent.getStringExtra("address");
    //    FoodItem item = (FoodItem) intent.getSerializableExtra("item");

        Toast.makeText(this, title_lat+", "+ title_lng, Toast.LENGTH_SHORT).show();

//        manager.findFragmentById(R.id.content_nav);
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", title_lat);
        bundle.putDouble("lng", title_lng);
        bundle.putString("title", title);
        bundle.putString("address", address);
    //    bundle.putSerializable("item", (Serializable) item);
        mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);
        manager.beginTransaction().replace(R.id.content_nav, mapFragment).commit();

    }

    @Override
    protected void onStop() {
        Log.d("googlmap", "Nav: onStop");
        super.onStop();
  //      manager.saveFragmentInstanceState(mapFragment);
    }

    @Override
    protected void onStart() {
        Log.d("googlmap", "Nav: onStart");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        Log.d("googlmap", "Nav: onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("googlmap", "Nav: onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("googlmap", "Nav: onResume");
        super.onResume();
    }
}
