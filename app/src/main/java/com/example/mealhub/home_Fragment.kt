package com.example.mealhub

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mealhub.activity.MealActivity
import com.example.mealhub.adapters.CategoriesAdapter
import com.example.mealhub.adapters.MostPopularMealAdapter
import com.example.mealhub.databinding.FragmentHomeBinding
import com.example.mealhub.pojo.Meal
import com.example.mealhub.pojo.MealsByCategory
import com.example.mealhub.viewModel.HomeViewModel


class home_Fragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularMealAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.example.mealhub.idMeal"
        const val MEAL_NAME = "com.example.mealhub.nameMeal"
        const val MEAL_THUMB = "com.example.mealhub.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]

        popularItemAdapter = MostPopularMealAdapter()

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemRecyclerView()
        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopulatItems()
        observePopularItemLiveData()

        onPopularItemClick()

        prepareCategoriesRecyclerview()

        homeMvvm.getCategories()
        observeCategoriesLiveData()


        }

    private fun prepareCategoriesRecyclerview() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
    }


}

    private fun observeCategoriesLiveData() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories->

            categoriesAdapter.setCategoryList(categories)


        }
        )
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = { meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemAdapter

        }
    }

    private fun observePopularItemLiveData() {
        homeMvvm.observePopularItemLiveData().observe(viewLifecycleOwner
        ) { mealList->
            popularItemAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner,
            { meal ->
                Glide.with(this@home_Fragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.randomMeal)

                this.randomMeal = meal


            })
    }
}

