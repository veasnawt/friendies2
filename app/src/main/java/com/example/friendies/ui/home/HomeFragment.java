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
import com.example.friendies.model.PopularItemModel;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerViewPopularItem;
    ArrayList<PopularItemModel> listPopularMoadel;
    PopularItemAdapter popularItemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewPopularItem = root.findViewById(R.id.popular_recyclerview);

        listPopularMoadel = new ArrayList<>();
        for (int i=0;i<3;i++){
            PopularItemModel model = new PopularItemModel();
            model.setId(i);
            model.setImgCover(R.drawable.popularbook);
            listPopularMoadel.add(model);
        }
        recyclerViewPopularItem.setLayoutManager(new LinearLayoutManager(root.getContext()));
        popularItemAdapter = new PopularItemAdapter(root.getContext(),listPopularMoadel);
        recyclerViewPopularItem.setAdapter(popularItemAdapter);

        return root;
    }
}
