package com.example.coffeehopper.uilayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeehopper.CoffeeHopperApplication
import com.example.coffeehopper.R
import com.example.coffeehopper.networklayer.CoffeeHopperServices
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}