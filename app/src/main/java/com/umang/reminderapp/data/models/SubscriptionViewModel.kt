package com.umang.reminderapp.data.models

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umang.reminderapp.data.classes.BillingPeriod
import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.singletons.SubscriptionManager
import kotlinx.coroutines.launch

class SubscriptionViewModel: ViewModel() {

    private var _subscriptionList = MutableLiveData<SnapshotStateList<SubscriptionItem>>()

    var subscriptionList : LiveData<SnapshotStateList<SubscriptionItem>> = _subscriptionList

    init {
        getAllSubscriptions()
    }

    fun getAllSubscriptions(){
        viewModelScope.launch {
            _subscriptionList.value = SubscriptionManager.getAllSubscriptions()
        }
        Log.d("Subscription ViewModel","${subscriptionList.value}")
    }

    fun getSubscriptionItem(id: Int): SubscriptionItem? {
        return SubscriptionManager.getSubscriptionItem(id)
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
        val subscriptionItem = SubscriptionManager.addSubscriptionItem(
            subscriptionName,
            duration,
            startDate,
            endDate,
            tags,
            billingPeriod,
            cost
        )
        this.viewModelScope.launch { getAllSubscriptions() }
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

        val updatedSubscriptionItem = SubscriptionManager.updateSubscriptionItem(
            updatedSubscriptionName,
            updatedDuration,
            updatedStartDate,
            updatedEndDate,
            updatedTags,
            updatedBillingPeriod,
            updatedCost,
            toUpdateID
        )
        this.viewModelScope.launch { getAllSubscriptions() }
        return updatedSubscriptionItem
    }

    fun deleteSubscriptionItem(id: Int) {
        SubscriptionManager.deleteSubscriptionItem(id)
        this.viewModelScope.launch { getAllSubscriptions() }
    }

    fun getSubscriptionsCost(calculationPeriod: BillingPeriod): Double {
        this.viewModelScope.launch { getAllSubscriptions() }
        return SubscriptionManager.getSubscriptionsCost(calculationPeriod)

    }

}