package com.umang.reminderapp.util

import com.umang.reminderapp.data.classes.MealTiming
import com.umang.reminderapp.data.classes.MedicineIntakeTime
import com.umang.reminderapp.data.classes.MedicineItem
import com.umang.reminderapp.data.classes.MedicineMealType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit


fun getMedicineReminderTimes(
    medicineItem: MedicineItem,
    mealTimings: Map<String,String>
): List<LocalDateTime> {

    var remindersList = mutableListOf<LocalDateTime>()

    // Get breakfast reminder time.
    val breakfastDelta = when( medicineItem.whenToTake[MedicineMealType.BREAKFAST.value] ) {
        MedicineIntakeTime.BEFORE.value -> -15
        MedicineIntakeTime.WITH.value -> 0
        MedicineIntakeTime.AFTER.value -> 15
        else ->  Int.MAX_VALUE
    }

    // Get lunch reminder time.
    val lunchDelta = when( medicineItem.whenToTake[MedicineMealType.LUNCH.value] ) {
        MedicineIntakeTime.BEFORE.value -> -15
        MedicineIntakeTime.WITH.value -> 0
        MedicineIntakeTime.AFTER.value -> 15
        else ->  Int.MAX_VALUE
    }

    // Get dinner reminder time.
    val dinnerDelta = when( medicineItem.whenToTake[MedicineMealType.DINNER.value] ) {
        MedicineIntakeTime.BEFORE.value -> -15
        MedicineIntakeTime.WITH.value -> 0
        MedicineIntakeTime.AFTER.value -> 15
        else ->  Int.MAX_VALUE
    }

    val daysDifference = ChronoUnit.DAYS.between(
        LocalDate.parse(medicineItem.prescriptionStart),
        LocalDate.parse(medicineItem.prescriptionEnd)
    )

    // For start to end date, for every date, check the medicine time.
    for(i in 0..daysDifference) {
        val date = LocalDate.parse(medicineItem.prescriptionStart).plusDays(i)

        if(medicineItem.whenToTake[MedicineMealType.BREAKFAST.value]!= MedicineIntakeTime.NONE.value) {
            remindersList.add(
                LocalDateTime.of(
                    date,
                    LocalTime.parse(mealTimings[MedicineMealType.BREAKFAST.value]).plusMinutes(
                        breakfastDelta.toLong()
                    ))
            )
        }

        if(medicineItem.whenToTake[MedicineMealType.LUNCH.value]!= MedicineIntakeTime.NONE.value) {
            remindersList.add(
                LocalDateTime.of(
                    date,
                    LocalTime.parse(mealTimings[MedicineMealType.LUNCH.value]).plusMinutes(
                        lunchDelta.toLong()
                    ))
            )
        }

        if(medicineItem.whenToTake[MedicineMealType.DINNER.value]!= MedicineIntakeTime.NONE.value) {
            remindersList.add(
                LocalDateTime.of(
                    date,
                    LocalTime.parse(mealTimings[MedicineMealType.DINNER.value]).plusMinutes(
                        dinnerDelta.toLong()
                    ))
            )
        }
    }

    remindersList = remindersList.filter {it.isAfter(LocalDateTime.now())}.toMutableList()

    return remindersList
}


fun getNextIntakeTime(
    medicineItem: MedicineItem,
    mealTimings: Map<String,String>
): Long {
    val reminders = getMedicineReminderTimes(medicineItem, mealTimings)
    val nextAlarm = reminders.min()

    return ChronoUnit.HOURS.between(LocalDateTime.now(), nextAlarm)
}
