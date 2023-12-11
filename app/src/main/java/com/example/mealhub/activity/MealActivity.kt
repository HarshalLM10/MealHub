package com.example.mealhub.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.mealhub.R
import com.example.mealhub.databinding.ActivityMealBinding
import com.example.mealhub.home_Fragment
import com.example.mealhub.pojo.Meal
import com.example.mealhub.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private  lateinit var mealMvvm:MealViewModel
    private lateinit var youtubeLink:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]


        getMealInformationFromIntent()
        setInformationInViews()

        loadingBar()

        mealMvvm.getMealDetail(mealId)
        observerMealDeatailsLiveData()

        onYoutubeImgClick()
    }

    private fun onYoutubeImgClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDeatailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {

                onResponseBar()
                val meal = value

                binding.tvCategories.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstructionSteps.text = meal.strInstructions

                youtubeLink = meal.strYoutube
            }


        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(home_Fragment.MEAL_ID)!!
        mealName = intent.getStringExtra(home_Fragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(home_Fragment.MEAL_THUMB)!!
    }

    private fun loadingBar(){
        binding.progressbar.visibility = View.VISIBLE
        binding.btnAddFavourite.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvCategories.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }
    private  fun  onResponseBar(){
        binding.progressbar.visibility = View.INVISIBLE
        binding.btnAddFavourite.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvCategories.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}