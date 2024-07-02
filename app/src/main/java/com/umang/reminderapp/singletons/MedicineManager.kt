package com.umang.reminderapp.singletons

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umang.reminderapp.data.classes.MedicineIntakeTime
import com.umang.reminderapp.data.classes.MedicineItem
import com.umang.reminderapp.data.classes.MedicineMealType
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration

object MedicineManager {

    private var medicineList = SnapshotStateList<MedicineItem>()

    suspend fun getAllMedicine(): SnapshotStateList<MedicineItem> {
        val tempMedlist = SnapshotStateList<MedicineItem>()
        try{
            Firebase.firestore
                .collection( "meds_"+ Firebase.auth.currentUser!!.uid)
                .get()
                .await()
                .map{
                    if(it.id!="tags"){
                        val medicineItem = it.toObject(MedicineItem::class.java)
                        tempMedlist.add(medicineItem)
                    }
                }
            MedicineManager.medicineList = tempMedlist
            Log.d("Medicine Manager","${MedicineManager.medicineList})")
        } catch (e: Exception){
            Log.w("Firebase GET ERROR", "Error getting documents: ", e)
            if (e is CancellationException) throw e
        }
        return MedicineManager.medicineList
    }

    fun getMedicineItem(id: Int): MedicineItem? {
        return MedicineManager.medicineList.find { it.id == id }
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

        val user = Firebase.auth.currentUser

        val medicineItem = MedicineItem(
            id = System.currentTimeMillis().toInt(),
            name = name,
            prescriptionStart = prescriptionStart,
            duration = duration,
            prescriptionEnd = prescriptionEnd,
            whenToTake = whenToTake,
            takenMap = takenMap,
            isActive = isActive,
            expiry = expiry
        )

        MedicineManager.medicineList.add(medicineItem)

        // Add item to Firestore to the user collection

        if (user != null) {
            Firebase.firestore.collection("meds_"+user.uid)
                .document(medicineItem.id.toString())
                .set(medicineItem)
                .addOnSuccessListener {
                    Log.d("Medicine", "DocumentSnapshot successfully written!")
                }
        }
        return medicineItem
    }

    fun deleteMedicineItem(id : Int){
        MedicineManager.medicineList.removeIf{
            it.id == id
        }

        // Delete from firestore.
        Firebase.firestore.collection("meds_"+Firebase.auth.currentUser!!.uid)
            .document(id.toString()).delete()

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
        val existingMedicineItem = MedicineManager.getMedicineItem(toUpdateMedicineItemID)

        if (existingMedicineItem != null) {
            existingMedicineItem.name = toUpdateName
            existingMedicineItem.prescriptionStart = toUpdatePrescriptionStart
            existingMedicineItem.duration = toUpdateDuration
            existingMedicineItem.prescriptionEnd = toUpdatePrescriptionEnd
            existingMedicineItem.whenToTake = toUpdateWhenToTake
            existingMedicineItem.takenMap = toUpdateTakenMap
            existingMedicineItem.isActive = toUpdateIsActive
            existingMedicineItem.expiry = toUpdateExpiry

            Firebase.firestore.collection("meds_"+Firebase.auth.currentUser!!.uid)
                .document(toUpdateMedicineItemID.toString()).set(existingMedicineItem)

        }
        return existingMedicineItem
    }

}