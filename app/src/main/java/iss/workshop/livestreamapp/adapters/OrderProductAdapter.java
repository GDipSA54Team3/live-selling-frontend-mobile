package iss.workshop.livestreamapp.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import iss.workshop.livestreamapp.R;
import iss.workshop.livestreamapp.models.OrderProduct;
import iss.workshop.livestreamapp.models.Orders;

public class OrderProductAdapter extends BaseAdapter {

    private Context context;
    private List<OrderProduct> oProducts;
    //private List<Orders> purchases;
    //private Orders orders;

    public OrderProductAdapter(Context context, List<OrderProduct> orderProducts){
        this.context = context;
        this.oProducts = orderProducts;
    }
    @Override
    public int getCount() {
        return oProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return oProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_product_row, viewGroup, false);
        }
        OrderProduct orderProducts = oProducts.get(i);

        TextView productName = view.findViewById(R.id.product_Name);
        productName.setText(orderProducts.getProduct().getName());

        TextView productQty = view.findViewById(R.id.product_Qty);
        productQty.setText(Integer.toString(orderProducts.getProduct().getQuantity()));
        //txtQty.setText(Integer.toString(productQty.get(i)));

        TextView productDesc = view.findViewById(R.id.product_desc);
        productDesc.setText(orderProducts.getProduct().getDescription());

        TextView productPrice = view.findViewById(R.id.product_price);
        productPrice.setText(Double.toString(orderProducts.getProduct().getPrice()));

        return view;
    }
}
