package com.example.ebookapp.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.ebookapp.Adapter.AuthorAdapter;
import com.example.ebookapp.Adapter.CategoryAdapter;
import com.example.ebookapp.DatabaseHandler.AuthorHandler;
import com.example.ebookapp.DatabaseHandler.CategoryHandler;
import com.example.ebookapp.Model.Author;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.R;

import java.util.ArrayList;

public class Category_List_Activity extends AppCompatActivity {

    public static final int REQ_INSERT_CATEGORY = 1003;
    public static final int RES_INSERT_CATEGORY = 1004;

    SearchView searchView;
    ListView listViewItem;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> arrCategory;
    CategoryHandler db;
    ImageView showItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        init();
        doShowOption();
        doSearchView();
    }

    public void init()
    {
        listViewItem = findViewById(R.id.listviewItem);
        db = new CategoryHandler(Category_List_Activity.this);
        fillDataToListview();
    }

    public void fillDataToListview()
    {
        db.insertData(new Category("Action"));
        arrCategory = db.getAll();
        categoryAdapter = new CategoryAdapter(arrCategory);
        listViewItem.setAdapter(categoryAdapter);
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

    private void doSearchView()
    {
        searchView = findViewById(R.id.search_Author);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                categoryAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                categoryAdapter.filter(newText);
                return false;
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

                        return true;
                    case R.id.showallitem:

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

    private void LoadListView()
    {
        arrCategory.clear();
        arrCategory.addAll(db.getAll());
        categoryAdapter.loadingData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQ_INSERT_CATEGORY:
                if(resultCode == RES_INSERT_CATEGORY)
                {
                    LoadListView();
                }
                break;
        }
    }
}