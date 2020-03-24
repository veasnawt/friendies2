package com.example.friendies.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.friendies.R;
import com.example.friendies.adapter.CategoryAdapter;
import com.example.friendies.adapter.MostDownloadsItemAdapter;
import com.example.friendies.adapter.OtherItemAdapter;
import com.example.friendies.adapter.PopularItemAdapter;
import com.example.friendies.adapter.RecentlyAddedItemAdapter;
import com.example.friendies.books.BookDetailsActivity;
import com.example.friendies.books.BooksActivity;
import com.example.friendies.model.CategoryModel;
import com.example.friendies.model.MostDownloadsItemModel;
import com.example.friendies.model.OtherItemModel;
import com.example.friendies.model.PopularItemModel;
import com.example.friendies.model.RecentlyAddedItemModel;
import com.example.friendies.ui.home.HomeFragment;
import com.example.friendies.ui.home.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<CategoryModel> list;
    CategoryAdapter adapter;

    private RequestQueue requestQueue;
    private final String JSON_URL = "http://192.168.43.56:8000/api/category/read";
    private final String IMG_URL = "http://192.168.43.56:8000/images/";
    private JsonArrayRequest request;

    View root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_category,container,false);

        recyclerView=root.findViewById(R.id.recyclerview_category);
        list = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(root.getContext());

        jsonRequest();


        // Item Onclick
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(root.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String toptext = list.get(position).getTitle();
                        Intent intent = new Intent(root.getContext(), BooksActivity.class);
                        intent.putExtra("CATEGORY_TOPTEXT", toptext);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return root;
    }

    private void jsonRequest() {
        request = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                        // Recently Added Item
                        CategoryModel model = new CategoryModel();
                        model.setId(jsonObject.getInt("id"));
                        model.setTitle(jsonObject.getString("title"));
                        model.setImage(IMG_URL + jsonObject.getString("image"));
                        list.add(model);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Category Item
                recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(),3));
                adapter=new CategoryAdapter(root.getContext(),list);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(request);
    }
}
