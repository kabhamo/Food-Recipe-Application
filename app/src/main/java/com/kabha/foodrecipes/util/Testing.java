package com.kabha.foodrecipes.util;

import android.util.Log;

import com.kabha.foodrecipes.models.Recipe;

import java.util.List;

public class Testing {

    public static void printRecipes(List<Recipe> list, String tag){
        for(Recipe recipe: list){
            Log.v(tag,"onChanges: " + recipe.getTitle());
        }
    }
}
