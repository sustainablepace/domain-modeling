package abrechnungen

import java.math.BigDecimal

typealias GewinneVerluste = BigDecimal
typealias Gutschrift = BigDecimal
typealias Provision = BigDecimal
typealias Ausführungskurs = BigDecimal
typealias Abrechnungen = List<Abrechnung>
typealias Stück = Int
typealias WertpapierBezeichnung = String
typealias Datum = Any

sealed class Abrechnung(open val datum: Datum, open val wertpapierBezeichnung: WertpapierBezeichnung)

typealias Kurswert = BigDecimal

data class Kauf(
        override val datum: Datum,
        override val wertpapierBezeichnung: WertpapierBezeichnung,
        val stück: Stück,
        val ausführungskurs: Ausführungskurs
): Abrechnung(datum, wertpapierBezeichnung) {
    val kurswert : Kurswert = -1.times(stück).toBigDecimal().times(ausführungskurs)
    val provision: Provision = Provision(-10)
}

data class Verkauf(
        override val datum: Datum,
        override val wertpapierBezeichnung: WertpapierBezeichnung,
        val stück: Stück,
        val ausführungskurs: Ausführungskurs
): Abrechnung(datum, wertpapierBezeichnung){
    val kurswert : Kurswert = stück.toBigDecimal().times(ausführungskurs)
    val provision: Provision = Provision(-10)
}

data class Dividenden(
        override val datum: Datum,
        override val wertpapierBezeichnung: WertpapierBezeichnung,
        val gutschrift: Gutschrift
): Abrechnung(datum, wertpapierBezeichnung)

fun berechneGewinneVerluste(abrechnungen: Abrechnungen) : GewinneVerluste {
    return abrechnungen.fold(GewinneVerluste(0) ) { gewinneVerluste, abrechnung ->
        gewinneVerluste + when(abrechnung) {
            is Verkauf -> abrechnung.kurswert + abrechnung.provision
            is Kauf -> abrechnung.kurswert + abrechnung.provision
            is Dividenden -> abrechnung.gutschrift
        }
    }
}

