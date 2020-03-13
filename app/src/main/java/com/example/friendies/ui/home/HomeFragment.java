package com.example.friendies.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.friendies.R;
import com.example.friendies.adapter.PopularItemAdapter;
import com.example.friendies.adapter.RecentlyAddedItemAdapter;
import com.example.friendies.model.PopularItemModel;
import com.example.friendies.model.RecentlyAddedItemModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerViewPopularItem, recyclerViewRecentlyAddedItem;
    ArrayList<PopularItemModel> listPopularModel;
    ArrayList<RecentlyAddedItemModel> listRecentlyAddedModel;
    PopularItemAdapter popularItemAdapter;
    RecentlyAddedItemAdapter recentlyAddedItemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Recycler Views
        recyclerViewPopularItem = root.findViewById(R.id.popular_recyclerview);
        recyclerViewRecentlyAddedItem = root.findViewById(R.id.recently_added_recyclerview);

        // Popular List
        listPopularModel = new ArrayList<>();
        listRecentlyAddedModel = new ArrayList<>();

        // Recently Added  List
        for (int i=0;i<3;i++){

            // Popular Item
            PopularItemModel model = new PopularItemModel();
            model.setId(i);
            model.setImgCover(R.drawable.popularbook);
            listPopularModel.add(model);

            // Recently Added Item;
            RecentlyAddedItemModel model1 = new RecentlyAddedItemModel();
            model1.setId(i);
            model1.setImgCover(R.drawable.sololevel);
            model1.setTitle("King of Fighters");
            model1.setAuthor("Harry Potter");
            listRecentlyAddedModel.add(model1);
        }

        // Popular Item
        recyclerViewPopularItem.setLayoutManager(new LinearLayoutManager(root.getContext()));
        popularItemAdapter = new PopularItemAdapter(root.getContext(),listPopularModel);
        recyclerViewPopularItem.setAdapter(popularItemAdapter);

        // Recently Added Item
        recyclerViewRecentlyAddedItem.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recentlyAddedItemAdapter = new RecentlyAddedItemAdapter(root.getContext(), listRecentlyAddedModel);
        recyclerViewRecentlyAddedItem.setAdapter(recentlyAddedItemAdapter);

        return root;
    }
}
