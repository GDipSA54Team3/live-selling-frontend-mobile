package iss.workshop.livestreamapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

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

        Orders currOrder = orders.get(i);

        TextView txtId = view.findViewById(R.id.order_id);
        txtId.setText(orders.get(i).getId());

        TextView txtTime = view.findViewById(R.id.order_time);
        txtTime.setText(orders.get(i).getOrderDateTime().toString());

        TextView txtDes = view.findViewById(R.id.order_description);
        txtDes.setText(orders.get(i).getUser().getUsername());

        Chip orderStatus = view.findViewById(R.id.btn_order_status);
        orderStatus.setText(orders.get(i).getOrderStatus().toString());

        Button btnConfirm = view.findViewById(R.id.btn_order_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Orders orderToConfirm = orders.get(i);
                //code to confirm
            }
        });

        Button btnReject = view.findViewById(R.id.btn_order_reject);
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Orders orderToReject = orders.get(i);
                //code to reject
            }
        });


        return view;
    }
}
