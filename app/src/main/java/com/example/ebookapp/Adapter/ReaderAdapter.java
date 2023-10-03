package com.example.ebookapp.Adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ebookapp.Model.Category;
import com.example.ebookapp.Model.Reader;
import com.example.ebookapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class ReaderAdapter extends BaseAdapter {

    ArrayList<Reader> arrReader;
    ArrayList<Reader> lstReader;

    public ReaderAdapter(ArrayList<Reader> arrLstReader)
    {
        this.arrReader = arrLstReader;
        lstReader = new ArrayList<>();
        lstReader.addAll(arrReader);
    }

    @Override
    public int getCount() {
        return lstReader.size();
    }

    @Override
    public Object getItem(int i) {
        return lstReader.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lstReader.get(i).getReaderId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewProduct;
        if (view == null) {
            viewProduct = View.inflate(viewGroup.getContext(), R.layout.reader_item, null);
        } else viewProduct = view;
        Reader reader = (Reader) getItem(i);
        ((TextView) viewProduct.findViewById(R.id.idReader)).setText(reader.getReaderId() + "");
        ((TextView) viewProduct.findViewById(R.id.nameReader)).setText(reader.getName() + "");
        ((TextView) viewProduct.findViewById(R.id.phoneReader)).setText(reader.getPhone() + "");
        return viewProduct;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        lstReader.clear();
        if (charText.length() == 0 || TextUtils.isEmpty(charText)) {
            lstReader.addAll(arrReader);
        } else {
            for (Reader reader : arrReader) {
                if (reader.getName().toLowerCase(Locale.getDefault()).contains(charText)
                    || reader.getPhone().toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    lstReader.add(reader);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void loadingData()
    {
        this.lstReader.clear();
        this.lstReader.addAll(arrReader);
        notifyDataSetChanged();
    }
}
