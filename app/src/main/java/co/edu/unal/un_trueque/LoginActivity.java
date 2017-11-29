package co.edu.unal.un_trueque;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passEditText);

    }

    public void launch(String id){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }


    public void loginClick(View view){
        new LoginTask(view.getContext()).execute(email.getText().toString(), password.getText().toString());
    }

    public void forgottenClick(View view){
        Toast.makeText(view.getContext(), "Forgot", Toast.LENGTH_LONG).show();
    }

    public void registerClick(View view){
        startActivity(new Intent(view.getContext(), RegisterActivity.class));
    }

    private class LoginTask extends AsyncTask<String,Void,JSONObject>{

        Context context;
        ProgressDialog progressDialog;

        public LoginTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Iniciando Sesión...");
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonString = null;

            try{

                String mUrl = "http://www.procesosyoperaciones.com/untrueque.php?q=login";

                Uri buildUri = Uri.parse(mUrl).buildUpon()
                        .appendQueryParameter("email", params[0])
                        .appendQueryParameter("password", params[1])
                        .build();

                URL url = new URL(buildUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null){
                    stringBuffer.append(line + "\n");
                }
                jsonString = stringBuffer.toString();

                Log.e("AQUI", jsonString);

            }catch (Exception e){

                Log.e("Login Activity: ", e.toString());

            }finally {

                if(urlConnection != null)
                    urlConnection.disconnect();

                if(reader != null)
                    try{
                        reader.close();
                    }catch (Exception e){
                        Log.e("Reader", e.toString());
                    }

                JSONObject r;
                try {
                    r = new JSONObject(jsonString);
                } catch (JSONException e) {
                    return new JSONObject();
                }
                return r;

            }

        }

        @Override
        protected void onPostExecute(JSONObject s) {

            progressDialog.dismiss();
            //Toast.makeText(context, "Login" + s.toString(), Toast.LENGTH_LONG).show();
            try {

                String id = s.getString("id");
                if(s.isNull("id"))
                    Toast.makeText(context, "No coincide tu E-mail y Contraseña", Toast.LENGTH_LONG).show();
                else
                    launch(id);


            } catch (JSONException e) {
                Toast.makeText(context, "No coincide tu E-mail y Contraseña", Toast.LENGTH_LONG).show();
            }


        }
    }

}
