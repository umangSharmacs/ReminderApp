package com.umang.reminderapp.data.models

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umang.reminderapp.data.classes.MedicineIntakeTime
import com.umang.reminderapp.data.classes.MedicineItem
import com.umang.reminderapp.data.classes.MedicineMealType
import com.umang.reminderapp.singletons.MedicineManager
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

class MedicineViewModel: ViewModel() {

    private val _medicineList = MutableLiveData<SnapshotStateList<MedicineItem>>()
    val medicineList : LiveData<SnapshotStateList<MedicineItem>> = _medicineList

    init {
        getAllMedicine()
    }

    fun getAllMedicine(){
        viewModelScope.launch {
            _medicineList.value = MedicineManager.getAllMedicine()
        }
        Log.d("Medicine ViewModel", _medicineList.value.toString())
    }

    fun getMedicineItem(id: Int): MedicineItem? {
        return MedicineManager.getMedicineItem(id)
    }

    fun addMedicineItem(
        name: String,
        prescriptionStart: String = LocalDate.now().toString(),
        duration: String = Duration.ZERO.toString(),
        prescriptionEnd: String = LocalDate.now().toString(),
        whenToTake: MutableMap<String, String?>,
        takenMap: MutableMap<String, Boolean>,
        isActive: Boolean,
        expiry: String?
    ): MedicineItem {
        val medicineItem = MedicineManager.addMedicineItem(
            name,
            prescriptionStart,
            duration,
            prescriptionEnd,
            whenToTake,
            takenMap,
            isActive,
            expiry
        )

        this.viewModelScope.launch {
            getAllMedicine()
        }

        return medicineItem
    }

    fun deleteMedicineItem(id: Int){
        MedicineManager.deleteMedicineItem(id)
        this.viewModelScope.launch {
            getAllMedicine()
        }
    }

    fun updateMedicineItem(
        toUpdateName: String,
        toUpdatePrescriptionStart: String?,
        toUpdateDuration: String?,
        toUpdatePrescriptionEnd: String?,
        toUpdateWhenToTake: MutableMap<String,String?>,
        toUpdateTakenMap: MutableMap<String, Boolean>,
        toUpdateIsActive: Boolean,
        toUpdateExpiry: String?,
        toUpdateMedicineItemID: Int
    ): MedicineItem? {
        val updatedMedicineItem = MedicineManager.updateMedicineItem(
            toUpdateName,
            toUpdatePrescriptionStart,
            toUpdateDuration,
            toUpdatePrescriptionEnd,
            toUpdateWhenToTake,
            toUpdateTakenMap,
            toUpdateIsActive,
            toUpdateExpiry,
            toUpdateMedicineItemID
        )

        this.viewModelScope.launch {
            getAllMedicine()
        }

        return updatedMedicineItem
    }

    fun createDummyMedicineItem(){

        val dummyItem = MedicineItem()

        val medItem1 = addMedicineItem(
            name = "Paracetamol",
            prescriptionStart = LocalDate.now().toString(),
            duration = 7.days.toString(),
            prescriptionEnd = LocalDate.now().plusDays(7).toString(),
            whenToTake = mutableMapOf(
                MedicineMealType.BREAKFAST.value to MedicineIntakeTime.BEFORE.value,
                MedicineMealType.LUNCH.value to MedicineIntakeTime.BEFORE.value,
                MedicineMealType.DINNER.value to MedicineIntakeTime.BEFORE.value
            ),
            takenMap = mutableMapOf(
                MedicineMealType.BREAKFAST.value to false,
                MedicineMealType.LUNCH.value to false,
                MedicineMealType.DINNER.value to false
            ),
            isActive = true,
            expiry = LocalDate.now().plusYears(1).toString()
        )

        val medItem2 = addMedicineItem(
            name = "Diabetes Meds",
            prescriptionStart = LocalDate.now().toString(),
            duration = 15.days.toString(),
            prescriptionEnd = LocalDate.now().plusDays(15).toString(),
            whenToTake = mutableMapOf(
                MedicineMealType.BREAKFAST.value to MedicineIntakeTime.AFTER.value,
                MedicineMealType.LUNCH.value to MedicineIntakeTime.NONE.value,
                MedicineMealType.DINNER.value to MedicineIntakeTime.BEFORE.value
            ),
            takenMap = mutableMapOf(
                MedicineMealType.BREAKFAST.value to false,
                MedicineMealType.LUNCH.value to false,
                MedicineMealType.DINNER.value to false
            ),
            isActive = true,
            expiry = LocalDate.now().plusYears(1).toString()
        )

        val medItem3 = addMedicineItem(
            name = "Blood Pressure Meds",
            prescriptionStart = LocalDate.now().plusDays(30).toString(),
            duration = 7.days.toString(),
            prescriptionEnd = LocalDate.now().plusDays(37).toString(),
            whenToTake = mutableMapOf(
                MedicineMealType.BREAKFAST.value to MedicineIntakeTime.BEFORE.value,
                MedicineMealType.LUNCH.value to MedicineIntakeTime.BEFORE.value,
                MedicineMealType.DINNER.value to MedicineIntakeTime.BEFORE.value
            ),
            takenMap = mutableMapOf(
                MedicineMealType.BREAKFAST.value to false,
                MedicineMealType.LUNCH.value to false,
                MedicineMealType.DINNER.value to false
            ),
            isActive = false,
            expiry = LocalDate.now().plusYears(1).toString()
        )

    }

}