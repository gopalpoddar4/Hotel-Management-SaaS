package com.nexifotech.hotelsaas.feature.frontoffice.domain.model

data class FrontOfficeMetrics(
    val checkIns: Int,
    val checkOuts: Int,
    val walkIns: Int,
    val pendingArrivals: Int
)
