package co.edu.unal.un_trueque;

import android.app.ProgressDialog;
import android.content.Context;
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

public class RegisterActivity extends AppCompatActivity {

    boolean succes = false;
    EditText name, lastname, email, password, confirmation, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.r_nameEditText);
        lastname = (EditText) findViewById(R.id.r_lastnameEditText);
        email= (EditText) findViewById(R.id.r_emailEditText);
        password = (EditText) findViewById(R.id.r_passwordEditText);
        confirmation = (EditText) findViewById(R.id.r_confirmationEditText);
        phone = (EditText) findViewById(R.id.r_confirmationEditText);

    }

    public void registerClick(View view){

        if(name.getText().toString().equals("") || lastname.getText().toString().equals("") || email.getText().toString().equals("") ||
            password.getText().toString().equals("") || !password.getText().toString().equals(confirmation.getText().toString())){

            Toast.makeText(view.getContext(), "Hay un error en tus datos", Toast.LENGTH_LONG).show();

        }else{

            new RegisterTask(view.getContext()).execute(name.getText().toString(), lastname.getText().toString(),
                    email.getText().toString(), password.getText().toString(), phone.getText().toString());

        }

    }

    public void end(){
        finish();
    }

    private class RegisterTask extends AsyncTask<String, Void, JSONObject>{

        Context context;
        ProgressDialog progressDialog;

        public RegisterTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Registrandote...");
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonString = null;

            try{

                String mUrl = "http://www.procesosyoperaciones.com/untrueque.php?q=register";

                Uri buildUri = Uri.parse(mUrl).buildUpon()
                        .appendQueryParameter("name", params[0])
                        .appendQueryParameter("lastname", params[1])
                        .appendQueryParameter("email", params[2])
                        .appendQueryParameter("password", params[3])
                        .appendQueryParameter("phone", params[4])
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

            }catch (Exception e){

                Log.e("Register Activity: ", e.toString());

            }finally {

                if(urlConnection != null)
                    urlConnection.disconnect();

                if(reader != null)
                    try{
                        reader.close();
                    }catch (IOException e){
                        Log.e("Reader", e.toString());
                    }

                try {
                    return new JSONObject(jsonString);
                } catch (JSONException e) {
                    return null;
                }

            }

        }

        @Override
        protected void onPostExecute(JSONObject s) {
            progressDialog.dismiss();

            if(!s.toString().equals(""))
                end();
            else
                Toast.makeText(context, "Error, intentalo de nuevo", Toast.LENGTH_LONG).show();

        }
    }

}
