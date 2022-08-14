package iss.workshop.livestreamapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import iss.workshop.livestreamapp.R;
import iss.workshop.livestreamapp.models.Orders;
import iss.workshop.livestreamapp.models.Product;

public class PurchaseAdapter extends BaseAdapter {

    private Context context;
    private List<Orders> purchases;
    private List<Product> products;

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

        //getUser
        TextView userName = view.findViewById(R.id.profile_Image);
        userName.setText(orders.getUser().getId());

        TextView totalPurchase = view.findViewById(R.id.total_Purchase);

        //getChannelName
        TextView profileName = view.findViewById(R.id.profile_Name);
        profileName.setText(orders.getChannel().getName());

        TextView productName = view.findViewById(R.id.product_Name);


        //TextView productQty = view.findViewById(R.id.product_Qty);
        TextView totalPrice = view.findViewById(R.id.total_Price);

        //orderStatus
        TextView orderStatus = view.findViewById(R.id.order_status);
        orderStatus.setText(orders.getOrderStatus().name());

        Button cancelOrder = view.findViewById(R.id.cancel_order);

        return view;
    }
}
