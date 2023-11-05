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

import com.example.ebookapp.Adapter.CategoryAdapter;
import com.example.ebookapp.CODE;
import com.example.ebookapp.DatabaseHandler.CategoryHandler;
import com.example.ebookapp.Model.Category;
import com.example.ebookapp.R;

import java.util.ArrayList;

public class Category_List_Activity extends AppCompatActivity {


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
        arrCategory = db.getAll();
        categoryAdapter = new CategoryAdapter(arrCategory);
        listViewItem.setAdapter(categoryAdapter);

        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(Category_List_Activity.this, Category_Edit_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Category", (Category)categoryAdapter.getItem(i));
                intent.putExtra("Category", bundle);
                intent.putExtra("isUpdate", true);
                startActivityForResult(intent, CODE.REQ);
            }
        });
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
        searchView = findViewById(R.id.search_item);
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
                        Intent intent = new Intent();
                        intent.setClass(Category_List_Activity.this, Category_Edit_Activity.class);
                        startActivityForResult(intent, CODE.REQ);
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
            case CODE.REQ:
                if(resultCode == CODE.RES)
                {
                    //Toast.makeText(Category_List_Activity.this, "oke", Toast.LENGTH_LONG).show();
                    LoadListView();
                }
                break;
        }
    }
}