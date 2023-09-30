package com.example.ebookapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.ebookapp.Adapter.AuthorAdapter;
import com.example.ebookapp.DatabaseHandler.AuthorHandler;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.R;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    ListView listViewItem;
    AutoCompleteTextView searchEdit;
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

    }

    private void init()
    {
        listViewItem = findViewById(R.id.listviewItem);
        searchEdit = findViewById(R.id.search_edit);
        showitem = findViewById(R.id.showitem);


        db = new AuthorHandler(ItemListActivity.this);
        arrAuthor = db.getall();
        authorAdapter = new AuthorAdapter(arrAuthor);
        listViewItem.setAdapter(authorAdapter);
    }

    private void LoadListView()
    {
        arrAuthor.clear();
        arrAuthor = db.getall();
        authorAdapter.notifyDataSetChanged();
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
                        Toast.makeText(ItemListActivity.this, "create", Toast.LENGTH_LONG).show();
                        return true;
//                    case R.id.showallitem:
//                        return true;
//                    case R.id.backitem:
//                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popup.show();

    }
}