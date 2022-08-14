package iss.workshop.livestreamapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import iss.workshop.livestreamapp.R;
import iss.workshop.livestreamapp.models.Product;

public class MyProductsAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;

    public MyProductsAdapter(Context context, List<Product> products){
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.product_row, //change to the row before testing
                    viewGroup, false);
        }

        Product product= products.get(i);

        TextView txtName = view.findViewById(R.id.product_name);
        txtName.setText(product.getName());

        TextView txtDesc = view.findViewById(R.id.product_description);
        txtDesc.setText(product.getDescription());

        TextView txtPrice = view.findViewById(R.id.product_price);
        txtPrice.setText(Double.toString(product.getPrice()));
        //name, price, SKU, buttons (update/delete)

        return view;
    }
}
