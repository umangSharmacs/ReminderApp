package com.umang.reminderapp.singletons

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umang.reminderapp.data.classes.BillingPeriod
import com.umang.reminderapp.data.classes.SubscriptionItem
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

object SubscriptionManager {

    private var subscriptionList = SnapshotStateList<SubscriptionItem>()

    suspend fun getAllSubscriptions(): SnapshotStateList<SubscriptionItem> {
        val tempSubscriptionList = SnapshotStateList<SubscriptionItem>()
        try{
            Firebase.firestore
                .collection( "subscriptions_"+ Firebase.auth.currentUser!!.uid)
                .get()
                .await()
                .map{
                    if(it.id!="tags"){
                        val subscriptionItem = it.toObject(SubscriptionItem::class.java)
                        Log.d("SubscriptionManager","$subscriptionItem")
                        tempSubscriptionList.add(subscriptionItem)
                    }
                }
            SubscriptionManager.subscriptionList = tempSubscriptionList
            Log.d("TodoManager","${SubscriptionManager.subscriptionList})")
        } catch (e: Exception){
            Log.w("Firebase GET ERROR", "Error getting documents: ", e)
            if (e is CancellationException) throw e
        }
        return SubscriptionManager.subscriptionList
    }

    fun getSubscriptionItem(id: Int): SubscriptionItem? {
        return SubscriptionManager.subscriptionList.find { it.id == id }
    }

    fun addSubscriptionItem(
        subscriptionName: String ,
        duration: String,
        startDate: String,
        endDate: String,
        tags: List<String>,
        billingPeriod: BillingPeriod
    ): SubscriptionItem {

        val subscriptionItem = SubscriptionItem(
            id = System.currentTimeMillis().toInt(),
            subscriptionName = subscriptionName,
            duration = duration,
            startDate = startDate,
            endDate = endDate,
            isActive = true,
            tags = tags,
            billingPeriod = billingPeriod
        )

        Firebase.firestore
            .collection( "subscriptions_"+ Firebase.auth.currentUser!!.uid)
            .document(subscriptionItem.id.toString())
            .set(subscriptionItem)
            .addOnSuccessListener {
                Log.d("Subscription", "DocumentSnapshot successfully written!")
            }
        return subscriptionItem
    }

    fun updateSubscriptionItem(
        updatedSubscriptionName: String,
        updatedDuration: String,
        updatedStartDate: String,
        updatedEndDate: String,
        updatedTags: List<String>,
        updatedBillingPeriod: BillingPeriod,
        toUpdateID: Int
    ): SubscriptionItem? {

        val toUpdateSubscriptionItem = SubscriptionManager.getSubscriptionItem(toUpdateID)

        if (toUpdateSubscriptionItem != null) {
            toUpdateSubscriptionItem.subscriptionName = updatedSubscriptionName
            toUpdateSubscriptionItem.duration = updatedDuration
            toUpdateSubscriptionItem.startDate = updatedStartDate
            toUpdateSubscriptionItem.endDate = updatedEndDate
            toUpdateSubscriptionItem.tags = updatedTags
            toUpdateSubscriptionItem.billingPeriod = updatedBillingPeriod

            Firebase.firestore.collection("subscriptions_"+Firebase.auth.currentUser!!.uid)
                .document(toUpdateID.toString()).set(toUpdateSubscriptionItem)
        }
        return toUpdateSubscriptionItem
    }

    fun deleteSubscriptionItem(toDeleteID: Int) {

        subscriptionList.removeIf { it.id == toDeleteID }

        // Remove from firestore
        Firebase.firestore.collection("subscriptions_"+Firebase.auth.currentUser!!.uid)
            .document(toDeleteID.toString()).delete()
    }
}