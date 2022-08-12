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
import iss.workshop.livestreamapp.models.Orders;
import iss.workshop.livestreamapp.models.Product;

public class OrdersAdapter extends BaseAdapter {

    private Context context;
    private List<Orders> orders;

    public OrdersAdapter(Context context, List<Orders> orders){
        this.context = context;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.orders_row, viewGroup, false);
        }

        //Orders currOrder = orders.get(i);

        TextView txtId = view.findViewById(R.id.order_id);
        txtId.setText(orders.get(i).getId());

        TextView txtUser = view.findViewById(R.id.user_name);
        txtId.setText(orders.get(i).getUser().getUsername());

        return view;
    }
}
