package co.edu.unal.un_trueque.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

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
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));
        products.add(new Product(R.drawable.book, "Libro", "Donación", "Libro de cálculo universitario"));

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
        
        return root;
    }

}
