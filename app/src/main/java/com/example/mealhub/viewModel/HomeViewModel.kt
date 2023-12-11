package com.example.mealhub.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealhub.pojo.Category
import com.example.mealhub.pojo.CategoryList
import com.example.mealhub.pojo.Meal
import com.example.mealhub.pojo.MealList
import com.example.mealhub.pojo.MealsByCategory
import com.example.mealhub.pojo.MealsByCategoryList
import com.example.mealhub.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {

    private var randomMealLiveData =  MutableLiveData<Meal>()
    private var popularItemLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : retrofit2.Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal

                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("home_Fragment",t.message.toString())
            }
        } )
    }

    fun getPopulatItems(){
        RetrofitInstance.api.getPopularItem("Seafood").enqueue(object  : Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if(response.body() != null){
                    popularItemLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }

        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               response.body()?.let { categoryList ->
               categoriesLiveData.postValue(categoryList.categories)

               }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeVieModel",t.message.toString())
            }

        })
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun  observePopularItemLiveData():LiveData<List<MealsByCategory>>{
        return popularItemLiveData
    }

    fun  observeCategoriesLiveData():LiveData<List<Category>>{
        return categoriesLiveData
    }

}