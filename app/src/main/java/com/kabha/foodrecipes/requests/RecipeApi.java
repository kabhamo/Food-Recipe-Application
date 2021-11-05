package com.kabha.foodrecipes.requests;

import com.kabha.foodrecipes.requests.responses.RecipeResponse;
import com.kabha.foodrecipes.requests.responses.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {

    //? SEARCH getter request
    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
          @Query("q") String query,
          @Query("page") String page
    );

    //? GET RECIPE REQUEST
    @GET("api/get")
    Call<RecipeResponse> getRecipe(
            @Query("rId") String recipe_id
    );

}
