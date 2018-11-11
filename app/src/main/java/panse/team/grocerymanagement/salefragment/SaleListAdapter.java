package panse.team.grocerymanagement.salefragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import panse.team.grocerymanagement.R;
import panse.team.grocerymanagement.entities.Product;

public class SaleListAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private ArrayList<Product> products;
    public SaleListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> products) {
        super(context, resource, products);
        this.context = context;
        this.resource = resource;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_product_orderdetail_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvProName = convertView.findViewById(R.id.tvProName);
            viewHolder.tvProQty = convertView.findViewById(R.id.tvProQty);
            viewHolder.tvProPrice = convertView.findViewById(R.id.tvTotalPrice);
            viewHolder.tvTotalPrice = convertView.findViewById(R.id.tvTotalPrice);
            viewHolder.imgBtnClear = convertView.findViewById(R.id.imgBtnClear);
            viewHolder.imgBtnDes = convertView.findViewById(R.id.imgBtnDes);
            viewHolder.imgBtnIns = convertView.findViewById(R.id.imgBtnIns);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = products.get(position);
        viewHolder.tvProName.setText(product.getProductName());
        viewHolder.tvProQty.setText(product.getProductQty() + "");
        viewHolder.tvProPrice.setText(product.getProductPrice()+"");
        double total = product.getProductQty() * product.getProductPrice();
        viewHolder.tvTotalPrice.setText((int)total+"");
        return convertView;
    }

    private class ViewHolder {
        TextView tvProName,tvProQty,tvProPrice,tvTotalPrice;
        ImageButton imgBtnDes,imgBtnIns,imgBtnClear;
    }
}
