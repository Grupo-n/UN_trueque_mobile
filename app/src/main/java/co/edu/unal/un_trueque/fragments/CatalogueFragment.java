package co.edu.unal.un_trueque.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import co.edu.unal.un_trueque.ProductAdapter;
import co.edu.unal.un_trueque.R;
import co.edu.unal.un_trueque.objects.Product;

public class CatalogueFragment extends Fragment {

    private GridView gridView;
    private ProductAdapter productAdapter;

    public CatalogueFragment() {
    }

    public static CatalogueFragment newInstance() {
        CatalogueFragment fragment = new CatalogueFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_catalogue, container, false);


        List<Product> products = new ArrayList<>();
        /*
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        */
        productAdapter = new ProductAdapter(getContext(), products);
        gridView = (GridView) root.findViewById(R.id.gridView);
        gridView.setAdapter(productAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Product p = (Product) parent.getItemAtPosition(position);
                Fragment fragment = ProductDetailFragment.newInstance(p);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });

        new ProductsTask(root.getContext()).execute("1");
        
        return root;
    }

    private class ProductsTask extends AsyncTask<String,Void,List<Product>>{

        Context context;

        public ProductsTask(Context context){
            this.context = context;
        }

        @Override
        protected List<Product> doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            JSONArray jsonString = null;

            try{

                String mUrl = "http://www.procesosyoperaciones.com/untrueque.php?q=get_products";

                Uri buildUri = Uri.parse(mUrl).buildUpon()
                        .appendQueryParameter("user", params[0])
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
                jsonString = new JSONArray(stringBuffer.toString());

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
                    List<Product> products = new ArrayList<>();
                    for (int i = 0; i < jsonString.length(); i++) {
                        JSONObject x = (JSONObject) jsonString.get(i);
                        products.add(new Product(R.drawable.noimage, x.getString("name"), x.getString("type"), x.getString("description")));
                    }
                    return products;
                }catch (Exception e){
                    return null;
                }

            }

        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            productAdapter = new ProductAdapter(context, products);
            gridView.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
        }
    }

}
