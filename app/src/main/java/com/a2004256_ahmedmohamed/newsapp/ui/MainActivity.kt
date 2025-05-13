package com.a2004256_ahmedmohamed.newsapp.ui

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.a2004256_ahmedmohamed.newsapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var nameScreen: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        loadLocale()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isInternetAvailable(this)) {

        } else {
            Toast.makeText(this, "يرجى الاتصال بالإنترنت", Toast.LENGTH_SHORT).show()
        }

        val sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        getCurrentLanguage(this)

        nameScreen = findViewById(R.id.nameScreen)

        bottomNavigationView = findViewById(R.id.btnNav)

        if (isDarkMode) {
            bottomNavigationView.setBackgroundColor(resources.getColor(R.color.black))
            bottomNavigationView.setItemIconTintList(resources.getColorStateList(R.color.white))
            bottomNavigationView.setItemTextColor(resources.getColorStateList(R.color.white))
            nameScreen.setTextColor(resources.getColor(R.color.white))
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    nameScreen.text = getString(R.string.home)
                    true
                }
                R.id.search -> {
                    replaceFragment(SearchFragment())
                    nameScreen.text = getString(R.string.search)
                    true
                }
                R.id.star -> {
                    replaceFragment(StarFragment())
                    nameScreen.text = getString(R.string.favorite)
                    true
                }
                R.id.settings -> {
                    replaceFragment(SettingsFragment())
                    nameScreen.text = getString(R.string.settings)
                    true
                }
                else -> false
            }
        }
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            nameScreen.text = getString(R.string.home)
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun getCurrentLanguage(context: Context): String {
        val sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return sharedPref.getString("language", Locale.getDefault().language) ?: "en"
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.closeApp))
        builder.setMessage(getString(R.string.messageCloseApp))
        builder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }
        builder.setPositiveButton(getString(R.string.okay)) { dialog, _ ->
            dialog.dismiss()
            finishAffinity()
            super.onBackPressed()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun loadLocale() {
        val sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val languageCode = sharedPref.getString("language", Locale.getDefault().language) ?: "en"
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}