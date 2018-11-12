package panse.team.grocerymanagement.orderfragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import panse.team.grocerymanagement.R;

import panse.team.grocerymanagement.entities.Order;

public class OrderListAdapter extends ArrayAdapter<Order> {
    private Context context;
    private int resource;
    private ArrayList<Order> orders;

    public OrderListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Order> orders) {
        super(context, resource, orders);
        this.context = context;
        this.resource = resource;
        this.orders = orders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_order_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvOrderId = convertView.findViewById(R.id.tvOrderId);
            viewHolder.tvCustomerName = convertView.findViewById(R.id.tvCustomerName);
            viewHolder.tvOrderDate = convertView.findViewById(R.id.tvOrderDate);
            viewHolder.tvTotalOrderPrice = convertView.findViewById(R.id.tvTotalOrderPrice);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Order order = orders.get(position);
        viewHolder.tvOrderId.setText(order.getOrderId());
        viewHolder.tvCustomerName.setText(Order.getOrderCusFirstName(order.getCustomerName()));
        viewHolder.tvOrderDate.setText(order.getOrderDate());
        viewHolder.tvTotalOrderPrice.setText((int) order.getTotalOrderPrice() + "");
        return convertView;
    }

    private class ViewHolder {
        TextView tvOrderId, tvCustomerName, tvOrderDate, tvTotalOrderPrice;
    }
}
