package com.example.ebookapp.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.ebookapp.Adapter.AuthorAdapter;
import com.example.ebookapp.DatabaseHandler.AuthorHandler;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.R;

import java.util.ArrayList;

public class Author_List_Activity extends AppCompatActivity {

    public static final int REQ_INSERT_AUTHOR = 1001;
    public static final int RES_INSERT_AUTHOR = 1002;

    SearchView searchView;
    ListView listViewItem;
    AuthorAdapter authorAdapter;
    ArrayList<Author> arrAuthor;
    AuthorHandler db;
    ImageView showitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        init();

        showitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(Author_List_Activity.this, Edit_Author_Activity.class);
                Bundle author = new Bundle();
                author.putSerializable("Author", (Author)authorAdapter.getItem(i));
                intent.putExtra("Author", author);
                intent.putExtra("isUpdate", true);
                startActivityForResult(intent, REQ_INSERT_AUTHOR);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(TextUtils.isEmpty(query))
                {
                    authorAdapter.filter("");
                }
                else {
                    authorAdapter.filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText))
                {
                    authorAdapter.filter("");
                }
                else {
                    authorAdapter.filter(newText);
                }
                return false;
            }
        });
    }

    private void init()
    {
        listViewItem = findViewById(R.id.listviewItem);
        searchView = findViewById(R.id.search_Author);
        showitem = findViewById(R.id.showitem);

        db = new AuthorHandler(Author_List_Activity.this);
        arrAuthor = db.getall();
        authorAdapter = new AuthorAdapter(arrAuthor);
        listViewItem.setAdapter(authorAdapter);
    }

    private void LoadListView()
    {
        arrAuthor.clear();
        arrAuthor.addAll(db.getall());
        authorAdapter.loadingData();

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
                        intent.setClass(Author_List_Activity.this, Edit_Author_Activity.class);
                        startActivityForResult(intent, REQ_INSERT_AUTHOR);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQ_INSERT_AUTHOR:
                if(resultCode == RES_INSERT_AUTHOR)
                {
                    LoadListView();
                }
                break;
        }
    }
}