package com.ashishpatel.softwarelab.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.ashishpatel.softwarelab.R
import com.ashishpatel.softwarelab.adapter.ViewPagerAdapter
import com.ashishpatel.softwarelab.databinding.ActivityOnBoardingBinding
import com.ashishpatel.softwarelab.screens.onboarding.FirstScreenFragment
import com.ashishpatel.softwarelab.screens.onboarding.SecondFragment
import com.ashishpatel.softwarelab.screens.onboarding.ThirdFragment
import com.ashishpatel.softwarelab.utils.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onStart() {
        super.onStart()
        if (sharedPref.getToken() != null) {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
        window.statusBarColor = ContextCompat.getColor(this, R.color.tertiary)

        val fragmentList = arrayListOf(
            FirstScreenFragment(),
            SecondFragment(),
            ThirdFragment()
        )

        val viewPagerAdapter = ViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )

        binding.onBoardingViewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("OnBoardingActivity", position.toString())
                window.statusBarColor = when (position) {
                    0 -> ContextCompat.getColor(this@OnBoardingActivity, R.color.tertiary)
                    1 -> ContextCompat.getColor(this@OnBoardingActivity, R.color.primary)
                    else -> ContextCompat.getColor(this@OnBoardingActivity, R.color.secondary)
                }
            }

        })

        binding.onBoardingViewPager2.adapter = viewPagerAdapter

    }
}