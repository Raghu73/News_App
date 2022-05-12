package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//4efa282a2092423189cffe699822396b  new api key
//https://newsapi.org/ for new api generate

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategorClickInterface {
    private RecyclerView newsRV,categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV =findViewById(R.id.idRVNews);
          categoryRV =findViewById(R.id.idRVcategories);
          loadingPB =findViewById(R.id.idPBLoading);
          articlesArrayList = new ArrayList<>();
          newsRVAdapter = new NewsRVAdapter(articlesArrayList,this);
          categoryRVModelArrayList = new ArrayList<>();
          categoryRVAdapter = new CategoryRVAdapter(categoryRVModelArrayList,this,this::onCategoryClick);
          newsRV.setLayoutManager(new LinearLayoutManager(this));
          newsRV.setAdapter(newsRVAdapter);
          categoryRV.setAdapter(categoryRVAdapter);
          getCategories();
          getNews("All");
          newsRVAdapter.notifyDataSetChanged();
    }
     //creating the methods for getting the data
    private void getCategories(){
        categoryRVModelArrayList.add(new CategoryRVModel("All","https://images.unsplash.com/photo-1446776653964-20c1d3a81b06?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1171&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("Technology","https://images.unsplash.com/photo-1507668077129-56e32842fceb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("Science","https://images.unsplash.com/photo-1628595351029-c2bf17511435?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("Sports","https://images.unsplash.com/photo-1612872087720-bb876e2e67d1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1307&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("General","https://images.unsplash.com/photo-1432821596592-e2c18b78144f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("Business","https://images.unsplash.com/photo-1507679799987-c73779587ccf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1171&q=80="));
        categoryRVModelArrayList.add(new CategoryRVModel("Entertainment","https://images.unsplash.com/photo-1616565441366-34a5fe33fe42?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("Health","https://images.unsplash.com/photo-1530026405186-ed1f139313f8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    private  void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL ="https://newsapi.org/v2/top-headlines?country=in&category="+ category +"&apiKey=4efa282a2092423189cffe699822396b";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=4efa282a2092423189cffe699822396b";
        String BASE_URL = "https://newsapi.org";
        //call retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //convert the data gson form
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal>call;
        if (category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }else {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }
        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE );
                ArrayList<Articles> articles = newsModal.getArticles();
                for (int i=0; i<articles.size(); i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail To Get News", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModelArrayList.get(position).getCategory();
        getNews(category);
    }

}