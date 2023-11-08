package com.example.ebookapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ebookapp.Model.Author;
import com.example.ebookapp.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryArrayAdapter extends ArrayAdapter {
    List<Category> lstCategory;
    List<Category> lstCategoryFiller;
    int resourceId;
    private LayoutInflater inflater;
    public CategoryArrayAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.lstCategory = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resourceId = resource;
    }
    @Override
    public Category getItem(int position) {
        return lstCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lstCategory.get(position).getId();
    }

    @Override
    public int getCount() {
        return lstCategory.size();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(resourceId, null);
        }
        Category category = (Category) getItem(i);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(category.getName());
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return placeFilter;
    }

    Filter placeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            lstCategoryFiller = new ArrayList<>();
            lstCategoryFiller.addAll(lstCategory);
            results.values = lstCategoryFiller;
            results.count = lstCategoryFiller.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Category) resultValue).getName();
        }
    };
}
