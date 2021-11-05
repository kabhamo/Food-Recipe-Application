package com.kabha.foodrecipes.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.kabha.foodrecipes.models.Recipe;
import com.kabha.foodrecipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;
    private String mQuery;
    private int mPageNumber;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>();


    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }
    private RecipeRepository(){
        mRecipeApiClient = RecipeApiClient.getInstance();
        initMediator();
    }
    //<--
    private void initMediator(){
        LiveData<List<Recipe>> recipeListApiSource = mRecipeApiClient.getRecipes();
        mRecipes.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes != null){
                    mRecipes.setValue(recipes);
                    doneQuery(recipes);
                }else{
                    //search database cache
                    doneQuery(null);
                }
            }
        });
    }

    private void doneQuery(List<Recipe> list){
        if(list != null){
            if(list.size() % 30 != 0){
                mIsQueryExhausted.setValue(true);
            }
        }else{
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted(){ return mIsQueryExhausted; }

    public LiveData<List<Recipe>> getRecipes(){ return mRecipes; }

    public LiveData<Recipe> getRecipe(){ return mRecipeApiClient.getRecipe(); }

    public LiveData<Boolean> isRecipeRequestTimeout(){ return mRecipeApiClient.isRecipeRequestTimeout(); }

    //-->
    public void searchRecipesApi(String query, int pageNumber){
        if(pageNumber == 0){
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mIsQueryExhausted.setValue(false);
        mRecipeApiClient.searchRecipesApi(query,pageNumber);
    }

    public void searchRecipeById(String recipeId){ mRecipeApiClient.searchRecipeById(recipeId); }

    public void searchNextPage(){ searchRecipesApi(mQuery, mPageNumber+1); }

    public void cancelRequest(){ mRecipeApiClient.cancelRequest(); }

}

