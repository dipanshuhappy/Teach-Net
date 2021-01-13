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

class Creator(val context: Context) {
    fun createMultipleChoice(options: Int = 4): MultipleChoice {
        val multipleChoice = MultipleChoice(context, options)
        multipleChoice.setUpQuestionViews()
        multipleChoice.addViewsToParentView()
        return multipleChoice
    }

    fun createTrueAndFalse(): TrueAndFalse {
        val trueAndFalse = TrueAndFalse(context)
        trueAndFalse.setUpQuestionViews()
        trueAndFalse.addViewsToParentView()
        return trueAndFalse;
    }

    fun createEssay(): Essay {
        val essay = Essay(context)
        essay.setUpQuestionViews()
        essay.addViewsToParentView()
        return essay;
    }

    fun createImageQuestion(image: Uri, type: String): ImageQuestion {
        val imageQuestion = ImageQuestion(context, image, type)
        imageQuestion.setUpQuestionViews()
        imageQuestion.addViewsToParentView()
        return imageQuestion;
    }

    companion object Utils : View.OnClickListener {
        var totalQuestion = 0;
        var layout: LinearLayout? = null;
        var ID = mapOf(
            "multipleChoiceQuestionTypeId" to 1,
            "trueAndFalseQuestionTypeId" to 2,
            "essayQuestionTypeId" to 3,
            "imageQuestionMultipleChoiceTypeQuestionId" to 4,
            "imageQuestionEssayTypeQuestionId" to 5,
            "questionEditTextId" to 1,
            "trueRadioButton" to 7,
            "falseRadioButtonId" to 8,
            "essayAnswerId" to 6,
            "imageQuestionId" to 2,
            "SideNoteId" to 666666
        )

        fun setUpEditText(
            editText: EditText,
            lines: Int,
            inputType: Int = InputType.TYPE_TEXT_FLAG_MULTI_LINE,
            margin: Int = 5,
            type: String = "answer"
        ) {
           _styleEditText(editText,margin,lines,inputType)
            if (type == "question") {
                editText.setBackgroundResource(R.drawable.question_edit_text)
                editText.hint = "Question $totalQuestion "
            } else if (type == "answer") {
                editText.hint = "Answer of Question $totalQuestion"
            } else if (type == "sideNote") {
                editText.hint = "Put a side note (not required)"
            }
            editText.isVerticalScrollBarEnabled = true
        }
        private fun _styleEditText(editText: EditText,margin: Int,lines: Int,inputType: Int){
            val optionsEditTextParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            optionsEditTextParams.setMargins(margin, margin, margin, margin)
            editText.layoutParams = optionsEditTextParams
            editText.inputType = inputType
            editText.imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
            editText.gravity = Gravity.TOP;Gravity.LEFT
            editText.isSingleLine = false
            editText.setLines(lines)
            editText.maxLines = 12
            editText.isVerticalScrollBarEnabled = true
            editText.movementMethod = ScrollingMovementMethod.getInstance();
            editText.scrollBarStyle = View.SCROLLBARS_INSIDE_INSET
        }

        fun setUpRadioButton(radioButton: RadioButton, text: String = "") {
            val optionsRadioButtonParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            if (text.isNotEmpty()) {
                radioButton.text = text
            }
            radioButton.layoutParams = optionsRadioButtonParams
        }

        @SuppressLint("LongLogTag")
        fun setUpButtonGroup(radioGroup: RadioGroup, orientation: Int = RadioGroup.VERTICAL) {
            val radioGroupParams: RadioGroup.LayoutParams = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT
            )
            radioGroup.layoutParams = radioGroupParams
            radioGroup.orientation = orientation
            Log.d("in set button group params", "${radioGroup}")
        }

        fun setUpLinearLayout(linearLayout: LinearLayout, orientation: Int) {
            linearLayout.removeAllViews()

            Log.d("The LinearLayout id is", linearLayout.id.toString())
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            linearLayout.orientation = orientation
        }

        //        fun setUpDeleteButton(view:RadioGroup,deleteButton:ImageButton,context:Context){
//            deleteButton.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_delete))
//            deleteButton.layoutParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
//            view.addView(deleteButton)
//        }
        fun setUpDeleteButton(view: LinearLayout, deleteButton: ImageButton, context: Context) {
            deleteButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_delete))
            deleteButton.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            view.addView(deleteButton)
            deleteButton.setOnClickListener(Creator.Utils)
        }

        fun setUpImageQuestion(imageView: ImageView, imageQuestion: Uri, height: Int = 100) {
            imageView.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    height
                )
            imageView.setImageURI(imageQuestion)
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }

        fun createOption(
            context: Context,
            options: Int,
            multipleChoiceView: LinearLayout,
            Id: Int
        ) {
            for (i in 1..options) {
                val editTextID = "${Id}${i}".toInt()
                val radioButton = RadioButton(context)
                val editText = EditText(context)
                setUpRadioButton(radioButton)
                editText.id = editTextID
                radioButton.id = "${editTextID}${i}".toInt()
                setUpEditText(editText, 2, Creator.Utils.totalQuestion)
                multipleChoiceView.addView(radioButton)
                multipleChoiceView.addView(editText)
                Log.d("IN THE OPTION LOOP", " ${radioButton} radio and ${editText}")
            }
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Log.d("onclick button", "onClick is clicked inside the null check")
                layout?.removeView(v.parent as View)
            }
        }
    }

    class MultipleChoice(val context: Context, val numOfChoices: Int = 4) {
        var multipleChoiceView: LinearLayout = LinearLayout(context)
        var questionEditText: EditText = EditText(context)
        var deleteButton: ImageButton = ImageButton(context)
        var questionEditTextID: Int = 0;

        init {
            totalQuestion += 1
            val linearLayoutID = "${totalQuestion}${ID["multipleChoiceQuestionTypeId"]}".toInt()
            questionEditTextID = "${linearLayoutID}${ID["questionEditTextId"]}".toInt()
            //set ID
            multipleChoiceView.id = linearLayoutID
            questionEditText.id = questionEditTextID
            Log.d("id in multiple choice", "${multipleChoiceView.id} and ${questionEditText.id}")
        }

        fun setUpQuestionViews() {
            setUpLinearLayout(multipleChoiceView, LinearLayout.VERTICAL)
            setUpEditText(editText = questionEditText, lines = 3, type = "question")
            setUpDeleteButton(multipleChoiceView, deleteButton, context)
        }

        fun addViewsToParentView() {
            multipleChoiceView.addView(questionEditText)
            createOption(context, numOfChoices, multipleChoiceView, questionEditTextID)
        }
    }

    class TrueAndFalse(val context: Context) {
        val trueAndFalseView = LinearLayout(context)
        val questionEditText = EditText(context);
        val deleteButton = ImageButton(context)
        val trueRadioButton = RadioButton(context)
        val falseRadioButton = RadioButton(context);
        val trueAndFalseRadioGroup = RadioGroup(context)

        init {
            totalQuestion += 1
            val linearLayoutID = "${totalQuestion}${ID["trueAndFalseQuestionTypeId"]}".toInt()
            val questionEditTextID = "${linearLayoutID}${ID["questionEditTextId"]}".toInt()
            //SET ID
            trueAndFalseView.id = linearLayoutID
            questionEditText.id = questionEditTextID
            trueRadioButton.id = "${questionEditTextID}${ID["trueRadioButtonId"]}".toInt()
            falseRadioButton.id = "${questionEditTextID}${ID["falseRadioButtonId"]}".toInt()
        }

        fun setUpQuestionViews() {
            setUpEditText(editText = questionEditText, lines = 3, type = "question")
            setUpRadioButton(trueRadioButton, "True")
            setUpRadioButton(falseRadioButton, "False")
            setUpButtonGroup(trueAndFalseRadioGroup, RadioGroup.HORIZONTAL)
            setUpLinearLayout(trueAndFalseView, LinearLayout.VERTICAL)
            setUpDeleteButton(trueAndFalseView, deleteButton, context)
        }

        fun addViewsToParentView() {
            trueAndFalseView.addView(questionEditText)
            trueAndFalseRadioGroup.addView(trueRadioButton)
            trueAndFalseRadioGroup.addView(falseRadioButton)
            trueAndFalseView.addView(trueAndFalseRadioGroup)
        }
    }

    class Essay(val context: Context) {
        val questionEditText = EditText(context)
        val answerEditText = EditText(context)
        val essayView = LinearLayout(context)
        val deleteButton = ImageButton(context)

        init {
            totalQuestion += 1
            val linearLayoutID = "${totalQuestion}${ID["essayQuestionTypeId"]}".toInt()
            val questionEditTextID = "${linearLayoutID}${ID["questionEditTextId"]}".toInt()
            val answerEditTextID = "${questionEditTextID}${ID["essayAnswerId"]}".toInt()
            //SET ID
            essayView.id = linearLayoutID
            questionEditText.id = questionEditTextID
            answerEditText.id = answerEditTextID
        }

        fun setUpQuestionViews() {
            setUpEditText(editText = questionEditText, lines = 3, type = "question")
            setUpEditText(answerEditText, 2, totalQuestion)
            setUpLinearLayout(essayView, LinearLayout.VERTICAL)
            setUpDeleteButton(essayView, deleteButton, context)
        }

        fun addViewsToParentView() {
            essayView.addView(questionEditText)
            essayView.addView(answerEditText)
        }
    }

    class ImageQuestion(val context: Context, val imageQuestion: Uri, val type: String) {
        val imageQuestionView = LinearLayout(context);
        val sideNoteEditText = EditText(context);
        val imageView = ImageView(context);
        val deleteButton = ImageButton(context)
        var questionImageID: Int = 0;

        init {
            totalQuestion += 1
            val linearLayoutID = "${totalQuestion}${ID["essayQuestionTypeId"]}".toInt()
            questionImageID = "${linearLayoutID}${ID["imageQuestionId"]}".toInt()
            //set ID
            imageQuestionView.id = linearLayoutID
            imageView.id = questionImageID
            sideNoteEditText.id = "${linearLayoutID}${ID["SideNoteId"]}".toInt()
        }

        fun setUpQuestionViews() {
            setUpImageQuestion(imageView, imageQuestion)
            setUpEditText(editText = sideNoteEditText, lines = 3, type = "sideNote")
            setUpDeleteButton(imageQuestionView, deleteButton, context)
            setUpLinearLayout(imageQuestionView, LinearLayout.VERTICAL)
        }

        fun addViewsToParentView() {
            imageQuestionView.addView(deleteButton)
            imageQuestionView.addView(imageView)
            imageQuestionView.addView(sideNoteEditText)
            if (type == "multipleChoice") {
                createOption(context, 4, imageQuestionView, questionImageID)
            } else if (type == "essay") {
                val answerEditText = EditText(context)
                answerEditText.id = "${questionImageID}${ID["essayAnswerTypeId"]}".toInt()
                setUpEditText(answerEditText, 2, Creator.Utils.totalQuestion)
                imageQuestionView.addView(answerEditText)
            }
        }
    }
}

