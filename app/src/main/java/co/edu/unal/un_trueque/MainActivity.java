package co.edu.unal.un_trueque;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Toast.makeText(view.getContext(), "Menu", Toast.LENGTH_LONG).show();
    }

}
