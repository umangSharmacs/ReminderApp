package com.umang.reminderapp.data.classes

import java.time.LocalDate
import kotlin.time.DurationUnit
import kotlin.time.toDuration

sealed class MedicineMealType(val value: String){
    data object BREAKFAST: MedicineMealType("BREAKFAST")
    data object LUNCH: MedicineMealType("LUNCH")
    data object DINNER: MedicineMealType("DINNER")
}


sealed class MedicineIntakeTime(val value: String) {
    data object BEFORE: MedicineIntakeTime("BEFORE")
    data object WITH: MedicineIntakeTime("WITH")
    data object AFTER: MedicineIntakeTime("AFTER")
    data object NONE: MedicineIntakeTime("NONE")
}

data class MedicineItem(
    var id: Int,
    var name: String,
    var prescriptionStart: String?,
    var duration: String?,
    var prescriptionEnd: String?,
    var whenToTake: MutableMap<String, String?>,
    var takenMap: MutableMap<String, Boolean>,
    var isActive: Boolean,
    var expiry: String?,
){
    constructor(): this(
        id = 0,
        name = "Paracetamol",
        prescriptionStart = LocalDate.now().toString(),
        duration = 7.toDuration(DurationUnit.DAYS).toString(),
        prescriptionEnd = LocalDate.now().plusDays(7).toString(),
        whenToTake = hashMapOf(
            MedicineMealType.BREAKFAST.value to MedicineIntakeTime.NONE.value,
            MedicineMealType.LUNCH.value to MedicineIntakeTime.NONE.value,
            MedicineMealType.DINNER.value to MedicineIntakeTime.NONE.value
        ),
        takenMap = hashMapOf(
            MedicineMealType.BREAKFAST.value to false,
            MedicineMealType.LUNCH.value to false,
            MedicineMealType.DINNER.value to false
        ),
        isActive = false,
        expiry = null
    )

}
