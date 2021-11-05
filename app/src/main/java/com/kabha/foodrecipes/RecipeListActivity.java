package com.kabha.foodrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kabha.foodrecipes.adapters.OnRecipeListener;
import com.kabha.foodrecipes.adapters.RecipeRecyclerAdapter;
import com.kabha.foodrecipes.models.Recipe;
import com.kabha.foodrecipes.requests.RecipeApi;
import com.kabha.foodrecipes.requests.ServiceGenerator;
import com.kabha.foodrecipes.requests.responses.RecipeResponse;
import com.kabha.foodrecipes.requests.responses.RecipeSearchResponse;
import com.kabha.foodrecipes.util.Testing;
import com.kabha.foodrecipes.util.VerticalSpacingItemDecorator;
import com.kabha.foodrecipes.viewmodels.RecipeListViewModel;
import com.kabha.foodrecipes.viewmodels.RecipeViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//will be responsible for displaying list of recipes
public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private RecipeListViewModel mRecipeListViewModel;
    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter mAdapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recipe_list);
        mSearchView = findViewById(R.id.search_view);

        //instantiate the ViewModel
        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        initSearchView();
        if(!mRecipeListViewModel.isViewingRecipes()){
            //display the search categories
            displaySearchCategories();
        }
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void subscribeObservers(){
        //<--
        // this method will triggered if any thing updated/changed/deleted
        mRecipeListViewModel.getRecipes().observe(this, recipes -> {
            if(recipes != null){
                if(mRecipeListViewModel.isViewingRecipes()){
                    Testing.printRecipes(recipes,"Recipes Test TAG");
                    mRecipeListViewModel.setIsPerformingQuery(false);
                    mAdapter.setRecipes(recipes);
                }
            }
        });
        mRecipeListViewModel.isQueryExhausted().observe(this, aBoolean -> {
            if(aBoolean){
                mAdapter.setQueryExhausted();
            }
        });
    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!mRecyclerView.canScrollVertically(1)){
                    //search the next page
                    mRecipeListViewModel.searchNextPage();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    //-->
    private void initSearchView(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.displayLoading();
                mRecipeListViewModel.searchRecipesApi(query,1);
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void displaySearchCategories(){
        mRecipeListViewModel.setmIsViewingRecipes(false);
        mAdapter.displaySearchCategory();
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(this,RecipeDescription.class);
        intent.putExtra("recipe",mAdapter.getSelectedRecipe(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {
        switch (category){
            case "Barbeque":
                category = "bbq";
                break;
            case "Breakfast":
                category = "lettuce";
                break;
            case "Brunch":
                category = "fish";
                break;
            case "Dinner":
                category = "bacon";
                break;
            case "Desserts":
                category = "blackberry";
                break;
            case "Italian":
                category = "pepperoni";
            default:
                break;
        }
        mAdapter.displayLoading();
        mRecipeListViewModel.searchRecipesApi(category,1);
    }

    @Override
    public void onBackPressed() {
        if(mRecipeListViewModel.onBackPressed()){
            super.onBackPressed();
        }else{
            displaySearchCategories();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_categories){
            displaySearchCategories();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}