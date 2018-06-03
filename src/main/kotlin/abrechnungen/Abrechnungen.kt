package abrechnungen

import java.math.BigDecimal

typealias GewinneVerluste = BigDecimal
typealias Gutschrift = BigDecimal
typealias Abrechnungen = List<Abrechnung>
typealias Stück = Int
typealias WertpapierBezeichnung = String
typealias Ausführungskurs = BigDecimal
typealias Datum = Any

sealed class Abrechnung(open val datum: Datum, open val wertpapierBezeichnung: WertpapierBezeichnung)

data class Kauf(
        override val datum: Datum,
        override val wertpapierBezeichnung: WertpapierBezeichnung,
        val stück: Stück,
        val ausführungskurs: Ausführungskurs
): Abrechnung(datum, wertpapierBezeichnung)

data class Verkauf(
        override val datum: Datum,
        override val wertpapierBezeichnung: WertpapierBezeichnung,
        val stück: Stück,
        val ausführungskurs: Ausführungskurs
): Abrechnung(datum, wertpapierBezeichnung)

data class Dividenden(
        override val datum: Datum,
        override val wertpapierBezeichnung: WertpapierBezeichnung,
        val gutschrift: Gutschrift
): Abrechnung(datum, wertpapierBezeichnung)

fun berechneGewinneVerluste(abrechnungen: Abrechnungen) : GewinneVerluste {
    var gewinneVerluste = GewinneVerluste(0)
    abrechnungen.forEach { abrechnung ->
        gewinneVerluste += when(abrechnung) {
            is Verkauf -> abrechnung.stück.toBigDecimal().times(abrechnung.ausführungskurs)
            is Kauf -> -1.times(abrechnung.stück).toBigDecimal().times(abrechnung.ausführungskurs)
            is Dividenden -> abrechnung.gutschrift
        }
    }
    return gewinneVerluste
}

