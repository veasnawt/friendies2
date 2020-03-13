package com.example.friendies.ui.category;

import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.adapter.CategoryAdapter;
import com.example.friendies.model.CategoryModel;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<CategoryModel> list;
    CategoryAdapter adapter;


    View root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_category,container,false);
        recyclerView=root.findViewById(R.id.recyclerview_category);
        list = new ArrayList<>();

        for (int i=0; i<10;i++){
            CategoryModel model = new CategoryModel();
            model.setTitle("The RainMaker");
            model.setUrl_img("https://4.bp.blogspot.com/-Ur_U1Lryjlw/VE3j6LZaWlI/AAAAAAAAAI0/r9OKbX2vfKo/s1600/6.jpg");
            list.add(model);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(),2));
        adapter=new CategoryAdapter(root.getContext(),list);
        recyclerView.setAdapter(adapter);



        return root;

    }
}
