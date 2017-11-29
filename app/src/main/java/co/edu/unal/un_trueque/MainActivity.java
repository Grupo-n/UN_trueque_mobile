package co.edu.unal.un_trueque;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import co.edu.unal.un_trueque.fragments.CatalogueFragment;
import co.edu.unal.un_trueque.fragments.LoginFragment;
import co.edu.unal.un_trueque.fragments.MyPostsFragment;
import co.edu.unal.un_trueque.fragments.NewProductFragment;
import co.edu.unal.un_trueque.fragments.ProductDetailFragment;
import co.edu.unal.un_trueque.fragments.RegisterFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        if(savedInstanceState == null){
            Fragment fragment = null;
            try{
                fragment = CatalogueFragment.newInstance(id);
            }catch (Exception e){
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    //Buttons
    public void addClick(View view){
        MyPostsFragment fragment = (MyPostsFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        fragment.createProduct();
    }

    public void acceptClick(View view){
        NewProductFragment fragment = (NewProductFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        fragment.saveProduct(view.getContext());
    }

    public void imageClick(View view){
        NewProductFragment fragment = (NewProductFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        fragment.loadImage(view.getContext());
    }

    public void offertClick(View view){
        ProductDetailFragment fragment = (ProductDetailFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        fragment.makeOffer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;

        switch (item.getItemId()){
            case R.id.home:
                fragment = CatalogueFragment.newInstance(id);
                break;
            case R.id.myposts:
                fragment = MyPostsFragment.newInstance(id);
                break;
            case R.id.logout:
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            default:
                return true;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
