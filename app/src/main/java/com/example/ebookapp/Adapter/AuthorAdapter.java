package com.example.ebookapp.Adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ebookapp.Model.Author;
import com.example.ebookapp.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Locale;

public class AuthorAdapter extends BaseAdapter {
    ArrayList<Author> lstAuthor;
    ArrayList<Author> arrAuthor;

    public AuthorAdapter(ArrayList<Author> lstAuthor) {
        this.arrAuthor = lstAuthor;
        this.lstAuthor = new ArrayList<>();
        this.lstAuthor.addAll(arrAuthor);
    }

    @Override
    public int getCount() {
        return lstAuthor.size();
    }

    @Override
    public Object getItem(int i) {
        return lstAuthor.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lstAuthor.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewProduct;
        if (view == null) {
            viewProduct = View.inflate(viewGroup.getContext(), R.layout.author_item_layout, null);
        } else viewProduct = view;
        Author author = (Author) getItem(i);
        ((TextView) viewProduct.findViewById(R.id.txtAuthorID)).setText(author.getId() + "");
        ((TextView) viewProduct.findViewById(R.id.txtAuthorName)).setText(author.getName() + "");
        return viewProduct;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        lstAuthor.clear();
        if (charText.length() == 0 || TextUtils.isEmpty(charText)) {
            lstAuthor.addAll(arrAuthor);
        } else {
            for (Author author : arrAuthor) {
                if (author.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    lstAuthor.add(author);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void loadingData()
    {
        lstAuthor.clear();
        this.lstAuthor.addAll(arrAuthor);
    }
}
