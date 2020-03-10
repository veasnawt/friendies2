package com.example.friendies.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            model.setId(i+1);
            model.setUrl_imgCover("https://4.bp.blogspot.com/-Ur_U1Lryjlw/VE3j6LZaWlI/AAAAAAAAAI0/r9OKbX2vfKo/s1600/6.jpg");
            listPopularMoadel.add(model);
        }
        recyclerViewPopularItem.setLayoutManager(new LinearLayoutManager(root.getContext()));
        popularItemAdapter = new PopularItemAdapter(root.getContext(),listPopularMoadel);
        recyclerViewPopularItem.setAdapter(popularItemAdapter);

        return root;
    }
}
