package com.bilwg.app.shayri2019

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class MainAdapter(private val titles: ArrayList<String>, private val context: Context) : RecyclerView.Adapter<MainViewHolder>() {

    private var i = 1

    var listner:onCardClick? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.card_main, p0, false))
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(p0: MainViewHolder, p1: Int) {
        p0.textView.text = titles[p1]
        if (i == 15) {
            i = 1
        }
        p0.imageView.setImageDrawable(context.resources.getDrawable(context.resources.getIdentifier("@drawable/a$i", null, context.packageName)))
        i++

        p0.imageView.setOnClickListener {
            listner!!.onClick(p1)
        }
    }

    interface onCardClick {
        fun onClick(position: Int)
    }
}

class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView = view.findViewById<TextView>(R.id.cardMainText)!!
    val imageView = view.findViewById<ImageView>(R.id.cardMainImage)!!
}