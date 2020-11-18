package ro.ubbcluj.cs.ilazar.mycarapplication196

data class Car(
    val id: String,
    var text: String,
    var date: String,
    var version:Number,
    var edited:Boolean
) {
    override fun toString(): String = "$text $date Version: $version Edited: $edited"
}
