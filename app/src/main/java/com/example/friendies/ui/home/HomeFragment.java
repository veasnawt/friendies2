package com.example.friendies.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.friendies.MainActivity;
import com.example.friendies.R;
import com.example.friendies.adapter.MostDownloadsItemAdapter;
import com.example.friendies.adapter.OtherItemAdapter;
import com.example.friendies.adapter.PopularItemAdapter;
import com.example.friendies.adapter.RecentlyAddedItemAdapter;
import com.example.friendies.books.BookDetailsActivity;
import com.example.friendies.books.BooksActivity;
import com.example.friendies.model.MostDownloadsItemModel;
import com.example.friendies.model.OtherItemModel;
import com.example.friendies.model.PopularItemModel;
import com.example.friendies.model.RecentlyAddedItemModel;
import com.example.friendies.profile.Profile_view;
import com.example.friendies.profile.UseSingOutAccount;
import com.example.friendies.register.RegisterActivity;
import com.squareup.picasso.Picasso;

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

    ImageView profile;

    CardView recentlyAddedBook;
    TextView popular_book_see_all, recently_added_book_see_all, most_downloads_book_see_all, other_book_see_all;

    private RequestQueue requestQueue;
    private final String JSON_URL = "http://192.168.43.56:8000/api/book/read";
    private final String IMG_URL = "http://192.168.43.56:8000/images/";
    private JsonArrayRequest request;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        requestQueue = Volley.newRequestQueue(HomeFragment.this.getContext());

        // Recycler Views
        recyclerViewPopularItem = root.findViewById(R.id.popular_recyclerview);
        recyclerViewRecentlyAddedItem = root.findViewById(R.id.recently_added_recyclerview);
        recyclerviewMostDownloadsItem = root.findViewById(R.id.most_downloads_recyclerview);
        recyclerviewOtherItem = root.findViewById(R.id.other_recyclerview);

        profile=root.findViewById(R.id.profile_image);

        String image = MainActivity.URL_IMAGE;

        if (!image.equals("")){
            Toast.makeText(root.getContext(), "No", Toast.LENGTH_SHORT).show();
            Picasso.get().load(MainActivity.URL_IMAGE).into(profile);
        }


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.EMAIL.equals("")){
                    startActivity(new Intent(root.getContext(), Profile_view.class));
                }
                else {
                    startActivity(new Intent(root.getContext(), UseSingOutAccount.class));
                }

            }
        });

        recentlyAddedBook = root.findViewById(R.id.recently_added_book);
        popular_book_see_all = root.findViewById(R.id.popular_book_see_all);
        recently_added_book_see_all = root.findViewById(R.id.recently_added_book_see_all);
        most_downloads_book_see_all = root.findViewById(R.id.most_downloads_book_see_all);
        other_book_see_all = root.findViewById(R.id.other_book_see_all);

        // Popular List
        listPopularModel = new ArrayList<>();
        listRecentlyAddedModel = new ArrayList<>();
        listMostDownloadsModel = new ArrayList<>();
        listOtherModel = new ArrayList<>();

        jsonRequest();

        // Popular See all onclick
        popular_book_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toptext = "Popular";
                Intent intent = new Intent(HomeFragment.this.getActivity(), BooksActivity.class);
                intent.putExtra("POPULAR_TOPTEXT", toptext);
                startActivity(intent);
            }
        });

        // Recently Added See all onclick
        recently_added_book_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeFragment.this.getActivity(), BooksActivity.class));
                String toptext = "Recently Added";
                Intent intent = new Intent(HomeFragment.this.getActivity(), BooksActivity.class);
                intent.putExtra("RECENTLY_ADDED_TOPTEXT", toptext);
                startActivity(intent);
            }
        });

        // Most Downloads See all onclick
        most_downloads_book_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toptext = "Most Downloads";
                Intent intent = new Intent(HomeFragment.this.getActivity(), BooksActivity.class);
                intent.putExtra("MOST_DOWNLOADS_TOPTEXT", toptext);
                startActivity(intent);
            }
        });

        // Other See all onclick
        other_book_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toptext = "Others";
                Intent intent = new Intent(HomeFragment.this.getActivity(), BooksActivity.class);
                intent.putExtra("OTHER_TOPTEXT", toptext);
                startActivity(intent);
            }
        });

        // Popular Book Onclick
        recyclerViewPopularItem.addOnItemTouchListener(
                new RecyclerItemClickListener(root.getContext(), recyclerViewPopularItem ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Intent intent = new Intent(HomeFragment.this.getActivity(), BookDetailsActivity.class);
                        intent.putExtra("BOOK_COVER", listPopularModel.get(position).getCover());
                        intent.putExtra("BOOK_TITLE", listPopularModel.get(position).getTitle());
                        intent.putExtra("BOOK_AUTHOR", listPopularModel.get(position).getAuthor());
                        intent.putExtra("BOOK_DESCRIPTION", listPopularModel.get(position).getDescription());
                        intent.putExtra("BOOK_IMAGE", listPopularModel.get(position).getImgCover());
                        intent.putExtra("BOOK_CATEGORY_ID", listPopularModel.get(position).getCategory_id());
                        intent.putExtra("BOOK_NOD", listPopularModel.get(position).getNod());
                        intent.putExtra("BOOK_RATING", listPopularModel.get(position).getRating());
                        intent.putExtra("BOOK_PDF", listPopularModel.get(position).getPdf());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        // Recently Added Book Onclick
        recyclerViewRecentlyAddedItem.addOnItemTouchListener(
                new RecyclerItemClickListener(root.getContext(), recyclerViewPopularItem ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(HomeFragment.this.getActivity(), BookDetailsActivity.class);
                        intent.putExtra("BOOK_COVER", listRecentlyAddedModel.get(position).getCover());
                        intent.putExtra("BOOK_TITLE", listRecentlyAddedModel.get(position).getTitle());
                        intent.putExtra("BOOK_AUTHOR", listRecentlyAddedModel.get(position).getAuthor());
                        intent.putExtra("BOOK_DESCRIPTION", listRecentlyAddedModel.get(position).getDescription());
                        intent.putExtra("BOOK_IMAGE", listRecentlyAddedModel.get(position).getImgCover());
                        intent.putExtra("BOOK_CATEGORY_ID", listRecentlyAddedModel.get(position).getCategory_id());
                        intent.putExtra("BOOK_NOD", listRecentlyAddedModel.get(position).getNod());
                        intent.putExtra("BOOK_RATING", listRecentlyAddedModel.get(position).getRating());
                        intent.putExtra("BOOK_PDF", listRecentlyAddedModel.get(position).getPdf());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        // Most Downloads Book Onclick
        recyclerviewMostDownloadsItem.addOnItemTouchListener(
                new RecyclerItemClickListener(root.getContext(), recyclerViewPopularItem ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(HomeFragment.this.getActivity(), BookDetailsActivity.class);
                        intent.putExtra("BOOK_COVER", listMostDownloadsModel.get(position).getCover());
                        intent.putExtra("BOOK_TITLE", listMostDownloadsModel.get(position).getTitle());
                        intent.putExtra("BOOK_AUTHOR", listMostDownloadsModel.get(position).getAuthor());
                        intent.putExtra("BOOK_DESCRIPTION", listMostDownloadsModel.get(position).getDescription());
                        intent.putExtra("BOOK_IMAGE", listMostDownloadsModel.get(position).getImgCover());
                        intent.putExtra("BOOK_CATEGORY_ID", listMostDownloadsModel.get(position).getCategory_id());
                        intent.putExtra("BOOK_NOD", listMostDownloadsModel.get(position).getNod());
                        intent.putExtra("BOOK_RATING", listMostDownloadsModel.get(position).getRating());
                        intent.putExtra("BOOK_PDF", listMostDownloadsModel.get(position).getPdf());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        // Other Book Onclick
        recyclerviewOtherItem.addOnItemTouchListener(
                new RecyclerItemClickListener(root.getContext(), recyclerViewPopularItem ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(HomeFragment.this.getActivity(), BookDetailsActivity.class);
                        intent.putExtra("BOOK_COVER", listOtherModel.get(position).getCover());
                        intent.putExtra("BOOK_TITLE", listOtherModel.get(position).getTitle());
                        intent.putExtra("BOOK_AUTHOR", listOtherModel.get(position).getAuthor());
                        intent.putExtra("BOOK_DESCRIPTION", listOtherModel.get(position).getDescription());
                        intent.putExtra("BOOK_IMAGE", listOtherModel.get(position).getImgCover());
                        intent.putExtra("BOOK_CATEGORY_ID", listOtherModel.get(position).getCategory_id());
                        intent.putExtra("BOOK_NOD", listOtherModel.get(position).getNod());
                        intent.putExtra("BOOK_RATING", listOtherModel.get(position).getRating());
                        intent.putExtra("BOOK_PDF", listOtherModel.get(position).getPdf());
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

                        // Popular Item
                        PopularItemModel popularItemModel = new PopularItemModel();
                        popularItemModel.setId(jsonObject.getInt("id"));
                        popularItemModel.setImgCover(IMG_URL + jsonObject.getString("image"));
                        popularItemModel.setCover(IMG_URL + jsonObject.getString("cover"));
                        popularItemModel.setTitle(jsonObject.getString("title"));
                        popularItemModel.setAuthor(jsonObject.getString("author"));
                        popularItemModel.setDescription(jsonObject.getString("description"));
                        popularItemModel.setCategory_id(jsonObject.getInt("category_id"));
                        popularItemModel.setNod(jsonObject.getInt("nod"));
                        //popularItemModel.setRating(jsonObject.getInt("rating"));
                        popularItemModel.setPdf(jsonObject.getString("pdf"));
                        listPopularModel.add(popularItemModel);

                        // Recently Added Item
                        RecentlyAddedItemModel model = new RecentlyAddedItemModel();
                        model.setId(jsonObject.getInt("id"));
                        model.setImgCover(IMG_URL + jsonObject.getString("image"));
                        model.setCover(IMG_URL + jsonObject.getString("cover"));
                        model.setTitle(jsonObject.getString("title"));
                        model.setAuthor(jsonObject.getString("author"));
                        model.setDescription(jsonObject.getString("description"));
                        model.setCategory_id(jsonObject.getInt("category_id"));
                        model.setNod(jsonObject.getInt("nod"));
                        //model.setRating(jsonObject.getInt("rating"));
                        model.setPdf(jsonObject.getString("pdf"));
                        listRecentlyAddedModel.add(model);

                        // Most Downloads Item
                        MostDownloadsItemModel model1 = new MostDownloadsItemModel();
                        model1.setId(jsonObject.getInt("id"));
                        model1.setImgCover(IMG_URL + jsonObject.getString("image"));
                        model1.setCover(IMG_URL + jsonObject.getString("cover"));
                        model1.setTitle(jsonObject.getString("title"));
                        model1.setAuthor(jsonObject.getString("author"));
                        model1.setDescription(jsonObject.getString("description"));
                        model1.setCategory_id(jsonObject.getInt("category_id"));
                        model1.setNod(jsonObject.getInt("nod"));
                        //model1.setRating(jsonObject.getInt("rating"));
                        model1.setPdf(jsonObject.getString("pdf"));
                        listMostDownloadsModel.add(model1);

                        // Other Item
                        OtherItemModel model2 = new OtherItemModel();
                        model2.setId(jsonObject.getInt("id"));
                        model2.setImgCover(IMG_URL + jsonObject.getString("image"));
                        model2.setCover(IMG_URL + jsonObject.getString("cover"));
                        model2.setTitle(jsonObject.getString("title"));
                        model2.setAuthor(jsonObject.getString("author"));
                        model2.setDescription(jsonObject.getString("description"));
                        model2.setCategory_id(jsonObject.getInt("category_id"));
                        model2.setNod(jsonObject.getInt("nod"));
                        //model2.setRating(jsonObject.getInt("rating"));
                        model2.setPdf(jsonObject.getString("pdf"));
                        listOtherModel.add(model2);

                        //Log
                        Log.d("JSONObject", "onResponse: " +
                                jsonObject.getInt("id") + " " +
                                jsonObject.getString("title") + "   " +
                                jsonObject.getString("author")
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Popular Item
                recyclerViewPopularItem.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false));
                popularItemAdapter = new PopularItemAdapter(HomeFragment.this.getContext(),listPopularModel);
                recyclerViewPopularItem.setAdapter(popularItemAdapter);


                // Recently Added Item
                recyclerViewRecentlyAddedItem.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false));
                recentlyAddedItemAdapter = new RecentlyAddedItemAdapter(HomeFragment.this.getContext(), listRecentlyAddedModel);
                recyclerViewRecentlyAddedItem.setAdapter(recentlyAddedItemAdapter);

                // Most Downloads Item
                recyclerviewMostDownloadsItem.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false));
                mostDownloadsItemAdapter = new MostDownloadsItemAdapter(HomeFragment.this.getContext(), listMostDownloadsModel);
                recyclerviewMostDownloadsItem.setAdapter(mostDownloadsItemAdapter);

                // Other Item
                recyclerviewOtherItem.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false));
                otherItemAdapter = new OtherItemAdapter(HomeFragment.this.getContext(), listOtherModel);
                recyclerviewOtherItem.setAdapter(otherItemAdapter);

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
