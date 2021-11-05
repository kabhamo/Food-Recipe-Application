package com.kabha.foodrecipes.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kabha.foodrecipes.AppExecuters;
import com.kabha.foodrecipes.models.Recipe;
import com.kabha.foodrecipes.repositories.RecipeRepository;
import com.kabha.foodrecipes.requests.responses.RecipeResponse;
import com.kabha.foodrecipes.requests.responses.RecipeSearchResponse;
import com.kabha.foodrecipes.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class RecipeApiClient {

    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes ;
    private RetrieveRecipesRunnable mRetrieveRecipesRunnable;
    private MutableLiveData<Recipe> mRecipe;
    private RetrieveRecipeRunnable mRetrieveRecipeRunnable;
    private MutableLiveData<Boolean> mRecipeRequestTimeout = new MutableLiveData<>();

    public static RecipeApiClient getInstance(){
        if(instance == null){
            instance = new RecipeApiClient();
        }
        return instance;
    }
    private RecipeApiClient(){

        mRecipes = new MutableLiveData<>();
        mRecipe = new MutableLiveData<>();
    }

    //<--
    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }

    public LiveData<Recipe> getRecipe(){ return mRecipe; }

    public LiveData<Boolean> isRecipeRequestTimeout(){ return mRecipeRequestTimeout; }

    //Here I will make the request to the service
    //Creating new Runnable task that would be send to the Executers
    //-->
    public void searchRecipesApi(String query, int pageNumber){
        if(mRetrieveRecipesRunnable != null){
            mRetrieveRecipesRunnable = null;
        }
        mRetrieveRecipesRunnable = new RetrieveRecipesRunnable(query, pageNumber);
        final Future handler = AppExecuters.getInstance().networkIO().submit(mRetrieveRecipesRunnable);

        AppExecuters.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //here referencing my handler
                //this is the reference for the task passing true to interrupt this runnable and stop the request
                handler.cancel(true);
            }
        },Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void searchRecipeById(String recipeId){
        if(mRetrieveRecipeRunnable != null){
            mRetrieveRecipeRunnable = null;
        }
        mRetrieveRecipeRunnable = new RetrieveRecipeRunnable(recipeId);

        final Future handler = AppExecuters.getInstance().networkIO().submit(mRetrieveRecipeRunnable);

        mRecipeRequestTimeout.setValue(false);
        AppExecuters.getInstance().networkIO().schedule(() -> {
            //will interrupt the runnable if it still running after 4sec
            mRecipeRequestTimeout.postValue(true);
            handler.cancel(true);
        },Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    //the runnable class that will be used to retrieve the SearchRequest data from rest api
    private class RetrieveRecipesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int page) {
            this.query = query;
            this.pageNumber = page;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query,pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse)response.body()).getRecipes());
                    if(pageNumber == 1){
                        //send this recipes to the LiveData
                        //postValue for background thread
                        mRecipes.postValue(list);
                    }else{
                        //appending new entries to the current recipes
                        List<Recipe> currentRecipes = mRecipes.getValue();
                        currentRecipes.addAll(list);
                        mRecipes.postValue(currentRecipes);
                    }
                }else{
                    String error = response.errorBody().string();
                    Log.v("TAG","run: " + error);
                    //!null
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecipes.postValue(null);
            }
        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber){
            return ServiceGenerator.getRecipeApi().searchRecipe(query,String.valueOf(pageNumber));
        }

        private void cancelRequest(){
            Log.v("TAG","canceling the search request");
            cancelRequest = true;
        }
    }

    private class RetrieveRecipeRunnable implements Runnable{

        private String recipeId;
        boolean cancelRequest;

        public RetrieveRecipeRunnable(String recipeId) {
            this.recipeId = recipeId;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipe(recipeId).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    Recipe recipe = ((RecipeResponse)response.body()).getRecipe();
                    mRecipe.postValue(recipe); //will update the LiveData
                }else{
                    String error = response.errorBody().string();
                    Log.v("TAG","run: " + error);
                    //!null
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecipes.postValue(null);
            }
        }

        private Call<RecipeResponse> getRecipe(String recipeId){
            return ServiceGenerator.getRecipeApi().getRecipe(recipeId);
        }

        private void cancelRequest(){
            Log.v("TAG","canceling the search request");
            cancelRequest = true;
        }
    }

    public void cancelRequest(){
        if(mRetrieveRecipesRunnable != null){
            mRetrieveRecipesRunnable.cancelRequest();
        }
        if(mRetrieveRecipeRunnable != null){
            mRetrieveRecipeRunnable.cancelRequest();
        }
    }
}
