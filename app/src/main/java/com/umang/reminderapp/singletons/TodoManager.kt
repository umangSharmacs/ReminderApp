package com.umang.reminderapp.singletons

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umang.reminderapp.data.classes.TodoItem
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

import java.time.LocalDate

object TodoManager {


    private var todoList = SnapshotStateList<TodoItem>()

    suspend fun getAllToDo(): SnapshotStateList<TodoItem> {
        val tempTodolist = SnapshotStateList<TodoItem>()
        try{
            Firebase.firestore
                .collection( "todo_"+Firebase.auth.currentUser!!.uid)
                .get()
                .await()
                .map{
                    if(it.id!="tags"){
                        val todoItem = it.toObject(TodoItem::class.java)
                        Log.d("TodoManager","$todoItem")
                        tempTodolist.add(todoItem)
                    }
                }
            todoList = tempTodolist
            Log.d("TodoManager","$todoList)")
        } catch (e: Exception){
            Log.w("Firebase GET ERROR", "Error getting documents: ", e)
            if (e is CancellationException) throw e
        }
        return todoList
    }

    fun getToDoItem(id: Int): TodoItem? {
        return todoList.find { it.id == id }
    }

    fun addTodoItem(
        title: String,
        dueDate: String = LocalDate.now().toString(),
        tags: List<String> = emptyList(),
        description: String = "Hello World. This is a description",
        completed: Boolean = false,
        completedOn: String = "1900-01-01",
        reminders: List<String> = emptyList(),
        priority: Int = 3
    ): TodoItem {

        val user = Firebase.auth.currentUser

        // Create todo_item
        val todoItem = TodoItem(
            id = System.currentTimeMillis().toInt(),
            title = title,
            createdOn = LocalDate.now().toString(),
            dueDate = dueDate,
            tags = tags,
            description = description,
            completed = completed,
            completedOn = completedOn,
            reminders = reminders,
            priority = priority
        )

        todoList.add(todoItem)

        // Add item to Firestore to the user collection

        if (user != null) {
            Firebase.firestore.collection("todo_"+user.uid)
                .document(todoItem.id.toString())
                .set(todoItem)
                .addOnSuccessListener {
                    Log.d("TAG", "DocumentSnapshot successfully written!")
            }
        }
        return todoItem
    }

    fun deleteTodoItem(id : Int){
        todoList.removeIf{
            it.id == id
        }

        // Delete from firestore.
        Firebase.firestore.collection("todo_"+Firebase.auth.currentUser!!.uid)
            .document(id.toString()).delete()

    }

    fun updateTodoItem(
        updatedTodoTitle : String,
        updatedTodoDescription : String,
        updatedTodoDueDate : String,
        updatedTodoTags : List<String>,
        updatedPriority : Int,
        updatedReminders : List<String>,
        toUpdateTodoItemID: Int
    ): TodoItem? {
        val existingToDoItem = getToDoItem(toUpdateTodoItemID)

        if (existingToDoItem != null) {
            existingToDoItem.title = updatedTodoTitle
            existingToDoItem.description = updatedTodoDescription
            existingToDoItem.dueDate = updatedTodoDueDate
            existingToDoItem.tags = updatedTodoTags
            existingToDoItem.priority = updatedPriority
            existingToDoItem.reminders = updatedReminders

            Firebase.firestore.collection("todo_"+Firebase.auth.currentUser!!.uid)
                .document(toUpdateTodoItemID.toString()).set(existingToDoItem)

        }
        return existingToDoItem
    }

}