package com.umang.reminderapp.singletons

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umang.reminderapp.data.classes.BillingPeriod
import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.util.getNextBillingAlarmInDays
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale
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
        billingPeriod: BillingPeriod,
        cost: Double
    ): SubscriptionItem {

        val subscriptionItem = SubscriptionItem(
            id = System.currentTimeMillis().toInt(),
            subscriptionName = subscriptionName,
            duration = duration,
            startDate = startDate,
            endDate = endDate,
            isActive = true,
            tags = tags,
            billingPeriod = billingPeriod,
            cost = cost
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
        updatedCost: Double,
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
            toUpdateSubscriptionItem.cost = updatedCost

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

    fun getSubscriptionsCost(calculationPeriod: BillingPeriod): Double {

        var totalCost = 0.0

        // Get all the subscriptions which are

        for (subscription in subscriptionList){

            if(!subscription.isActive){ continue }

            // Get the nearest alarm
            val nearestAlarm = LocalDate.now().plusDays(getNextBillingAlarmInDays(subscription).toLong())

            when(calculationPeriod){
                BillingPeriod.MONTHLY -> {
                    if(nearestAlarm.month == LocalDate.now().month){
                        totalCost += when(subscription.billingPeriod){
                            BillingPeriod.WEEKLY -> {
                                (subscription.cost * 4)
                            }
                            BillingPeriod.DAILY -> {
                                (subscription.cost * 28)
                            }
                            else -> {
                                subscription.cost
                            }
                        }
                    }
                }
                BillingPeriod.YEARLY -> {
                    if(nearestAlarm.year == LocalDate.now().year){
                        totalCost += when(subscription.billingPeriod){
                            BillingPeriod.MONTHLY -> {
                                (subscription.cost * 12)
                            }
                            BillingPeriod.WEEKLY -> {
                                (subscription.cost * 52)
                            }
                            BillingPeriod.DAILY -> {
                                (subscription.cost * 365)
                            }
                            else -> {
                                subscription.cost
                            }
                        }
                    }
                }
                BillingPeriod.WEEKLY -> {

                    val currentWeek = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())
                    val currentYear = LocalDate.now().year

                    val testWeek = nearestAlarm.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())
                    val testYear = nearestAlarm.year

                    if( currentWeek==testWeek && currentYear==testYear){
                        totalCost += when(subscription.billingPeriod){
                            BillingPeriod.DAILY -> {
                                (subscription.cost * 7)
                            }
                            else -> { subscription.cost }
                        }
                    }
                }

                BillingPeriod.DAILY -> {
                    if(nearestAlarm == LocalDate.now()){
                        totalCost += subscription.cost
                    }
                }
                else -> totalCost += 0.0
            }
        }

        return totalCost
    }


}