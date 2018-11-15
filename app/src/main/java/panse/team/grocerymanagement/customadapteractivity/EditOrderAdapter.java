package panse.team.grocerymanagement.customadapteractivity;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import panse.team.grocerymanagement.EditOrdersActivity;
import panse.team.grocerymanagement.R;
import panse.team.grocerymanagement.entities.Product;


public class EditOrderAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private ArrayList<Product> products;

    public EditOrderAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> products) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_product_orderdetail_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvProName = convertView.findViewById(R.id.tvProName);
            viewHolder.tvProQty = convertView.findViewById(R.id.tvProQty);
            viewHolder.tvProPrice = convertView.findViewById(R.id.tvProPrice);
            viewHolder.tvTotalPrice = convertView.findViewById(R.id.tvTotalPrice);
            viewHolder.imgBtnClear = convertView.findViewById(R.id.imgBtnClear);
            viewHolder.imgBtnDes = convertView.findViewById(R.id.imgBtnDes);
            viewHolder.imgBtnIns = convertView.findViewById(R.id.imgBtnIns);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Product product = products.get(position);
        viewHolder.tvProName.setText(product.getProductName());
        viewHolder.tvProQty.setText(product.getProductQty() + "");
        viewHolder.tvProPrice.setText((int) product.getProductPrice() + "");
        double total = product.getProductQty() * product.getProductPrice();
        viewHolder.tvTotalPrice.setText((int) total + "");
        viewHolder.imgBtnIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double total = Double.parseDouble(EditOrdersActivity.tvTotalOrderPrice.getText().toString());
                EditOrdersActivity.tvTotalOrderPrice.setText(String.valueOf((int) product.getProductPrice() + (int) total));
                product.setProductQty(product.getProductQty() + 1);
                viewHolder.tvProQty.setText(String.valueOf(product.getProductQty()));
                viewHolder.tvTotalPrice.setText(String.valueOf(product.getProductQty() * (int) product.getProductPrice()));
                if (!EditOrdersActivity.editDetail.contains(product)) {
                    EditOrdersActivity.editDetail.add(product);
                }
            }
        });
        viewHolder.imgBtnDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getProductQty() <= 1) {
                    Toast.makeText(context, "Số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
                } else {
                    double total = Double.parseDouble(EditOrdersActivity.tvTotalOrderPrice.getText().toString());
                    EditOrdersActivity.tvTotalOrderPrice.setText(String.valueOf((int) total - (int) product.getProductPrice()));
                    product.setProductQty(product.getProductQty() - 1);
                    viewHolder.tvProQty.setText(String.valueOf(product.getProductQty()));
                    viewHolder.tvTotalPrice.setText(String.valueOf(product.getProductQty() * (int) product.getProductPrice()));
                    if (!EditOrdersActivity.editDetail.contains(product)) {
                        EditOrdersActivity.editDetail.add(product);
                    }
                }
            }
        });
        final View finalConvertView = convertView;
        viewHolder.imgBtnClear.setOnClickListener(new View.OnClickListener() {
            int isClick = 0;
            @Override
            public void onClick(View v) {
                if (isClick == 0) {
                    double total = Double.parseDouble(EditOrdersActivity.tvTotalOrderPrice.getText().toString());
                    EditOrdersActivity.tvTotalOrderPrice.setText(String.valueOf((int) total - (int) product.getProductPrice() * product.getProductQty()));
                    finalConvertView.setBackgroundColor(Color.GRAY);
                    viewHolder.imgBtnDes.setEnabled(false);
                    viewHolder.imgBtnIns.setEnabled(false);
                    isClick=1;
                    EditOrdersActivity.deleteDetail.add(products.get(position));
                } else {
                    double total = Double.parseDouble(EditOrdersActivity.tvTotalOrderPrice.getText().toString());
                    EditOrdersActivity.tvTotalOrderPrice.setText(String.valueOf((int) total + (int) product.getProductPrice() * product.getProductQty()));
                    finalConvertView.setBackgroundColor(Color.TRANSPARENT);
                    viewHolder.imgBtnDes.setEnabled(true);
                    viewHolder.imgBtnIns.setEnabled(true);
                    isClick=0;
                    EditOrdersActivity.deleteDetail.remove(products.get(position));
                }
//                products.remove(position);
//                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView tvProName, tvProQty, tvProPrice, tvTotalPrice;
        ImageButton imgBtnDes, imgBtnIns, imgBtnClear;
    }
}

