package co.edu.unal.un_trueque.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.Serializable;

import co.edu.unal.un_trueque.R;
import co.edu.unal.un_trueque.objects.Product;

public class ProductDetailFragment extends Fragment {

    private static final String ID = "ID";
    private static final String ARG_PARAM1 = "product";

    private String id;
    private Product product;

    public ProductDetailFragment() {
    }

    public static ProductDetailFragment newInstance(String id, Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        args.putSerializable(ARG_PARAM1, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.id = getArguments().getString(ID);
            this.product = (Product) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);

        ImageView imageView = (ImageView) root.findViewById(R.id.productImage);
        TextView text1 = (TextView) root.findViewById(R.id.nameText);
        TextView text2 = (TextView) root.findViewById(R.id.descriptionText);

        Glide.with(imageView.getContext()).load(product.getImg()).into(imageView);
        text1.setText(product.getName());
        text2.setText(product.getDescription());

        return root;
    }

    public void makeOffer(){
        Fragment fragment = null;
        fragment = OfferFragment.newInstance(id, product);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.flContent, fragment, "NewOffer");
        ft.addToBackStack(null);
        ft.commit();
    }

}
