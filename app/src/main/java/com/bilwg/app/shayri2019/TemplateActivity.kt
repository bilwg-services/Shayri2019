package com.bilwg.app.shayri2019

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Intent
import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast


class TemplateActivity : AppCompatActivity() {

    private val sayries = ArrayList<String>()
    private var currentSayri = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_template)

        findViewById<ImageButton>(R.id.imageButton4).setOnClickListener {
            val shareBody = sayries[currentSayri]
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share..."))
        }

        findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
            val shareBody = sayries[currentSayri]
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", shareBody)
            clipboard.primaryClip = clip
            Toast.makeText(this,"Text copied to clipboard successfully.",Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.imageButton3).setOnClickListener {
            val shareBody = sayries[currentSayri]
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.setPackage("com.whatsapp")
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            try {
                startActivity(whatsappIntent)
            } catch (ex: android.content.ActivityNotFoundException) {
                Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_LONG).show()
            }
        }

        val id = intent.getStringExtra("ID")
        
        findViewById<TextView>(R.id.textView).text = "${intent.getStringExtra("Name")} Shayri"
        
        FirebaseFirestore.getInstance().collection("Category").document(id).collection("Shayri").get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (doc in it.result!!) {
                    sayries.add(doc.getString("Text")!!)
                }
                addSayri()
            } else {
                AlertDialog.Builder(this).setTitle("Error").setMessage(it.exception!!.localizedMessage).create().show()
            }
        }

    }

    private fun addSayri() {
        val sayriview = findViewById<TextView>(R.id.ShayariView)
        if (currentSayri < sayries.size) {
            sayriview.text = sayries[currentSayri]
        }

        findViewById<ImageButton>(R.id.imageButton5).setOnClickListener {
            if (currentSayri != 0) {
                currentSayri -= 1
                addSayri()
            }
        }

        findViewById<ImageButton>(R.id.imageButton6).setOnClickListener {
            if (currentSayri != sayries.size - 1) {
                currentSayri += 1
                addSayri()
            }
        }

    }
}
