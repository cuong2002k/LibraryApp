package com.example.ebookapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ebookapp.DatabaseHandler.AuthorHandler;
import com.example.ebookapp.DatabaseHandler.CategoryHandler;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.Model.Book;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.Model.Reader;
import com.example.ebookapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class BookAdapter extends BaseAdapter {
    ArrayList<Book> arrBook;
    ArrayList<Book> lstBook;

    AuthorHandler authorHandler;
    CategoryHandler categoryHandler;
    public BookAdapter(ArrayList<Book> arrBook, Context context) {
        this.arrBook = arrBook;
        this.lstBook = new ArrayList<>();
        lstBook.addAll(arrBook);
        authorHandler = new AuthorHandler(context);
        categoryHandler = new CategoryHandler(context);
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
        Bitmap image = book.getImage();
        if(image != null)
        {
            Bitmap Resize =  Bitmap.createScaledBitmap(image, 100, 120, false);
            ((ImageView) viewProduct.findViewById(R.id.imageBook)).setImageBitmap(Resize);
        }
        ((TextView) viewProduct.findViewById(R.id.nameBook)).setText("Tiêu đề: " + book.getTitle() + "");
        Author author = authorHandler.getAuthorWithID(book.getAuthor());
        if(author != null)
        {
            ((TextView) viewProduct.findViewById(R.id.authorBook)).setText("Tác giả: " + author.getName() + "");
        }
        Category category = categoryHandler.getCategoryWithID(book.getCategory());
        if(category != null)
        {
            ((TextView) viewProduct.findViewById(R.id.CategoryBook)).setText("Thể loại: " + category.getName() + "");

        }
        return viewProduct;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        lstBook.clear();
        if (charText.length() == 0 || TextUtils.isEmpty(charText)) {
            lstBook.addAll(arrBook);
        } else {
            for (Book book : arrBook) {
                Author author = authorHandler.getAuthorWithID(book.getAuthor());
                Category category = categoryHandler.getCategoryWithID(book.getCategory());
                if (book.getTitle().toLowerCase(Locale.getDefault()).contains(charText)
                        || author.getName().toLowerCase(Locale.getDefault()).contains(charText)
                        || category.getName().toLowerCase(Locale.getDefault()).contains(charText)
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
