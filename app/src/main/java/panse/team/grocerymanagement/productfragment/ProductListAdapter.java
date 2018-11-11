package panse.team.grocerymanagement.productfragment;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import panse.team.grocerymanagement.R;

import java.util.ArrayList;


import panse.team.grocerymanagement.entities.Product;

public class ProductListAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private ArrayList<Product> products;

    public ProductListAdapter(@NonNull FragmentActivity context, int resource, @NonNull ArrayList<Product> products) {
        super(context, resource, products);
        this.context = context;
        this.resource = resource;
        this.products = products;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_product_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvProductId = convertView.findViewById(R.id.tvProductId);
            viewHolder.tvProductName = convertView.findViewById(R.id.tvProductName);
            viewHolder.tvProductQty = convertView.findViewById(R.id.tvProductQty);
            viewHolder.tvProductPrice = convertView.findViewById(R.id.tvProductPrice);
            viewHolder.tvProductInfor = convertView.findViewById(R.id.tvProductInfor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = products.get(position);
        viewHolder.tvProductId.setText(product.getProductId() + "");
        viewHolder.tvProductName.setText(Product.getProductFirstName(product.getProductName()));
        viewHolder.tvProductQty.setText(product.getProductQty() + "");
        viewHolder.tvProductPrice.setText(product.getProductPrice() + "");
        viewHolder.tvProductInfor.setText(product.getInformation());
        return convertView;
    }

    private class ViewHolder {
        TextView tvProductId, tvProductName, tvProductQty, tvProductPrice, tvProductInfor;
    }
}
