package iss.workshop.livestreamapp.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import iss.workshop.livestreamapp.MyPurchasesActivity;
import iss.workshop.livestreamapp.R;
import iss.workshop.livestreamapp.models.OrderProduct;
import iss.workshop.livestreamapp.models.Orders;
import iss.workshop.livestreamapp.models.Product;
import iss.workshop.livestreamapp.services.ProductsApi;
import iss.workshop.livestreamapp.services.RetroFitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseAdapter extends BaseAdapter {

    private Context context;

    private List<Orders> purchases;
    private List<Product> products;
    private Dialog dialog;
    private ListView orderProductListView;
    private Orders orders;


    public PurchaseAdapter(Context context, List<Orders> purchases,Dialog dialog){ //add dialog
        this.context = context;
        this.purchases = purchases;
        this.dialog = dialog;
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
        orders = purchases.get(i);

        //getChannelName
        TextView profileName = view.findViewById(R.id.profile_Name);
        profileName.setText(orders.getChannel().getName());

        //orderStatus
        TextView orderStatus = view.findViewById(R.id.order_status);
        orderStatus.setText(orders.getOrderStatus().name());
        Button cancelOrder = view.findViewById(R.id.cancel_order);

        //setting up dialog
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.orderproduct_details_listview);


        Button mOrderDetails = view.findViewById(R.id.viewOrderDetails);

        mOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetroFitService rfServ = new RetroFitService("order-product");
                ProductsApi productAPI = rfServ.getRetrofit().create(ProductsApi.class);

                productAPI.getProductsInOrder(orders.getId()).enqueue(new Callback<List<OrderProduct>>() {
                    @Override
                    public void onResponse(Call<List<OrderProduct>> call, Response<List<OrderProduct>> response) {
                        if (response.code() == 200) {
                            populateDialogList(response.body());
                            Toast.makeText(context, response.body().size() +
                                    " Orders_Product found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<OrderProduct>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(context,t.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                openProductDialog();
            }
        });
        return view;
    }

    private void openProductDialog(){
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (context.getResources().getDisplayMetrics().heightPixels*0.60));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void populateDialogList(List<OrderProduct> body){
        orderProductListView = dialog.findViewById(R.id.order_product_list);
        OrderProductAdapter orderProductAdapter = new OrderProductAdapter(context,body);
        orderProductListView.setAdapter(orderProductAdapter);
    }
}
