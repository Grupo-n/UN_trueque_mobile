package co.edu.unal.un_trueque;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        CatalogueFragment fragment = (CatalogueFragment) getSupportFragmentManager().findFragmentById(R.id.content_layout);
        if(fragment == null){
            fragment = CatalogueFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.content_layout, fragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void searchClick(View view){
        Toast.makeText(view.getContext(), "Search", Toast.LENGTH_LONG).show();
    }

    public void menuClick(View view){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                Toast.makeText(getApplicationContext(), "Menu_home", Toast.LENGTH_LONG).show();
                Log.e("Error: ", "err");
                break;
            case R.id.my_offers:
                Toast.makeText(getApplicationContext(), "Menu_home", Toast.LENGTH_LONG).show();
                Log.e("Error: ", "err");
                break;
            case R.id.my_posts:
                Toast.makeText(getApplicationContext(), "Menu_home", Toast.LENGTH_LONG).show();
                Log.e("Error: ", "err");
                break;
            case R.id.my_products:
                Toast.makeText(getApplicationContext(), "Menu_home", Toast.LENGTH_LONG).show();
                Log.e("Error: ", "err");
                break;
            case R.id.my_requests:
                Toast.makeText(getApplicationContext(), "Menu_home", Toast.LENGTH_LONG).show();
                Log.e("Error: ", "err");
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
