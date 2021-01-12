package com.bookies.teachnet

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class CreatorAndInterpretorActivity : AppCompatActivity() {
    var layout:LinearLayout?=null
    var questionImage:Uri?=null
    var creator:Creator?=null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creator_and_interpretor)
         creator= Creator(baseContext)
         layout=findViewById<LinearLayout>(R.id.dynamic_layout)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.creator_navigation_menu,menu
        )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
val creator=Creator(baseContext)
        Creator.Utils.layout = layout
        when(item.itemId){
                R.id.multiple_choice->{
                    val view: LinearLayout?= creator?.createMultipleChoice(4)
                    layout?.addView(view )
                    return true
                }
                R.id.true_or_false->{
                    val view:LinearLayout?=creator?.createTrueAndFalse()
                    layout?.addView(view)
                    return true
                }
                R.id.essay->{
                    val view:LinearLayout?=creator?.createEssay();
                    layout?.addView(view)
                    return true
                }
                R.id.image_question->{
                    getImage()


                }
                else->return true
            }
        return true
    }
    fun getImage(){
        val gallery= Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("this is the data", data.toString())
        if(resultCode== RESULT_OK&&requestCode==100){
           questionImage=data?.data
            val view:LinearLayout?= questionImage?.let { creator?.createImageQuestion(it) };
            layout?.addView(view)

        }
        else{
            Toast.makeText(this,"No image selected",Toast.LENGTH_SHORT).show()
        }
    }

}