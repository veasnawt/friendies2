package com.example.friendies.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.friendies.MainActivity;
import com.example.friendies.R;
import com.example.friendies.adapter.MostDownloadsItemAdapter;
import com.example.friendies.adapter.OtherItemAdapter;
import com.example.friendies.adapter.PopularItemAdapter;
import com.example.friendies.adapter.RecentlyAddedItemAdapter;
import com.example.friendies.books.BooksActivity;
import com.example.friendies.model.MostDownloadsItemModel;
import com.example.friendies.model.OtherItemModel;
import com.example.friendies.model.PopularItemModel;
import com.example.friendies.model.RecentlyAddedItemModel;
import com.example.friendies.register.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerViewPopularItem,
            recyclerViewRecentlyAddedItem,
            recyclerviewMostDownloadsItem,
            recyclerviewOtherItem;
    ArrayList<PopularItemModel> listPopularModel;
    ArrayList<RecentlyAddedItemModel> listRecentlyAddedModel;
    ArrayList<MostDownloadsItemModel> listMostDownloadsModel;
    ArrayList<OtherItemModel> listOtherModel;
    PopularItemAdapter popularItemAdapter;
    RecentlyAddedItemAdapter recentlyAddedItemAdapter;
    MostDownloadsItemAdapter mostDownloadsItemAdapter;
    OtherItemAdapter otherItemAdapter;

    CardView recentlyAddedBook;
    TextView recently_added_book_see_all;

    private RequestQueue requestQueue;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Recycler Views
        recyclerViewPopularItem = root.findViewById(R.id.popular_recyclerview);
        recyclerViewRecentlyAddedItem = root.findViewById(R.id.recently_added_recyclerview);
        recyclerviewMostDownloadsItem = root.findViewById(R.id.most_downloads_recyclerview);
        recyclerviewOtherItem = root.findViewById(R.id.other_recyclerview);

        recentlyAddedBook = root.findViewById(R.id.recently_added_book);
        recently_added_book_see_all = root.findViewById(R.id.recently_added_book_see_all);

        // Popular List
        listPopularModel = new ArrayList<>();
        listRecentlyAddedModel = new ArrayList<>();
        listMostDownloadsModel = new ArrayList<>();
        listOtherModel = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(root.getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://127.0.0.1:8000/api/book/read", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.d("JSONArray", "onResponse: " + jsonObject.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);

        for (int i=0;i<7;i++) {

            // Popular Item
            PopularItemModel model = new PopularItemModel();
            model.setId(i);
            model.setImgCover(R.drawable.popularbook);
            listPopularModel.add(model);

            // Recently Added Item;
            RecentlyAddedItemModel model1 = new RecentlyAddedItemModel();
            model1.setId(i);
            model1.setImgCover(R.drawable.lioness);
            model1.setTitle("King of Fighters");
            model1.setAuthor("Harry Potter");
            listRecentlyAddedModel.add(model1);

            // Most Downloads Item;
            MostDownloadsItemModel model2 = new MostDownloadsItemModel();
            model2.setId(i);
            model2.setImgCover(R.drawable.sololevel);
            model2.setTitle("King of Fighters");
            model2.setAuthor("Harry Potter");
            listMostDownloadsModel.add(model2);

            // Other Item;
            OtherItemModel model3 = new OtherItemModel();
            model3.setId(i);
            model3.setImgCover(R.drawable.sunmoonstars);
            model3.setTitle("King of Fighters");
            model3.setAuthor("Harry Potter");
            listOtherModel.add(model3);

        }

        // Popular Item
        recyclerViewPopularItem.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularItemAdapter = new PopularItemAdapter(root.getContext(),listPopularModel);
        recyclerViewPopularItem.setAdapter(popularItemAdapter);

        // Recently Added Item
        recyclerViewRecentlyAddedItem.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recentlyAddedItemAdapter = new RecentlyAddedItemAdapter(root.getContext(), listRecentlyAddedModel);
        recyclerViewRecentlyAddedItem.setAdapter(recentlyAddedItemAdapter);

        // Most Downloads Item
        recyclerviewMostDownloadsItem.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mostDownloadsItemAdapter = new MostDownloadsItemAdapter(root.getContext(), listMostDownloadsModel);
        recyclerviewMostDownloadsItem.setAdapter(mostDownloadsItemAdapter);

        // Other Item
        recyclerviewOtherItem.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false));
        otherItemAdapter = new OtherItemAdapter(root.getContext(), listOtherModel);
        recyclerviewOtherItem.setAdapter(otherItemAdapter);


        // Book Onclick
        recently_added_book_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeFragment.this.getActivity(), BooksActivity.class));
            }
        });

        return root;
    }
}
