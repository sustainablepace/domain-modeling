import abrechnungen.*
import java.time.Instant
import java.util.*

fun main(args:Array<String>) {
    val abrechnungen: Abrechnungen = listOf(
            Kauf(Date.from(Instant.now()), "Jetbrains", 10, Ausführungskurs(5)),
            Verkauf(Date.from(Instant.now()), "Atlassian", 5, Ausführungskurs(11)),
            Dividenden(Date.from(Instant.now()), "Google", Gutschrift(10))
    )

    val depotwert = berechneGewinneVerluste(abrechnungen)

    println(depotwert.toString())
}