package com.example.coffeehopper.presentationlayer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.coffeehopper.R
import com.example.coffeehopper.databinding.ActivityCoffeeDetailsBinding

class CoffeeDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCoffeeDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_coffee_details)
        intent.extras?.let { ext ->
            binding.coffeeHop = ext.getParcelable("coffeeHop")
        }
    }
}