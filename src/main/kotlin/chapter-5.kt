sealed class ProductCode(val code: String) {
    class WidgetCode(val code: String)
    class GizmoCode(val code: String)
}

sealed class OrderQuantity {
    class UnitQuantity
    class KilogramQuantity
}

class UnvalidatedProductCode
class UnvalidatedOrderQuantity

class UnvalidatedCustomerInfo
class UnvalidatedShippingAddress
class UnvalidatedBillingAddress
class UnvalidatedOrderLine(
        val unvalidatedProductCode: UnvalidatedProductCode,
        val unvalidatedOrderQuantity: UnvalidatedOrderQuantity
)
typealias UnvalidatedOrderLines = List<UnvalidatedOrderLine>

class UnvalidatedOrder(
        val unvalidatedCustomerInfo: UnvalidatedCustomerInfo,
        val unvalidatedShippingAddress: UnvalidatedShippingAddress,
        val unvalidatedBillingAddress: UnvalidatedBillingAddress,
        val unvalidatedOrderLines: UnvalidatedOrderLines
)