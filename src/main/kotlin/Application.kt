import abrechnungen.*
import java.time.Instant
import java.util.*

fun main(args:Array<String>) {
    val abrechnungen: Abrechnungen = listOf(
            Kauf(Date.from(Instant.now()), "Atlassian", 100, Ausführungskurs(6)),
            Verkauf(Date.from(Instant.now()), "Atlassian", 50, Ausführungskurs(11)),
            Dividenden(Date.from(Instant.now()), "Atlassian", Gutschrift(80))
    )

    val depotwert = berechneGewinneVerluste(abrechnungen)

    println(depotwert.toString())
}