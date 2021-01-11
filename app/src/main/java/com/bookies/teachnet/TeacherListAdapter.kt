package com.bookies.teachnet

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi

class TeacherListAdapter(var teacherProps:List<TeacherProp>,private val context: Context):BaseAdapter(){
    private var layoutInflater:LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ViewHolder", "UseCompatLoadingForDrawables")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View=layoutInflater.inflate(R.layout.teacher_prop,parent,false)
        val teacherName=view.findViewById<TextView>(R.id.teacher_name)
        val subject=view.findViewById<TextView>(R.id.subject)
        val notification=view.findViewById<TextView>(R.id.notif_counter)
        val teacherPic=view.findViewById<ImageView>(R.id.teacher_pic)
        teacherName.text = teacherProps[position].name
        subject.text = teacherProps[position].subject
        notification.text=teacherProps[position].notification
        teacherPic.setImageDrawable(context.getDrawable(R.mipmap.ic_launcher_round))
        return view
    }
    override fun getItem(position: Int): Any {
        return  teacherProps[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return teacherProps.size
    }
}