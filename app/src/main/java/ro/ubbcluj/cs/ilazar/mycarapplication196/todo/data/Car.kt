package ro.ubbcluj.cs.ilazar.mycarapplication196.todo.data

data class Car(
    val _id: String,
    var title: String,
    var text:String,
    var date: String,
    var version:Int,
    var modified:Boolean
) {
    override fun toString(): String = text
}
