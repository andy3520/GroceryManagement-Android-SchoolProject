package panse.team.grocerymanagement.customadapteractivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import panse.team.grocerymanagement.R;
import panse.team.grocerymanagement.entities.Product;

public class DetailOrderListAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private ArrayList<Product> products;

    public DetailOrderListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> products) {
        super(context, resource, products);
        this.context = context;
        this.resource = resource;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_detail_order_item_orderdetail, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvProName = convertView.findViewById(R.id.tvProName);
            viewHolder.tvProQty = convertView.findViewById(R.id.tvProQty);
            viewHolder.tvProPrice = convertView.findViewById(R.id.tvProPrice);
            viewHolder.tvTotalPrice = convertView.findViewById(R.id.tvTotalPrice);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = products.get(position);
        viewHolder.tvProName.setText(product.getProductName());
        viewHolder.tvProQty.setText(product.getProductQty() + "");
        viewHolder.tvProPrice.setText((int) product.getProductPrice() + "");
        double total = product.getProductQty() * product.getProductPrice();
        viewHolder.tvTotalPrice.setText((int) total + "");
        return convertView;
    }

    private class ViewHolder {
        TextView tvProName, tvProQty, tvProPrice, tvTotalPrice;
    }
}
