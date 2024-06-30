package com.umang.reminderapp.util

import com.umang.reminderapp.data.classes.BillingPeriod
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun daysToYMWD(startDate: LocalDate, endDate: LocalDate): List<Long> {

    val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)

    val years = daysBetween / 365
    val weeks = (daysBetween%365) / 7
    val days = daysBetween - (years*365) - (weeks*7)

    return listOf(years, weeks, days)
}


fun getAlarms(startDate: LocalDate, endDate: LocalDate, billingPeriod: BillingPeriod): List<LocalDate> {

    var tempDate = startDate

    val alarmsList = mutableListOf<LocalDate>()
    while(tempDate<endDate){
        if(billingPeriod == BillingPeriod.DAILY){ tempDate=tempDate.plusDays(1) }
        else if(billingPeriod == BillingPeriod.WEEKLY){ tempDate=tempDate.plusWeeks(1) }
        else if(billingPeriod == BillingPeriod.MONTHLY){ tempDate=tempDate.plusMonths(1) }
        else if(billingPeriod == BillingPeriod.YEARLY){ tempDate=tempDate.plusYears(1) }

        alarmsList.add(tempDate)
    }

    return alarmsList

}