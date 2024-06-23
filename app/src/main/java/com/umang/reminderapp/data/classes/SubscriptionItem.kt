package com.umang.reminderapp.data.classes

import kotlin.time.Duration

data class SubscriptionItem(
    var id: Int,
    var subscriptionName: String,
    var duration: String,
    var startDate: String,
    var endDate: String,
    val isActive: Boolean,
    var tags: List<String>,
    var billingPeriod: BillingPeriod
){
    constructor(): this(
        id = 0,
        subscriptionName = "Subscription",
        duration = Duration.ZERO.toString(),
        startDate = "",
        endDate = "",
        isActive = true,
        tags = emptyList(),
        billingPeriod = BillingPeriod.MONTHLY
    )
}


