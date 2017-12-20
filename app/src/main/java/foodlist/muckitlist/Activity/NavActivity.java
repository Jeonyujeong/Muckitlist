package foodlist.muckitlist.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import foodlist.muckitlist.Fragment.FriendFragment;
import foodlist.muckitlist.Fragment.MapFragment;
import foodlist.muckitlist.Fragment.MenuFragment;
import foodlist.muckitlist.R;
import foodlist.muckitlist.SearchActivity;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView nameTextView;
    private TextView emailTextView;
    private FirebaseAuth auth;
    private EditText searchEditText;
    Fragment mapFragment ;
    FragmentManager manager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        }


/**
        if (id == R.id.action_settings) {
            Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();

            startActivity(intent);
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager manager = getFragmentManager();

        if (id == R.id.nav_map) {
            manager.beginTransaction().replace(R.id.content_nav, new MapFragment()).commit();
            Toast.makeText(this, "map", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.nav_menu) {
            manager.beginTransaction().replace(R.id.content_nav, new MenuFragment()).commit();
            Toast.makeText(getApplicationContext(), "menu", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_friend) {
            manager.beginTransaction().replace(R.id.content_nav, new FriendFragment()).commit();
            Toast.makeText(getApplicationContext(), "friend", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_logout) {
            auth.signOut();
            finish();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Double title_lat = intent.getDoubleExtra("lat", -1);
        Double title_lng = intent.getDoubleExtra("lng", -1);

        Toast.makeText(this, title_lat+", "+ title_lng, Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putDouble("lat", title_lat);
        bundle.putDouble("lng", title_lng);
        mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);

        manager.beginTransaction().add(R.id.content_nav, mapFragment).commit();

    }
}
