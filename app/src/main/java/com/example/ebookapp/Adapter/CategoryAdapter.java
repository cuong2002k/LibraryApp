package com.example.ebookapp.Adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ebookapp.Model.Category;
import com.example.ebookapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<Category> arrCategory;
    ArrayList<Category> lstCategory;
    public CategoryAdapter(ArrayList<Category> categories)
    {
        this.arrCategory = categories;
        lstCategory = new ArrayList<>();
        lstCategory.addAll(arrCategory);
    }

    @Override
    public int getCount() {
        return lstCategory.size();
    }

    @Override
    public Object getItem(int i) {
        return lstCategory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lstCategory.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewProduct;
        if (view == null) {
            viewProduct = View.inflate(viewGroup.getContext(), R.layout.author_item_layout, null);
        } else viewProduct = view;
        Category category = (Category) getItem(i);
        ((TextView) viewProduct.findViewById(R.id.txtName)).setText("Tên thể loại :" + category.getName() + "");
        return viewProduct;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        lstCategory.clear();
        if (charText.length() == 0 || TextUtils.isEmpty(charText)) {
            lstCategory.addAll(arrCategory);
        } else {
            for (Category category : arrCategory) {
                if (category.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    lstCategory.add(category);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void loadingData()
    {
        this.lstCategory.clear();
        this.lstCategory.addAll(arrCategory);
        notifyDataSetChanged();
    }
}
