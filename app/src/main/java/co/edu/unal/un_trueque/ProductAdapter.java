package co.edu.unal.un_trueque;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.edu.unal.un_trueque.R;
import co.edu.unal.un_trueque.objects.Product;

/**
 * Created by Jonathan on 9/22/2017.
 */

public class ProductAdapter extends BaseAdapter{

    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products){
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getName().hashCode();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item_product, parent, false);
        }

        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        TextView name = (TextView) view.findViewById(R.id.nameTextView);
        TextView type = (TextView) view.findViewById(R.id.typeTextView);

        Product product = getItem(position);
        Glide.with(image.getContext()).load(product.getImg()).into(image);
        name.setText(product.getName());
        type.setText(product.getType());

        return view;
    }
}
