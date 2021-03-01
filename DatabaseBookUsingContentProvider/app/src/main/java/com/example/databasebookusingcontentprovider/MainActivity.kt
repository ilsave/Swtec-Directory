package com.example.databasebookusingcontentprovider

import android.content.ContentValues
import android.content.Intent
import android.content.UriMatcher
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ProviderWorker {

    companion object {
        const val AUTHORITY = "com.swtecnn.contentproviderlesson.DbContentProvider"
        private val DIARY_ENTRY_TABLE = "DiaryEntry"
        val DIARY_TABLE_CONTENT_URI: Uri = Uri.parse("content://" +
                AUTHORITY + "/" + DIARY_ENTRY_TABLE)
    }

    private val DIARY_ENTRIES = "#"
    private val DIARY_ENTRY_ID = 2
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)


    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = AdapterContentProvider(this, list)
//        (recyclerView.adapter as AdapterContentProvider).notifyDataSetChanged()

        recyclerView.adapter = AdapterContentProvider(this, getDbFromLesson())
        (recyclerView.adapter as AdapterContentProvider).notifyDataSetChanged()

    }

    override fun deleteItem(id: Long) {
        val uri = Uri.parse("content://" +
                AUTHORITY + "/" + DIARY_ENTRY_TABLE + "/" + id)
        Log.d("Ilsave", uri.toString())
        val listCursor = contentResolver.delete(
            uri, null, null)
        recyclerView.adapter = AdapterContentProvider(this, getDbFromLesson())
        (recyclerView.adapter as AdapterContentProvider).notifyDataSetChanged()
    }

    override fun updateItem(item: Item) {
        val intent = Intent(this, UpdateActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("item", item)
        intent.putExtras(bundle)
        startActivityForResult(intent,1)
    }

    private fun getDbFromLesson(): List<Item>{
        val contactList = ArrayList<Item>()
        Log.d("Ilsave", DIARY_TABLE_CONTENT_URI.toString())
        val listCursor = contentResolver.query(
            DIARY_TABLE_CONTENT_URI, null, null, null, null)
        while (listCursor?.moveToNext()!!){
            val textEntry = listCursor.getString(listCursor.getColumnIndex("entry_text"))
            val dateText = listCursor.getString(listCursor.getColumnIndex("entry_date"))
            val dateTextId = listCursor.getString(listCursor.getColumnIndex("id"))
            Log.d("ilsave", dateTextId)
            contactList.add(Item( dateTextId.toLong(), textEntry, dateText))
        }
        return contactList
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let{
            val item = it.extras?.get("itemCustom") as Item
            val contentValues = ContentValues()
            contentValues.put("id", item.id)
            contentValues.put("entry_text", item.name)
            contentValues.put("entry_date", item.date)
            val uri = Uri.parse("content://" +
                    AUTHORITY + "/" + DIARY_ENTRY_TABLE + "/" + item.id)
            Log.d("IlsaveUri", uri.toString())
            Log.d("IlsaveContent", "${item.id} ${item.name} ${item.date} ")
            Log.d("IlsaveContents", contentValues.toString())
            contentResolver?.update(uri, contentValues, null, null)
            recyclerView.adapter = AdapterContentProvider(this, getDbFromLesson())
            (recyclerView.adapter as AdapterContentProvider).notifyDataSetChanged()
        }
    }
}