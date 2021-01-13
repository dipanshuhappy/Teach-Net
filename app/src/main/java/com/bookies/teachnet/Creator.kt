package com.bookies.teachnet

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.content.ContextCompat

class Creator(val context: Context)  {
    fun createMultipleChoice(options: Int = 4):LinearLayout{
        val multipleChoiceView=LinearLayout(context)
        val deleteButton=ImageButton(context)
        val questionEditText=EditText(context)

        val linearLayoutID="${totalQuestion}${multipleChoiceQuestionType}".toInt()
        val questionEditTextID="${linearLayoutID}${questionId}".toInt()
        multipleChoiceView.id=linearLayoutID
        questionEditText.id=questionEditTextID
        setUpLinearLayout(multipleChoiceView,LinearLayout.VERTICAL)
        setUpEditText(editText=questionEditText,lines=3,type = "question",totalQuestion = Creator.Utils.totalQuestion)
        setUpDeleteButton(multipleChoiceView,deleteButton,context)
        multipleChoiceView.addView(questionEditText)
        createOption(context,options,multipleChoiceView,questionEditTextID)
        return  multipleChoiceView
    }
    fun createTrueAndFalse():LinearLayout{
        val trueAndFalseView=LinearLayout(context)
        val questionEditText=EditText(context);
        val deleteButton=ImageButton(context)
        val trueRadioButton=RadioButton(context)
        val falseRadioButton=RadioButton(context);
        val trueAndFalseRadioGroup=RadioGroup(context)
        totalQuestion += 1
        val linearLayoutID="${totalQuestion}${trueAndFalseQuestionType}".toInt()
        val questionEditTextID="${linearLayoutID}${questionId}".toInt()
        trueAndFalseView.id=linearLayoutID
        questionEditText.id=questionEditTextID
        trueRadioButton.id="${questionEditTextID}${trueRadioButtonId}".toInt()
        falseRadioButton.id="${questionEditTextID}${falseRadioButtonId}".toInt()
        setUpEditText(editText=questionEditText,lines=3,type = "question",totalQuestion = Creator.Utils.totalQuestion)
        setUpRadioButton(trueRadioButton,"True")
        setUpRadioButton(falseRadioButton,"False")
        setUpButtonGroup(trueAndFalseRadioGroup,RadioGroup.HORIZONTAL)
        setUpLinearLayout(trueAndFalseView,LinearLayout.VERTICAL)
        setUpDeleteButton(trueAndFalseView,deleteButton,context)
        trueAndFalseView.addView(questionEditText)
        trueAndFalseRadioGroup.addView(trueRadioButton)
        trueAndFalseRadioGroup.addView(falseRadioButton)
        trueAndFalseView.addView(trueAndFalseRadioGroup)
        return trueAndFalseView;
    }
    fun createFillInTheBlank(){
    }
    fun createEssay():LinearLayout{
        val questionEditText=EditText(context)
        val answerEditText=EditText(context)
        val essayView=LinearLayout(context)
        val deleteButton=ImageButton(context)
        totalQuestion += 1
        val linearLayoutID="${totalQuestion}${essayQuestionType}".toInt()
        val questionEditTextID="${linearLayoutID}${questionId}".toInt()
        val answerEditTextID="${questionEditTextID}${essayAnswerType}".toInt()
        essayView.id=linearLayoutID
        questionEditText.id=questionEditTextID
        answerEditText.id=answerEditTextID
        setUpEditText(editText=questionEditText,lines=3,type = "question",totalQuestion = Creator.Utils.totalQuestion)
        setUpEditText(answerEditText,2,Creator.Utils.totalQuestion)
        setUpLinearLayout(essayView,LinearLayout.VERTICAL)
        setUpDeleteButton(essayView,deleteButton,context)
        essayView.addView(questionEditText)
        essayView.addView(answerEditText)
        return essayView
    }
    fun createImageQuestion(imageQuesition: Uri,type:String):LinearLayout{
        val imageQuestionView=LinearLayout(context);
        val sideNoteEditText=EditText(context);
        val imageView=ImageView(context);
        val deleteButton=ImageButton(context)
        totalQuestion += 1
        val linearLayoutID="${totalQuestion}${essayQuestionType}".toInt()
        val questionImageID="${linearLayoutID}${imageQuestionId}".toInt()
        imageQuestionView.id=linearLayoutID
        imageView.id=questionImageID
        sideNoteEditText.id="${linearLayoutID}${SideNoteId}".toInt()

        setUpImageQuestion(imageView,imageQuesition)
        setUpEditText(editText = sideNoteEditText,lines = 3,totalQuestion = totalQuestion,type = "sideNote")

        setUpDeleteButton(imageQuestionView,deleteButton,context)
        setUpLinearLayout(imageQuestionView,LinearLayout.VERTICAL)
        imageQuestionView.addView(deleteButton)
        imageQuestionView.addView(imageView)
        imageQuestionView.addView(sideNoteEditText)
        if(type=="multipleChoice"){
            createOption(context,4,imageQuestionView,questionImageID)
        }
        else if(type=="essay"){
            val answerEditText=EditText(context)
            answerEditText.id="${questionImageID}${essayAnswerType}".toInt()
            setUpEditText(answerEditText,2,Creator.Utils.totalQuestion)
            imageQuestionView.addView(answerEditText)
        }
        return imageQuestionView

    }
    companion object Utils:View.OnClickListener{
        var  totalQuestion=0;
        var layout:LinearLayout?=null;
        val multipleChoiceQuestionType:Int=1
        val trueAndFalseQuestionType:Int=2
        val essayQuestionType:Int=3
        val imageQuestionMultipleChoiceTypeQuestion: Int=4
        val imageQuestionEssayTypeQuestion: Int=5
        val questionId:Int=1
        val trueRadioButtonId:Int =7
        val falseRadioButtonId:Int=8
        val essayAnswerType:Int=6
        val imageQuestionId:Int=2
        val SideNoteId=666666
        fun setUpEditText(editText:EditText,lines:Int,totalQuestion:Int,inputType: Int=InputType.TYPE_TEXT_FLAG_MULTI_LINE,margin:Int=5,type:String="answer"){
             val optionsEditTextParams=LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT
            )
            optionsEditTextParams.setMargins(margin,margin,margin,margin)
            editText.layoutParams=optionsEditTextParams
            editText.inputType=inputType
            editText.imeOptions=EditorInfo.IME_FLAG_NO_ENTER_ACTION
            editText.gravity=Gravity.TOP;Gravity.LEFT
            editText.isSingleLine = false
            editText.setLines(lines)
            editText.maxLines=12
            editText.isVerticalScrollBarEnabled=true
            editText.movementMethod=ScrollingMovementMethod.getInstance();
            editText.scrollBarStyle=View.SCROLLBARS_INSIDE_INSET
            if(type == "question"){
                editText.setBackgroundResource(R.drawable.question_edit_text)
                editText.hint="Question ${totalQuestion} "
            }

            else if(type=="answer"){
                editText.hint="Answer of Question ${totalQuestion}"
            }
            else if(type=="sideNote"){
                editText.hint="Put a side note (not required)"
            }
            editText.isVerticalScrollBarEnabled=true
        }
        fun setUpRadioButton(radioButton: RadioButton,text:String=""){
            val optionsRadioButtonParams: LinearLayout.LayoutParams=LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT
            )
            if(text.isNotEmpty()){radioButton.text=text}
            radioButton.layoutParams=optionsRadioButtonParams
        }
        @SuppressLint("LongLogTag")
        fun setUpButtonGroup(radioGroup: RadioGroup,orientation:Int=RadioGroup.VERTICAL){
            val radioGroupParams:RadioGroup.LayoutParams=RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.WRAP_CONTENT
            )
            radioGroup.layoutParams=radioGroupParams
            radioGroup.orientation= orientation
            Log.d("in set button group params","${radioGroup}")
        }
        fun setUpLinearLayout(linearLayout: LinearLayout,orientation:Int){
            linearLayout.removeAllViews()

            Log.d("The LinearLayout id is",linearLayout.id.toString())
            linearLayout.layoutParams=LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT
            )
            linearLayout.orientation=orientation
        }
        fun setUpDeleteButton(view:RadioGroup,deleteButton:ImageButton,context:Context){
            deleteButton.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_delete))
            deleteButton.layoutParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            view.addView(deleteButton)
        }
        fun setUpDeleteButton(view:LinearLayout,deleteButton: ImageButton,context:Context){
            deleteButton.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_delete))
            deleteButton.layoutParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            view.addView(deleteButton)
            deleteButton.setOnClickListener(Creator.Utils)
        }
        fun setUpImageQuestion(imageView: ImageView,imageQuestion:Uri,height:Int=100){
            imageView.layoutParams=
                LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                height
                )
            imageView.setImageURI(imageQuestion)
            imageView.scaleType=ImageView.ScaleType.CENTER_INSIDE
        }
        fun createOption(context: Context,options:Int,multipleChoiceView:LinearLayout,Id: Int) {
            for(i in 1..options){
               val editTextID="${Id}${i}".toInt()
                val radioButton=RadioButton(context)
                val editText=EditText(context)
                setUpRadioButton(radioButton )
                editText.id=editTextID
                radioButton.id="${editTextID}${i}".toInt()
                setUpEditText(editText,2,Creator.Utils.totalQuestion)
                multipleChoiceView.addView(radioButton)
                multipleChoiceView.addView(editText)
                Log.d("IN THE OPTION LOOP"," ${radioButton} radio and ${editText}")
            }
        }
        override fun onClick(v: View?) {
            if (v != null) {
                Log.d("onclick button", "onClick is clicked inside the null check")
                layout?.removeView(v.parent as View)
            }
        }
    }
    class MultipleChoice(val context: Context,val numOfChoices:Int=4){
        var multipleChoiceView: LinearLayout = LinearLayout(context)
        var questionEditText: EditText= EditText(context)
        var deleteButton: ImageButton = ImageButton(context)
        var questionEditTextID:Int=0;
        init {
            totalQuestion+= 1
            val linearLayoutID="${totalQuestion}${multipleChoiceQuestionType}".toInt()
            questionEditTextID="${linearLayoutID}${questionId}".toInt()
            //set ID
            multipleChoiceView.id=linearLayoutID
            questionEditText.id=questionEditTextID
        }
        fun setUpQuestionViews(){
            setUpLinearLayout(multipleChoiceView,LinearLayout.VERTICAL)
            setUpEditText(editText=questionEditText,lines=3,type = "question",totalQuestion = Creator.Utils.totalQuestion)
            setUpDeleteButton(multipleChoiceView,deleteButton,context)
        }
        fun addViewsToParentView(){
            multipleChoiceView.addView(questionEditText)
            createOption(context,numOfChoices,multipleChoiceView,questionEditTextID)
        }

    }

}

