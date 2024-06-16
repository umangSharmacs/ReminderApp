package com.umang.reminderapp.singletons

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import kotlin.coroutines.cancellation.CancellationException

object TagManager {

    private var tags = SnapshotStateList<String>()

    suspend fun getAllTags():SnapshotStateList<String> {
        val tempList: SnapshotStateList<String> = SnapshotStateList()

        try{
            val documentSnapshot = Firebase.firestore
                .collection(Firebase.auth.currentUser!!.uid)
                .document("tags")
                .get()
                .await()

                val tagsArray = documentSnapshot.get("tags") as List<*>
                for(tag in tagsArray){
                    tempList.add(tag.toString())
                }
            tags = tempList

        } catch (e: Exception) {
            println("Error getting document: ${e.message}")
        }
        return tags
    }

    fun getTag(name: String): String? {
        return tags.find { it == name }
    }

    fun addTag(
        name: String = " Tag",
    ) {

        val user = Firebase.auth.currentUser
        TagManager.tags.add(name)
        if (user != null) {
            Firebase.firestore.collection(user.uid)
                .document("tags")
                .set(
                    hashMapOf("tags" to tags.toList())
                )
                .addOnSuccessListener {
                    Log.d("TAGS", "DocumentSnapshot successfully written!")
                }
        }

    }

    fun deleteTag(name : String){
        TagManager.tags.removeIf{
            it == name
        }

        // Update firestore

        val user = Firebase.auth.currentUser

        if (user!=null){
            Firebase.firestore.collection(user.uid)
                .document("tags")
                .set(
                    "tags" to tags.toList()
                )
                .addOnSuccessListener {
                    Log.d("TAGS", "DocumentSnapshot successfully written!")
                }
        }
    }
//
//    fun updateTag(
//        updatedName : String,
//        updatedBackgroundColor : Color,
//        updatedContentColor : Color,
//        originalName : String
//    ){
//        val existingTag = TagManager.getTag(originalName)
//
//        if (existingTag != null) {
//            existingTag.name = updatedName
//            existingTag.backgroundColor = updatedBackgroundColor
//            existingTag.contentColor = updatedContentColor
//
//            // TODO
////            Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
////                .document(toUpdateTodoItemID.toString()).set(existingToDoItem)
//        }
//    }

    fun createDummyTags() {

        val tag1 = "Tag1"
        val tag2 = "Tag2"

        addTag(tag1)
        addTag(tag2)

    }


}