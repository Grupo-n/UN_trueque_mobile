package co.edu.unal.un_trueque;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
        products.add(new Product(R.drawable.book, "Libro", "Donación"));
        products.add(new Product(R.drawable.book, "Libro", "Donación"));
        products.add(new Product(R.drawable.book, "Libro", "Donación"));
        products.add(new Product(R.drawable.book, "Libro", "Donación"));
        products.add(new Product(R.drawable.book, "Libro", "Donación"));
        products.add(new Product(R.drawable.book, "Libro", "Donación"));
        products.add(new Product(R.drawable.book, "Libro", "Donación"));


        gridView = (GridView) root.findViewById(R.id.gridView);
        productAdapter = new ProductAdapter(getContext(), products);
        gridView.setAdapter(productAdapter);
        
        return root;
    }

}
