package com.example.ebookapp.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ebookapp.DatabaseHandler.AuthorHandler;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.Model.Book;
import com.example.ebookapp.Model.Reader;
import com.example.ebookapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class BookAdapter extends BaseAdapter {
    ArrayList<Book> arrBook;
    ArrayList<Book> lstBook;

    AuthorHandler authorHandler;
    public BookAdapter(ArrayList<Book> arrBook, Context context) {
        this.arrBook = arrBook;
        this.lstBook = new ArrayList<>();
        lstBook.addAll(arrBook);
        authorHandler = new AuthorHandler(context);
    }

    @Override
    public int getCount() {
        return lstBook.size();
    }

    @Override
    public Object getItem(int i) {
        return lstBook.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lstBook.get(i).getBookId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewProduct;
        if (view == null) {
            viewProduct = View.inflate(viewGroup.getContext(), R.layout.book_item, null);
        } else viewProduct = view;
        Book book = (Book) getItem(i);
        ((ImageView) viewProduct.findViewById(R.id.imageBook)).setImageBitmap(book.getImage());
        ((TextView) viewProduct.findViewById(R.id.nameBook)).setText("Title: " + book.getTitle() + "");
        Author author = authorHandler.getAuthorWithID(book.getAuthor());
        ((TextView) viewProduct.findViewById(R.id.authorBook)).setText("Author: " + author.getName() + "");
        return viewProduct;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        lstBook.clear();
        if (charText.length() == 0 || TextUtils.isEmpty(charText)) {
            lstBook.addAll(arrBook);
        } else {
            for (Book book : lstBook) {
                Author author = authorHandler.getAuthorWithID(book.getAuthor());
                if (book.getTitle().toLowerCase(Locale.getDefault()).contains(charText)
                        || author.getName().toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    lstBook.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void loadingData()
    {
        this.lstBook.clear();
        this.lstBook.addAll(arrBook);
        notifyDataSetChanged();
    }
}
