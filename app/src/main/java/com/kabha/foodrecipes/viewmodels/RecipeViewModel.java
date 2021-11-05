package com.kabha.foodrecipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kabha.foodrecipes.models.Recipe;
import com.kabha.foodrecipes.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;
    private String mRecipeId;
    private boolean mDidRetrievedRecipe;

    public RecipeViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
        mDidRetrievedRecipe = false;
    }

    public LiveData<Recipe> getRecipe(){ return mRecipeRepository.getRecipe(); }

    public LiveData<Boolean> isRecipeRequestTimeout(){ return mRecipeRepository.isRecipeRequestTimeout(); }

    public void searchRecipeById(String recipeId ){
        mRecipeId = recipeId;
        mRecipeRepository.searchRecipeById(recipeId);
    }

    public String getRecipeId() {
        return mRecipeId;
    }

    public void setRetrievedRecipe(boolean retrievedRecipe){
        mDidRetrievedRecipe = retrievedRecipe;
    }

    public boolean didRetrievedRecipe(){
        return mDidRetrievedRecipe;
    }
}
