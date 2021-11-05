package com.kabha.foodrecipes.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kabha.foodrecipes.models.Recipe;

import java.util.Arrays;
import java.util.List;

// #2 the response that will be retrieved when making a GET Search request by query
//https://forkify-api.herokuapp.com/phrases.html For available search queries
public class RecipeSearchResponse {

    @SerializedName("count")
    @Expose()
    private int count;

    @SerializedName("recipes")
    @Expose()
    private List<Recipe> recipes;

    public int getCount(){
        return count;
    }

    public List<Recipe> getRecipes(){
        return recipes;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", recipes=" + recipes +
                '}';
    }
}
