package com.example.databasebookusingcontentprovider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class UpdateActivity : AppCompatActivity() {

    private lateinit var  editText: EditText
    private lateinit var  buttonUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        editText = findViewById<EditText>(R.id.editTextItem)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        val item = intent.extras?.get("item") as Item

        editText.setText(item.name)
        buttonUpdate.setOnClickListener {
            val intent = Intent()
            val bundle = Bundle()
            val customItem = Item(item.id, editText.text.toString(), item.date)
            Log.d("IlsaveCustom", customItem.toString())
            bundle.putSerializable("itemCustom", customItem)
            intent.putExtras(bundle)
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}