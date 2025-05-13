package com.a2004256_ahmedmohamed.newsapp.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import com.a2004256_ahmedmohamed.newsapp.R
import org.w3c.dom.Text
import java.util.Locale

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_settings, container, false)

        val switchDarkMode = v.findViewById<Switch>(R.id.switchDarkMode)
        val LanguageModeEN = v.findViewById<Switch>(R.id.LanguageModeEN)
        val LanguageModeAr = v.findViewById<Switch>(R.id.LanguageModeAr)
        val aboutLayout = v.findViewById<ConstraintLayout>(R.id.aboutLayout)

        val textView = v.findViewById<TextView>(R.id.textView)
        val textView2 = v.findViewById<TextView>(R.id.textView2)
        val textView3 = v.findViewById<TextView>(R.id.textView3)
        val textView4 = v.findViewById<TextView>(R.id.about)

        val sharedPref = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("dark_mode", false)

        if (isDarkMode) {
            textView.setTextColor(resources.getColor(R.color.white))
            textView2.setTextColor(resources.getColor(R.color.white))
            textView3.setTextColor(resources.getColor(R.color.white))
            textView4.setTextColor(resources.getColor(R.color.white))
        }

        val darkModeSharedPref = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        if (darkModeSharedPref.getBoolean("dark_mode", false)) {
            switchDarkMode.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            switchDarkMode.isChecked = false
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val LanguageSharedPref = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        if (LanguageSharedPref.getString("language", "") == "ar") {
            LanguageModeAr.isChecked = true
            setAppLocale(requireContext(), "ar")
        } else if (LanguageSharedPref.getString("language", "") == "en") {
            LanguageModeEN.isChecked = true
            setAppLocale(requireContext(), "en")
        }

        aboutLayout.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(getString(R.string.sub_about_app))
            builder.setMessage(getString(R.string.about))
            builder.setNegativeButton(getString(R.string.close)) { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            toggleDarkMode(isChecked)
            val sharedPref = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
            sharedPref.edit().putBoolean("dark_mode", isChecked).apply()
            restartActivity(requireActivity())
        }

        LanguageModeAr.setOnCheckedChangeListener { _, isChecked ->
            val newLang = if (isChecked) "ar" else "en"
            setAppLocale(requireContext(), newLang)
            restartActivity(requireActivity())
        }

        LanguageModeEN.setOnCheckedChangeListener { _, isChecked ->
            val newLang = if (isChecked) "en" else "ar"
            setAppLocale(requireContext(), newLang)
            restartActivity(requireActivity())
        }

        return v
    }

    fun toggleDarkMode(enableDarkMode: Boolean) {
        if (enableDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun setAppLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        val sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        sharedPref.edit().putString("language", languageCode).apply()
    }

    private fun restartActivity(activity: Activity) {
        val intent = activity.intent
        activity.finish()
        activity.startActivity(intent)
    }

}