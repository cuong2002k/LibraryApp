package com.example.ebookapp.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.ebookapp.Adapter.BookAdapter;
import com.example.ebookapp.Adapter.BorrowingAdapter;
import com.example.ebookapp.DatabaseHandler.BookHandler;
import com.example.ebookapp.DatabaseHandler.BorrowingHandler;
import com.example.ebookapp.Model.Book;
import com.example.ebookapp.Model.Borrowing;
import com.example.ebookapp.R;

import java.util.ArrayList;

public class Borrowing_List_Activity extends AppCompatActivity {

    public static final int REQ_INSERT = 1005;
    public static final int RES_INSERT = 1005;
    SearchView searchView;
    ListView listViewItem;
    BorrowingAdapter Adapter;
    ArrayList<Borrowing> arr;
    BorrowingHandler db;
    ImageView showItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowing_list);


        db = new BorrowingHandler(Borrowing_List_Activity.this);
        doShowOption();
        fillData();
        doSearchView();
    }
    private void fillData()
    {
        listViewItem = findViewById(R.id.listviewItem);
        arr = new ArrayList<>();
        arr.addAll(db.getAll());
        Adapter = new BorrowingAdapter( arr , Borrowing_List_Activity.this);


        listViewItem.setAdapter(Adapter);
        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(Borrowing_List_Activity.this, Borrowing_Edit_Activity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("Borrow", arr.get(i));
                intent.putExtra("Borrow", bundle);
                intent.putExtra("isUpdate", true);
                startActivityForResult(intent, REQ_INSERT);

            }
        });

    }
    private void LoadListView()
    {
        arr.clear();
        arr.addAll(db.getAll());
        Adapter.loadingData();

    }

    private void doShowOption()
    {
        showItem = findViewById(R.id.showitem);
        showItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.option_item, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id)
                {
                    case R.id.createitem:
                        Intent intent = new Intent();
                        intent.setClass(Borrowing_List_Activity.this, Borrowing_Edit_Activity.class);
                        startActivityForResult(intent, REQ_INSERT);
                        return true;
                    case R.id.showallitem:
                        LoadListView();
                        return true;
                    case R.id.backitem:
                        finish();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    private void doSearchView()
    {
        searchView = findViewById(R.id.search_item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Adapter.filter(newText);
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQ_INSERT:
                if(resultCode == RES_INSERT)
                {
                    LoadListView();
                }
                break;
        }
    }
}