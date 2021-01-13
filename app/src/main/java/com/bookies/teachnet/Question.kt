package com.bookies.teachnet
interface Question{
    var type:String
    var answer:String
    var question:String
    var id:String
}
class MultipleChoiceQuestion(override var question: String, override var answer: String,
                     override var id: String, var choices:MutableList<String>)
    :Question{
                override var type: String="multipleChoice"
}
