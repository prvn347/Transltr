package com.example.translaterg

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions


class MainActivity : AppCompatActivity() {
  private var englishGermanTranslator: FirebaseTranslator? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        // Create an English-German translator:
        val options = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(FirebaseTranslateLanguage.EN)
            .setTargetLanguage(FirebaseTranslateLanguage.DE)
            .build()
        englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options)
        val input = findViewById<EditText>(R.id.inputText)
        val translate = findViewById<Button>(R.id.translate)
        val display = findViewById<TextView>(R.id.idTVTranslatedLanguage)
//
//
//
//
    }
//
    fun onClickTranslate(view: View){
        val input = findViewById<EditText>(R.id.inputText)
        val translate = findViewById<Button>(R.id.translate)
        val text = input.text.toString()
        downloadLang(text)

    }
    private fun downloadLang(input: String){
//        val input = findViewById<EditText>(R.id.inputText)
        val conditions = FirebaseModelDownloadConditions.Builder()
            .requireWifi()
            .build()
        englishGermanTranslator?.downloadModelIfNeeded(conditions)
            ?.addOnSuccessListener {
                // Model downloaded successfully. Okay to start translating.
                Toast.makeText(this, "Please wait language modal is being downloaded.", Toast.LENGTH_SHORT).show();
                // (Set a flag, unhide the translation UI, etc.)
                translateLanguage(input)
            }
            ?.addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                Toast.makeText(this, "Fail to download modal", Toast.LENGTH_SHORT).show();
                // ...
            }
    }
    private fun translateLanguage(input: String) {
        val display = findViewById<TextView>(R.id.idTVTranslatedLanguage)
        englishGermanTranslator!!.translate(input).addOnSuccessListener { s ->
            display.setText(
                s
            )
        }.addOnFailureListener {
            Toast.makeText(
                this@MainActivity,
                "Fail to translate",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}