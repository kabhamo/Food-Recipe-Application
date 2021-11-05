package com.kabha.foodrecipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kabha.foodrecipes.models.Recipe;
import com.kabha.foodrecipes.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private boolean mIsViewingRecipes;
    private boolean mIsPerformingQuery;

    public RecipeListViewModel(){
        mRecipeRepository = RecipeRepository.getInstance();
        mIsPerformingQuery = false;
    }

    //<--
    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeRepository.getRecipes();
    }

    public LiveData<Boolean> isQueryExhausted(){ return mRecipeRepository.isQueryExhausted(); }

    //-->
    public void searchRecipesApi(String query, int pageNumber){
        mIsViewingRecipes = true;
        mIsPerformingQuery = true;
        mRecipeRepository.searchRecipesApi(query,pageNumber);
    }

    public void searchNextPage(){
        if(!mIsPerformingQuery && mIsViewingRecipes && !isQueryExhausted().getValue()){
            // search the next page
            mRecipeRepository.searchNextPage();
        }
    }

    public boolean isViewingRecipes(){
        return mIsViewingRecipes;
    }

    public void setmIsViewingRecipes(boolean isViewingRecipes){ mIsViewingRecipes = isViewingRecipes; }

    public void setIsPerformingQuery(Boolean isPerformingQuery){ mIsPerformingQuery = isPerformingQuery; }

    public boolean isPerformingQuery(){ return mIsPerformingQuery; }

    public boolean onBackPressed(){
        if(mIsPerformingQuery){
            //cancel the query
            mRecipeRepository.cancelRequest();
            mIsPerformingQuery = false;
        }
        if(mIsViewingRecipes){
            mIsViewingRecipes = false;
            return false;
        }
        return true;
    }

}
