import arrow.core.Option

val mightContainTheMeaningOfLife: Option<Int> = Option.invoke(42)


typealias ToString = (Int) -> String

val f: (Int) -> String = { "foo" + "o".repeat(it) }

fun stringBuilder(toString: ToString, n:Int):String {
    return toString(n)
}

fun main(args : Array<String>) {
    val x:String = stringBuilder(f, 7)
    println(x)
}