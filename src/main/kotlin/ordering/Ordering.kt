package ordering

import arrow.core.Either
import arrow.data.*
import java.math.BigDecimal

sealed class ProductCode

data class WidgetCode private constructor (val code: String) : ProductCode() {

    companion object {
        operator fun invoke(code: String) : Either<Error, WidgetCode> {
            return if("W\\d{4}".toRegex().matches(code)) {
                Either.Right(WidgetCode(code))
            } else {
                Either.Left(Error("WidgetCode must start with the letter W, followed by exactly 4 digits"))
            }
        }
    }
}



data class GizmoCode private constructor (val code: String) : ProductCode() {
    companion object {
        operator fun invoke(code: String) : Either<Error, GizmoCode> {
            return if("G\\d{3}".toRegex().matches(code)) {
                Either.Right(GizmoCode(code))
            } else {
                Either.Left(Error("GizmoCode must start with the letter G, followed by exactly 3 digits"))
            }
        }
    }
}

sealed class OrderQuantity

data class UnitQuantity private constructor (val quantity: Integer) : OrderQuantity() {
    companion object {
        operator fun invoke(quantity: Integer) : Either<Error, UnitQuantity> {
            return if(quantity < 1) {
                Either.Left(Error("Unit quantity cannot be less than one"))
            } else if(quantity > 1000) {
                Either.Left(Error("Unit quantity cannot be greater than one thousand"))
            } else {
                Either.Right(UnitQuantity(quantity))
            }
        }
    }
}

data class KilogramQuantity private constructor (val quantity: BigDecimal) : OrderQuantity() {
    companion object {
        operator fun invoke(quantity: BigDecimal) : Either<Error, KilogramQuantity> {
            return if(quantity < BigDecimal(0.05)) {
                Either.Left(Error("Kilogram quantity cannot be less than 0.05"))
            } else if(quantity > BigDecimal(1000)) {
                Either.Left(Error("Kilogram quantity cannot be greater than 1000.00"))
            } else {
                Either.Right(KilogramQuantity(quantity))
            }
        }
    }
}


typealias OrderId = Any
typealias OrderLineId = Any
typealias CustomerId = Any

typealias CustomerInfo = Any
typealias ShippingAddress = Any
typealias BillingAddress = Any
typealias Price = Any
typealias BillingAmount = Any

data class Order private constructor (
        val Id : OrderId, val CustomerInfo : CustomerInfo, val ShippingAddress : ShippingAddress,
        val BillingAddress : BillingAddress, val OrderLines : NonEmptyList<OrderLine>, val AmountToBill: BillingAmount)

data class OrderLine private constructor (
        val Id : OrderLineId, val OrderId : OrderId, val ProductCode : ProductCode, val OrderQuantity : OrderQuantity,
        val Price : Price)

sealed class PlaceOrderEvents

class AcknowledgementSent : PlaceOrderEvents()
class OrderPlaced : PlaceOrderEvents()
class BillableOrderPlaced : PlaceOrderEvents()

sealed class PlaceOrderError
data class ValidationErrorList(val errors: NonEmptyList<ValidationError>) : PlaceOrderError()

data class ValidationError private constructor ( val fieldName : String, val errorDescription : String)

data class UnvalidatedOrderLine private constructor (val productCode : ProductCode, val orderQuantity: OrderQuantity)

data class UnvalidatedOrder private constructor(
        val orderId : OrderId,
        val customerInfo : CustomerInfo,
        val shippingAddress : ShippingAddress,
        val orderLines : NonEmptyList<UnvalidatedOrderLine>
)

typealias ValidatedOrder = Any

typealias ValidationResponse<T> = Either<NonEmptyList<ValidationError>,T>

typealias ValidateOrder = (UnvalidatedOrder) -> ValidationResponse<ValidatedOrder>
typealias PlaceOrder = (UnvalidatedOrder) -> Either<PlaceOrderEvents, PlaceOrderError>

sealed class CategorizedMail
class QuoteForm(): CategorizedMail()
class OrderForm(): CategorizedMail()

typealias ProductCatalog = Any
typealias PricedOrder = Any

typealias CalculatePrices = ((OrderForm) -> ProductCatalog) -> PricedOrder

typealias InvoiceId = Any
sealed class Invoice

data class PaidInvoice(val invoiceId: InvoiceId): Invoice()
data class UnpaidInvoice(val invoiceId: InvoiceId): Invoice()

fun main(args:Array<String>) {
    val validWidgetCode = WidgetCode("W2342")
    val invalidWidgetCode = WidgetCode("ZZ234")

    val validGizmoCode = GizmoCode("G342")
    val invalidGizmoCode = GizmoCode("W3234")

}