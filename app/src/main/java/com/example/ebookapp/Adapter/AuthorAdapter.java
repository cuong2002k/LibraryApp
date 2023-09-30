package com.example.ebookapp.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ebookapp.Model.Author;
import com.example.ebookapp.R;

import java.sql.Array;
import java.util.ArrayList;

public class AuthorAdapter extends BaseAdapter {
    ArrayList<Author> lstAuthor;
    public AuthorAdapter(ArrayList<Author> lstAuthor) {
        this.lstAuthor = lstAuthor;
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
        ((TextView) viewProduct.findViewById(R.id.txtAuthorID)).setText(author.getId()+"");
        ((TextView) viewProduct.findViewById(R.id.txtAuthorName)).setText(author.getName()+"");
        return viewProduct;
    }
}
