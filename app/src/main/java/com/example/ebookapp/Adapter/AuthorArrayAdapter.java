package com.example.ebookapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ebookapp.Model.Author;
import com.example.ebookapp.R;

import java.util.ArrayList;
import java.util.List;

public class AuthorArrayAdapter extends ArrayAdapter<Author> {

    List<Author> lstAuthor;
    List<Author> lstAuthorFiller;
    int resourceId;
    private LayoutInflater inflater;
    public AuthorArrayAdapter(@NonNull Context context, int resource, List<Author> objects) {
        super(context, resource, objects);
        this.lstAuthor = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resourceId = resource;
    }

    @Override
    public Author getItem(int position) {
        return lstAuthor.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lstAuthor.get(position).getId();
    }

    @Override
    public int getCount() {
        return lstAuthor.size();
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(resourceId, null);
        }
        Author author = (Author) getItem(i);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(author.getName());
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
            lstAuthorFiller = new ArrayList<>();
            lstAuthorFiller.addAll(lstAuthor);
            results.values = lstAuthorFiller;
            results.count = lstAuthorFiller.size();

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
            return ((Author) resultValue).getName();
        }
    };
}
