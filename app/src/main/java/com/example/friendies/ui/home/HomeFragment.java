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

import com.example.friendies.MainActivity;
import com.example.friendies.R;
import com.example.friendies.adapter.MostDownloadsItemAdapter;
import com.example.friendies.adapter.OtherItemAdapter;
import com.example.friendies.adapter.PopularItemAdapter;
import com.example.friendies.adapter.RecentlyAddedItemAdapter;
import com.example.friendies.model.MostDownloadsItemModel;
import com.example.friendies.model.OtherItemModel;
import com.example.friendies.model.PopularItemModel;
import com.example.friendies.model.RecentlyAddedItemModel;

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

    LinearLayoutManager horizontalLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Recycler Views
        recyclerViewPopularItem = root.findViewById(R.id.popular_recyclerview);
        recyclerViewRecentlyAddedItem = root.findViewById(R.id.recently_added_recyclerview);
        recyclerviewMostDownloadsItem = root.findViewById(R.id.most_downloads_recyclerview);
        recyclerviewOtherItem = root.findViewById(R.id.other_recyclerview);

        // Popular List
        listPopularModel = new ArrayList<>();
        listRecentlyAddedModel = new ArrayList<>();
        listMostDownloadsModel = new ArrayList<>();
        listOtherModel = new ArrayList<>();

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

        return root;
    }
}
