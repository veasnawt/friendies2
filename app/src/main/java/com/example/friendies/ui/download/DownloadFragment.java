package com.example.friendies.ui.download;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendies.R;
import com.example.friendies.adapter.DownloadsAdapter;
import com.example.friendies.books.BookDetailsActivity;
import com.example.friendies.model.DownloadsModel;

import java.util.ArrayList;

public class DownloadFragment extends Fragment {


    RecyclerView recyclerViewDownloads;
    ArrayList<DownloadsModel> listDownloadsModel;
    DownloadsAdapter downloadsAdapter;

    String reqDownload = "no_req_download";

    View root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_download,container,false);

        recyclerViewDownloads = root.findViewById(R.id.download_recyclerview);
        try {
            reqDownload = BookDetailsActivity.getActivityInstance().getReqDownload();
        }catch (Exception e) {
            Log.d("REQ_DOWNLOAD", "Request Download Error: " + e.getMessage());
        }

        while (reqDownload == "req_download") {
            listDownloadsModel = new ArrayList<>();
            listDownloadsModel = BookDetailsActivity.getActivityInstance().getData();
            recyclerViewDownloads.setLayoutManager(new GridLayoutManager(root.getContext(),1));
            downloadsAdapter = new DownloadsAdapter(root.getContext(), listDownloadsModel);
            recyclerViewDownloads.setAdapter(downloadsAdapter);
            reqDownload = "requested";
        }

        return root;
    }
}
