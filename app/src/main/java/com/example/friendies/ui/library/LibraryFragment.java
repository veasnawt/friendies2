package com.example.friendies.ui.library;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.adapter.LibraryAdapter;
import com.example.friendies.model.LibraryModel;
import com.example.friendies.profile.EditProfile;
import com.example.friendies.ui.bookmark.BookmarkFragment;
import com.example.friendies.ui.bookmark.BookmarkViewModel;
import com.example.friendies.ui.category.CategoryFragment;
import com.example.friendies.ui.download.DownloadFragment;
import com.example.friendies.ui.home.HomeFragment;

import java.util.ArrayList;

public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryViewModel;
    RecyclerView historyRecyclerView;
    ArrayList<LibraryModel> list;

    private TextView tv_home, tv_Category, tv_Download, tv_Bookmarks;

    LibraryAdapter adapter;

    LinearLayoutManager H;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        libraryViewModel =
                ViewModelProviders.of(this).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        historyRecyclerView = root.findViewById(R.id.recycler_recent);
        tv_home = root.findViewById(R.id.tv_home_library);
        tv_Bookmarks = root.findViewById(R.id.tv_bookmarks_library);
        tv_Category = root.findViewById(R.id.tv_category_library);
        tv_Download = root.findViewById(R.id.tv_download_library);

        list = new ArrayList<>();
        for (int i=0; i<6; i++){
            LibraryModel model = new LibraryModel();
            model.setBookTitle("Solo Level");
            model.setURL_BookImage("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR-D9_R34RKHXPODsRnWrHhLUa7tHLPIdKx10EP4a7pboRDabrs&usqp=CAU");
            list.add(model);

        }
        H = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        historyRecyclerView.setLayoutManager(H);
        adapter = new LibraryAdapter(getContext(),list);
        historyRecyclerView.setAdapter(adapter);

//        tv_Download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LibraryFragment.this.getContext(), DownloadFragment.class));
//            }
//        });
//
//        tv_Category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LibraryFragment.this.getContext(), CategoryFragment.class));
//            }
//        });
//
//        tv_Bookmarks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LibraryFragment.this.getContext(), BookmarkFragment.class));
//            }
//        });
//
//        tv_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LibraryFragment.this.getContext(), EditProfile.class));
//            }
//        });


        return root;
    }
}
