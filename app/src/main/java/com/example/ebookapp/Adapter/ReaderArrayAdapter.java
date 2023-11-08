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
import com.example.ebookapp.Model.Reader;

import java.util.ArrayList;
import java.util.List;

public class ReaderArrayAdapter extends ArrayAdapter<Reader> {

    List<Reader> lstReader;
    List<Reader> lstReaderFiller;
    int resourceId;
    private LayoutInflater inflater;

    public ReaderArrayAdapter(@NonNull Context context, int resource, @NonNull List<Reader> objects) {
        super(context, resource, objects);
        this.lstReader = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resourceId = resource;
    }

    @Override
    public Reader getItem(int position) {
        return lstReader.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lstReader.get(position).getReaderId();
    }

    @Override
    public int getCount() {
        return lstReader.size();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(resourceId, null);
        }
        Reader reader = (Reader) getItem(i);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(reader.getName());
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
            lstReaderFiller = new ArrayList<>();
            lstReaderFiller.addAll(lstReader);
            results.values = lstReaderFiller;
            results.count = lstReaderFiller.size();

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
            return ((Reader) resultValue).getName();
        }
    };

}
