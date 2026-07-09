package com.nexifotech.hotelsaas.feature.billing.domain.model

data class Invoice(
    val id: String,
    val invoiceNumber: String,
    val guestName: String,
    val guestId: String,
    val roomNumber: String,
    val roomId: String,
    val bookingId: String,
    val checkInDate: String,
    val checkOutDate: String,
    val stayDurationDays: Int,
    val roomCharges: Double,
    val extraCharges: Double,
    val restaurantCharges: Double,
    val laundryCharges: Double,
    val discount: Double,
    val taxPercentage: Double,
    val totalAmount: Double,
    val paidAmount: Double,
    val paymentMethod: String,
    val paymentDate: String?,
    val invoiceStatus: InvoiceStatus,
    val paymentStatus: PaymentStatus,
    val notes: String
) {
    val remainingAmount: Double
        get() = totalAmount - paidAmount
        
    val taxAmount: Double
        get() = (roomCharges + extraCharges + restaurantCharges + laundryCharges - discount) * (taxPercentage / 100)
}

enum class InvoiceStatus {
    DRAFT, GENERATED, CANCELLED
}

enum class PaymentStatus {
    PENDING, PARTIALLY_PAID, PAID, OVERDUE, REFUNDED
}
