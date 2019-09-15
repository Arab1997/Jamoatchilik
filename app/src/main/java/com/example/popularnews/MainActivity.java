package com.example.popularnews;
/*
import android.app.SearchManager;*/
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
/*import android.widget.SearchView*/;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.popularnews.model.Example;
import com.example.popularnews.network.ApiClient;
import com.example.popularnews.network.ApiRespon;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;

import static com.example.popularnews.PaginationListener.PAGES;
import static com.example.popularnews.PaginationListener.PAGE_START;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Example> examples = new ArrayList<>();
    private Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();
    private TextView topHeadline;
 //   private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorLayout;
    private ImageView errorImage, search, xabar, added, talkif, btn_like, btn_dislike,  options;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = PAGES;
    private boolean isLoading = false;
    int itemCount = 1;

    private ApiClient apiClient;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


     //   swipeRefresh.setOnRefreshListener(this);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

      //  topHeadline = findViewById(R.id.topheadelines);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.addOnScrollListener(new PaginationListener((LinearLayoutManager) layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                LoadJson();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading; /*   adapter = new Adapter(new ArrayList<Example> ());
        recyclerView.setAdapter(adapter);
        LoadJson();
*/
            }
        });

   //     onLoadingSwipeRefresh();

     //    progressBar = (ProgressBar) findViewById(R.id.main_progress);
        options = findViewById(R.id.options);
        search = findViewById(R.id.search);
        btn_like = findViewById(R.id.btn_like);
        xabar = findViewById(R.id.xabar);
        talkif = findViewById(R.id.taklif1);
        added = findViewById(R.id.added);
        btn_dislike = findViewById(R.id.btn_dislike);

        errorLayout = findViewById(R.id.errorLayout);
        errorImage = findViewById(R.id.errorImage);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);

        LoadJson();

        xabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });
        talkif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });
        added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SendActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });


    }
    public void LoadJson(){
        /*errorLayout.setVisibility(View.INVISIBLE);*/
     //   topHeadline.setVisibility(View.INVISIBLE);

       swipeRefreshLayout.setRefreshing(true);


     /*   ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);*/
       ApiClient apiClient = ApiRespon.getClient().create(ApiClient.class);
        Call<List<Example>> call = apiClient.getResult();

            call.enqueue(new Callback<List<Example>>() {

                @Override
                public void onResponse(Call<List<Example>> call, retrofit2.Response<List<Example>> response)
                {
                 //   for (int i = 0; i < 10; i++)
                        if (response.isSuccessful() && response.body() !=null){
                        if (!examples.isEmpty()){
                            examples.clear();
                        }
                        //    if (currentPage != PAGE_START) adapter.removeLoading();
                    examples = response.body();
                    adapter = new Adapter(examples, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();

                    initListener();

               //     topHeadline.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                          /*  if (currentPage < totalPage) {
                                adapter.addLoading();
                            } else {
                                isLastPage = true;
                            }*/
                            isLoading = false;
                } else {

                    topHeadline.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                    String errorCode;
                    switch (response.code()){
                        case 404:
                            errorCode = " 404 bot found";
                            break;
                        case 500:
                            errorCode = " 500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }
                    showErrorMessage(
                            R.drawable.no_result,
                            "No Result",
                            "Please Try Again!\n"+
                                    errorCode);
                }
                }


                @Override
                public void onFailure(Call<List<Example>> call, Throwable t) {
           //         topHeadline.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                 /*   showErrorMessage(
                            R.drawable.no_result,
                            "Oops..",
                            "Network failure, Please Try Again!\n"+
                                    t.toString());*/
                }
            });
        }

    private void initListener(){

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JSONObject jsonObject  = null ;
                Intent intent = new Intent(MainActivity.this, AnimeActivity.class);

                Example items = new Gson().fromJson(jsonObject.toString(),Example.class);
                intent.putExtra("fio", items.getFio());
                intent.putExtra("taklif", items.getTaklif());
                intent.putExtra("taklif", items.getTaklif());
/*intent.putExtra("category", items.getCategory().getName());
                intent.putExtra("region", items.getRegion().getName());*/
                intent.putExtra("created_time",  items.getCreatedTime());
                intent.putExtra("dislike_count",  items.getDislikeCount());
                intent.putExtra("like_count",  items.getLikeCount());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE); /* SearchView */
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Ismlar ...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
               //     onLoadingSwipeRefresh();   /*LoadJson(query);*/
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LoadJson();/*harflarni yozganda qidiradi */
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

   /* @Override
    public void onRefresh() {
       LoadJson();

    }*/

        @Override
        public void onRefresh() {
            itemCount = 0;
            currentPage = PAGE_START;
            isLastPage = false;
          //  adapter.clear();
            //initListener();
            LoadJson();
        }
    private void onLoadingSwipeRefresh(){

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson();
                    }
                }
        );
    }

    private void showErrorMessage(int imageView, String title, String message){

        if (errorLayout.getVisibility() == View.GONE){
        errorLayout.setVisibility(View.VISIBLE);

        }
        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    onLoadingSwipeRefresh();
            }
        });
    }

}

