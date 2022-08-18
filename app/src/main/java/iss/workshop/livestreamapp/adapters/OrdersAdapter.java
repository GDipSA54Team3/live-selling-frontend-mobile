package iss.workshop.livestreamapp.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

import com.google.android.material.chip.Chip;

import java.util.List;
import java.util.Objects;

import iss.workshop.livestreamapp.MyPurchasesActivity;
import iss.workshop.livestreamapp.OrdersActivity;
import iss.workshop.livestreamapp.R;
import iss.workshop.livestreamapp.models.ChannelStream;
import iss.workshop.livestreamapp.models.Message;
import iss.workshop.livestreamapp.models.Orders;
import iss.workshop.livestreamapp.models.Product;
import iss.workshop.livestreamapp.models.Stream;
import iss.workshop.livestreamapp.models.User;
import iss.workshop.livestreamapp.services.OrdersApi;
import iss.workshop.livestreamapp.services.RetroFitService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersAdapter extends BaseAdapter {

    private Context context;
    private List<Orders> orders;
    ListView orders_listview;
    private User user;
    private ChannelStream channel;
    private ActivityResultLauncher<Intent> rlRefresh;


    public OrdersAdapter(Context context, List<Orders> orders, User user, ChannelStream channel,ActivityResultLauncher<Intent> rlRefresh ){
        this.context = context;
        this.orders = orders;
        this.user = user;
        this.channel = channel;
        this.rlRefresh = rlRefresh;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return orders.get(i);
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
            public void onClick(View v) {
                RetroFitService rfServ = new RetroFitService("order-status");
                OrdersApi ordersApi = rfServ.getRetrofit().create(OrdersApi.class);
                ordersApi.updateOrderStatus(currOrder.getId(), currOrder.getOrderStatus().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){

                            Intent intent = new Intent(context, OrdersActivity.class);
                            intent.putExtra("user", user);
                            intent.putExtra("channel", channel);
                            rlRefresh.launch(intent);
                            Toast.makeText(context, "Order Confirmed", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(context,t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        Button btnReject = view.findViewById(R.id.btn_order_reject);
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetroFitService rfServ = new RetroFitService("order-status");
                OrdersApi ordersApi = rfServ.getRetrofit().create(OrdersApi.class);
                ordersApi.updateOrderStatus(currOrder.getId(), currOrder.getOrderStatus().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){

                            Intent intent = new Intent(context, OrdersActivity.class);
                            intent.putExtra("user", user);
                            intent.putExtra("channel", channel);
                            rlRefresh.launch(intent);
                            Toast.makeText(context, "Order Rejected", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(context,t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }

}