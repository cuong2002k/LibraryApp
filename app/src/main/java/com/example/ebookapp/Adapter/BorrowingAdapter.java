package com.example.ebookapp.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ebookapp.DatabaseHandler.ReaderHandler;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.Model.Book;
import com.example.ebookapp.Model.Borrowing;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.Model.Reader;
import com.example.ebookapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class BorrowingAdapter extends BaseAdapter {
    ArrayList<Borrowing> arrBorrowing;
    ArrayList<Borrowing> lstBorrowing;
    ReaderHandler readerHandler;

    public BorrowingAdapter(ArrayList<Borrowing> arrBorrowing, Context context) {
        this.arrBorrowing = arrBorrowing;
        lstBorrowing = new ArrayList<>();
        this.lstBorrowing.addAll(arrBorrowing);
        readerHandler = new ReaderHandler(context);
    }

    @Override
    public int getCount() {
        return lstBorrowing.size();
    }

    @Override
    public Object getItem(int i) {
        return lstBorrowing.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lstBorrowing.get(i).getBorrowingId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewProduct;
        if (view == null) {
            viewProduct = View.inflate(viewGroup.getContext(), R.layout.borrowing_item, null);
        } else viewProduct = view;

        Borrowing borrowing = (Borrowing) getItem(i);
        Reader reader = readerHandler.getReaderWithID(borrowing.getReaderId());

        ((TextView) viewProduct.findViewById(R.id.nameReader)).setText("Tên đọc giả : " + reader.getName() + "");
        ((TextView) viewProduct.findViewById(R.id.phoneReader)).setText("Điện thoại : " + reader.getPhone() + "");

        ((TextView) viewProduct.findViewById(R.id.borrowDay)).setText("Ngày mượn : " + borrowing.getBorrowDay() + "");
        ((TextView) viewProduct.findViewById(R.id.returnDay)).setText("Ngày trả : " + borrowing.getReturnDay() + "");
        ((TextView) viewProduct.findViewById(R.id.returnTime)).setText("Thời điểm trả : " + borrowing.getReturnTime() + "");

        return viewProduct;
    }

    public void loadingData()
    {
        this.lstBorrowing.clear();
        this.lstBorrowing.addAll(arrBorrowing);
        notifyDataSetChanged();
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        lstBorrowing.clear();
        if (charText.length() == 0 || TextUtils.isEmpty(charText)) {
            lstBorrowing.addAll(arrBorrowing);
        } else {
            for (Borrowing borrowing : arrBorrowing) {
                Reader reader = readerHandler.getReaderWithID(borrowing.getReaderId());

                if (reader.getName().toLowerCase(Locale.getDefault()).contains(charText)
                        || reader.getPhone().toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    lstBorrowing.add(borrowing);
                }
            }
        }
        notifyDataSetChanged();
    }
}
