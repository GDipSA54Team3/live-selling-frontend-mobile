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

public class PurchaseAdapter extends BaseAdapter {

    private Context context;
    private List<Orders> purchases;

    public PurchaseAdapter(Context context, List<Orders> purchases){
        this.context = context;
        this.purchases = purchases;
    }
    @Override
    public int getCount() {
        return purchases.size();
    }

    @Override
    public Object getItem(int i) {
        return purchases.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.purchases_row, viewGroup, false);
        }
        Orders orders = purchases.get(i);

        TextView orderId = view.findViewById(R.id.o_id);
        orderId.setText("1234");

        TextView orderName = view.findViewById(R.id.o_name);
        orderName.setText("888");

        TextView orderDesc = view.findViewById(R.id.o_desc);
        orderDesc.setText("88888");
        return view;
    }
}
