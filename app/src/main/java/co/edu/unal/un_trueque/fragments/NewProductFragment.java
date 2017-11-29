package co.edu.unal.un_trueque.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.edu.unal.un_trueque.R;
import co.edu.unal.un_trueque.objects.Product;

import static android.app.Activity.RESULT_OK;

public class NewProductFragment extends Fragment {

    private static final String ID = "ID";
    private static final List<String> list = Arrays.asList("Producto", "Servicio");

    private Spinner type;
    private ImageView image;
    private EditText name, description;
    private String id, selectedImagePath;

    public NewProductFragment() {
    }

    public static NewProductFragment newInstance(String id) {
        NewProductFragment fragment = new NewProductFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.id = getArguments().getString(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_product, container, false);

        type = (Spinner) root.findViewById(R.id.typeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        image = (ImageView) root.findViewById(R.id.imageProduct);

        name = (EditText) root.findViewById(R.id.nameProduct);
        description = (EditText) root.findViewById(R.id.descriptionEditText);

        return root;
    }

    public void saveProduct(Context context){
        new NewProductTask(context).execute(name.getText().toString(), type.getSelectedItem().toString(),
                description.getText().toString(), "1", id);
    }

    public void loadImage(Context context){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = selectedImageUri.getPath();
                System.out.println("Image Path : " + selectedImagePath);
                image.setImageURI(selectedImageUri);
            }
        }
    }

    private class NewProductTask extends AsyncTask<String,Void,JSONObject>{

        Context context;
        ProgressDialog progressDialog;

        public NewProductTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Guardando producto...");
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonString = null;

            try{

                String mUrl = "http://www.procesosyoperaciones.com/untrueque.php?q=create_product";

                Uri buildUri = Uri.parse(mUrl).buildUpon()
                        .appendQueryParameter("name", params[0])
                        .appendQueryParameter("type", params[1])
                        .appendQueryParameter("description", params[2])
                        .appendQueryParameter("image", params[3])
                        .appendQueryParameter("user", params[4])
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

                JSONObject r = null;
                try {

                    r = new JSONObject(jsonString);

                }catch (Exception e){
                    Log.e("Casting to json", e.toString());
                }

                return r;

            }

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            progressDialog.dismiss();
            getFragmentManager().popBackStackImmediate();
        }
    }

}
