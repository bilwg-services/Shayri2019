package com.bilwg.app.shayri2019

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Window
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private val titles = ArrayList<String>()
    private val ids = ArrayList<String>()

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(titles, this)
        adapter.listner = object : MainAdapter.onCardClick {
            override fun onClick(position: Int) {
                val intent = Intent(this@MainActivity, TemplateActivity::class.java)
                intent.putExtra("ID", ids[position])
                intent.putExtra("Name",titles[position])
                startActivity(intent)
            }
        }

        val recyclerView: RecyclerView = findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        FirebaseFirestore.getInstance().collection("Category").get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (doc in it.result!!) {
                    ids.add(doc.id)
                    titles.add(doc.getString("Name")!!)
                }
                adapter.notifyDataSetChanged()
            } else {
                AlertDialog.Builder(this).setTitle("Error").setMessage(it.exception!!.localizedMessage).create().show()
            }
        }

    }
}
