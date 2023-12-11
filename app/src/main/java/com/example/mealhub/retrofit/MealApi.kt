package com.example.mealhub.retrofit

import com.example.mealhub.pojo.CategoryList
import com.example.mealhub.pojo.MealList
import com.example.mealhub.pojo.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String) : Call<MealList>

    @GET("filter.php?")
    fun getPopularItem(@Query("c") categoryName:String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>
}