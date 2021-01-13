package com.bookies.teachnet
interface Question{
    var type:String
    var answer:String
    var question:String
}
class MultipleChoice(override var question: String, override var answer: String, var choices:MutableList<String>) :Question{
    override var type: String="multipleChoice"
}
