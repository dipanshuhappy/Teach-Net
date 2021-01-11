package com.bookies.teachnet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostViewAdapter(val posts:MutableList<Post>) :  RecyclerView.Adapter<PostViewAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title: TextView
        val description:TextView
        val linearLayout:LinearLayout
        init {
            title=view.findViewById(R.id.title)
            description=view.findViewById(R.id.description)
            linearLayout=view.findViewById(R.id.post_media)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.post,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

}