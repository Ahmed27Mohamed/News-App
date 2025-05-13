package com.a2004256_ahmedmohamed.newsapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.a2004256_ahmedmohamed.newsapp.R
import com.a2004256_ahmedmohamed.newsapp.adapter.OnboardingAdapter
import com.a2004256_ahmedmohamed.newsapp.model.OnBoardingModel

class OnBoardingActivity : AppCompatActivity() {
        private lateinit var viewPager: ViewPager2
        private lateinit var btnNext: Button
        private lateinit var btnSkip: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val sharedPref = getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            if (sharedPref.getBoolean("finished", false)) {
                goToMain()
                return
            }

            setContentView(R.layout.activity_on_boarding)

            val onboardingItems = listOf(
                OnBoardingModel(getString(R.string.welcome_to_our_app),
                    getString(R.string.get_ready_to_discover_a_unique_and_distinctive_experience_that_helps_you_access_what_you_need_easily_and_quickly), R.drawable.img1),
                OnBoardingModel(getString(R.string.simple_and_easy_to_use_design),
                    getString(R.string.we_ve_carefully_designed_the_interface_to_be_convenient_simple_and_seamless_every_step_of_your_experience_with_us), R.drawable.img2),
                OnBoardingModel(getString(R.string.ready_to_go),
                    getString(R.string.let_s_get_started_now_everything_you_need_is_in_one_place_click_get_started_and_enjoy_the_service), R.drawable.img3)
            )

            viewPager = findViewById(R.id.onboardingViewPager)
            btnNext = findViewById(R.id.btnNext)
            btnSkip = findViewById(R.id.btnSkip)

            viewPager.adapter = OnboardingAdapter(onboardingItems)

            btnNext.setOnClickListener {
                val currentItem = viewPager.currentItem
                if (currentItem < onboardingItems.size - 1) {
                    viewPager.currentItem = currentItem + 1
                } else {
                    finishOnboarding()
                }
            }

            btnSkip.setOnClickListener {
                finishOnboarding()
            }

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == onboardingItems.lastIndex) {
                        btnNext.text = getString(R.string.start)
                        btnSkip.visibility = View.GONE
                    } else {
                        btnNext.text = getString(R.string.next)
                        btnSkip.visibility = View.VISIBLE
                    }
                }
            })
        }

        private fun finishOnboarding() {
            getSharedPreferences("onboarding", Context.MODE_PRIVATE).edit().putBoolean("finished", true).apply()
            goToMain()
        }

        private fun goToMain() {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }