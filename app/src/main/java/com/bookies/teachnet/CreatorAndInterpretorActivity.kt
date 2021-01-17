package com.bookies.teachnet

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import java.io.ByteArrayOutputStream


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
        Creator.Utils.layout = layout
        when(item.itemId){
                R.id.multiple_choice->{
                    val m: Creator.MultipleChoice? = creator?.createMultipleChoice(4)
                    layout?.addView(m?.multipleChoiceView)
                    m?.multipleChoiceView?.let { allQuestionsList.add(it) }
                    return true
                }
                R.id.true_or_false->{
                    val m: Creator.TrueAndFalse?= creator?.createTrueAndFalse()
                    layout?.addView(m?.trueAndFalseView)
                    m?.trueAndFalseView?.let { allQuestionsList.add(it) }
                    return true
                }
                R.id.essay->{
                    val m: Creator.Essay?= creator?.createEssay()
                    layout?.addView(m?.essayView)
                    m?.essayView?.let { allQuestionsList.add(it) }
                    return true
                }
                R.id.image_question_multiple_choice->{
                   typeOfQuestion="multipleChoice"
                    getImage()
                    return true
                }
            R.id.image_question_essay->{
                typeOfQuestion="essay"
                getImage()
                return true
            }
            R.id.send->{
                getQuestionsText();
                getAnswerText();
            }
                else->return true
            }
        return true
    }
    fun getImage(){
        val gallery= Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery,100)
    }
    @SuppressLint("LongLogTag")
    fun getQuestionsText(){
        Log.d("All questions are", allQuestionsList.toString())
        allQuestionsList.forEach {
                Log.d("the id of layout","${it.id}")
            Log.d("the id of the type of question",  "${it.id.toString().last().toString().toInt()}")

            if(
                it.id.toString().last().toString().toInt()==Creator.ID["imageQuestionMultipleChoiceTypeQuestionId"]
                ||
                it.id.toString().last().toString().toInt()==Creator.ID["imageQuestionEssayTypeQuestionId"]
            ){

            }
            else{
              val editText=  it[1] as EditText
                Log.d("the editTEXT IS ","$editText")
                Log.d("The questions are ","${editText.id} is id and text is ${editText.text}")
           }
            }
        }
    @SuppressLint("LongLogTag")
    fun getAnswerText(){

        allQuestionsList.forEach {
         val questionId=   "${it.id}".last()
            Log.d("LAST INDEX IS","${questionId}${it.id} and ${it.id.toString().last()} this is the thrid part${questionId.toString().toInt()}")
            when(questionId.toString().toInt()){
                Creator.ID["essayQuestionTypeId"]->{
                    Log.d("answer for essay is","${(it.get(2) as EditText).text}")
                }
                Creator.ID["multipleChoiceQuestionTypeId"]->{
                    Log.d("muliple choice","${it.get(2)} is radio group")
                }
                Creator.ID["trueAndFalseQuestionTypeId"]->{
                    Log.d("muliple choice","${it.get(2)} is radio group")
                }
                Creator.ID["imageQuestionEssayTypeQuestionId"]->{
                    val imageView: ImageView=(it.get(1)) as ImageView
                    var imageString=getImageToBase64(imageView)
                    Log.d("base 64 of essay image question",imageString)
                }
            }
        }
    }
    fun getImageToBase64(imageView:ImageView):String{
        var imageBitmap:Bitmap=(imageView.drawable as BitmapDrawable).bitmap
        val bitArray=ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG,100,bitArray)
        var imageBytes=bitArray.toByteArray()
        val ImageString64=Base64.encodeToString(imageBytes,Base64.DEFAULT)
        return ImageString64
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("this is the data", data.toString())
        if(resultCode== RESULT_OK&&requestCode==100){
           questionImage=data?.data
            val m= questionImage?.let {
                creator?.createImageQuestion(
                    it,
                    typeOfQuestion)
            }
            if (m != null) {
                layout?.addView(m.imageQuestionView)
                m?.imageQuestionView?.let { allQuestionsList.add(it) }

            }
        };

        else{
            Toast.makeText(this,"No image selected",Toast.LENGTH_SHORT).show()
        }
    }
    companion object Variable{
        var typeOfQuestion:String="";
        val allQuestionsList= mutableListOf<LinearLayout>()
    }
}