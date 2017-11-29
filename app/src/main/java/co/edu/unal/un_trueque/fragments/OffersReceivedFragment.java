package co.edu.unal.un_trueque.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.List;

import co.edu.unal.un_trueque.ProductAdapter;
import co.edu.unal.un_trueque.R;
import co.edu.unal.un_trueque.objects.Product;

public class OffersReceivedFragment extends Fragment {

    private static final String ID = "ID";
    private static final String PROD = "Producto";

    private String id;
    private TextView name;
    private Product product;
    private ImageView image;
    private GridView gridView;
    private ProductAdapter productAdapter;

    public OffersReceivedFragment() {
    }

    public static OffersReceivedFragment newInstance(String id, Product product) {
        OffersReceivedFragment fragment = new OffersReceivedFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        args.putSerializable(PROD, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.id = getArguments().getString(ID);
            this.product = (Product) getArguments().getSerializable(PROD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_offers_received, container, false);


        final Context mContext = root.getContext();

        name = (TextView) root.findViewById(R.id.nameImage);
        name.setText(product.getName());

        image = (ImageView) root.findViewById(R.id.productImage);

        productAdapter = new ProductAdapter(getContext(), new ArrayList<Product>());
        gridView = (GridView) root.findViewById(R.id.gridView);
        gridView.setAdapter(productAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long iden) {
                //Product p = (Product) parent.getItemAtPosition(position);
                //new OfferFragment.MakeOfferTask(mContext).execute(id, id, product.getId()+"", p.getId()+"", "");
                //getFragmentManager().popBackStackImmediate();
            }
        });

        new GetMyOffersTask(root.getContext()).execute(product.getId());

        return root;
    }

    private class GetMyOffersTask extends AsyncTask<String,Void,List<Product>>{

        Context context;

        public GetMyOffersTask(Context context){
            this.context = context;
        }

        @Override
        protected List<Product> doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            JSONArray jsonString = null;

            try{

                String mUrl = "http://www.procesosyoperaciones.com/untrueque.php?q=get_offers";

                Uri buildUri = Uri.parse(mUrl).buildUpon()
                        .appendQueryParameter("idProduct", params[0])
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
