package com.bookies.teachnet

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.content.ContextCompat

class Creator(val context: Context)  {
    val multipleChoiceQuestions = mutableMapOf<RadioGroup, MutableList<View>>()

    fun createMultipleChoice(options: Int = 4):LinearLayout{
        Creator.Utils.totalQuestion+= 1
        val multipleChoiceView=LinearLayout(context)
        val deleteButton=ImageButton(context)
        val optionsArray= mutableListOf<MultipleOption>()
        val questionEditText=EditText(context)
        setUpLinearLayout(multipleChoiceView,LinearLayout.VERTICAL)
        setUpEditText(editText=questionEditText,lines=3,type = "question",totalQuestion = Creator.Utils.totalQuestion)
        setUpDeleteButton(multipleChoiceView,deleteButton,context)
        multipleChoiceView.addView(questionEditText)
        for(i in 1..options){
            val radioButton=RadioButton(context)
            val editText=EditText(context)
            val multipleOption=MultipleOption(radioButton,editText)
            setUpRadioButton(radioButton )
            setUpEditText(editText,2,Creator.Utils.totalQuestion)
            optionsArray.add(multipleOption)
            multipleChoiceView.addView(radioButton)
            multipleChoiceView.addView(editText)
            Log.d("IN THE OPTION LOOP","${optionsArray} is the array and ${radioButton} radio and ${editText}")
        }
        return  multipleChoiceView
    }
    fun createTrueAndFalse():LinearLayout{
        Creator.Utils.totalQuestion += 1
        val trueAndFalseView=LinearLayout(context)
        val questionEditText=EditText(context);
        val deleteButton=ImageButton(context)
        val trueRadioButton=RadioButton(context)
        val falseRadioButton=RadioButton(context);
        val trueAndFalseRadioGroup=RadioGroup(context)
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
        Creator.Utils.totalQuestion += 1
        val questionEditText=EditText(context)
        val answerEditText=EditText(context)
        val essayView=LinearLayout(context)
        val deleteButton=ImageButton(context)
        setUpEditText(editText=questionEditText,lines=3,type = "question",totalQuestion = Creator.Utils.totalQuestion)
        setUpEditText(answerEditText,2,Creator.Utils.totalQuestion)
        setUpLinearLayout(essayView,LinearLayout.VERTICAL)
        setUpDeleteButton(essayView,deleteButton,context)
        essayView.addView(questionEditText)
        essayView.addView(answerEditText)
        return essayView
    }
    companion object Utils:View.OnClickListener{
        var  totalQuestion=0;
        var layout:LinearLayout?=null;
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
            else{
                editText.hint="Answer of Question ${totalQuestion}"
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
        override fun onClick(v: View?) {
            if (v != null) {
                Log.d("onclick button", "onClick is clicked inside the null check")
                layout?.removeView(v.parent as View)
            }
        }

    }
    class MultipleOption(val radioButton: RadioButton,val editText: EditText)

}