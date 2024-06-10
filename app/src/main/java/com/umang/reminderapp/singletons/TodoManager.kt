package com.umang.reminderapp.singletons

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.umang.reminderapp.data.classes.TodoItem

import java.time.LocalDate

object TodoManager {


    private var todoList = SnapshotStateList<TodoItem>()

    fun getAllToDo(): SnapshotStateList<TodoItem> {

        val user = Firebase.auth.currentUser
        val tempList = SnapshotStateList<TodoItem>()
        user?.let {
            Firebase.firestore.collection(it.uid).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val todoItem = document.toObject(TodoItem::class.java)
                        tempList.add(todoItem)

                    }
                    Log.d("TempList", "$tempList")
                    todoList = tempList
                    Log.d("TodoList", "$todoList")

                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }
        }

        return todoList

    }

    fun getToDoItem(id: Int): TodoItem? {
        return todoList.find { it.id == id }
    }

    fun addTodoItem(
        title: String = " ToDo",
        description: String = "Hello World. THis is a description",
        dueDate: String = LocalDate.now().toString(),
        tags: List<String> = listOf("tag1","tag2")
    ){

        val user = Firebase.auth.currentUser

        // Create todo item
        val todoItem = TodoItem(
            id = System.currentTimeMillis().toInt(),
            title = title,
            createdOn = LocalDate.now().toString(),
            description = description,
            dueDate = dueDate,
            tags = tags)

        todoList.add(todoItem)

        // Add item to Firestore to the user collection

        if (user != null) {
            Firebase.firestore.collection(user.uid)
                .document(todoItem.id.toString())
                .set(todoItem)
                .addOnSuccessListener {
                    Log.d("TAG", "DocumentSnapshot successfully written!")
            }
        }
    }

    fun deleteTodoItem(id : Int){
        todoList.removeIf{
            it.id == id
        }

        // Delete from firestore.
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
            .document(id.toString()).delete()

    }

    fun updateTodoItem(
        updatedTodoTitle : String,
        updatedTodoDescription : String,
        updatedTodoDueDate : String,
        updatedTodoTags : List<String>,
        toUpdateTodoItemID: Int
                       ){
        val existingToDoItem = getToDoItem(toUpdateTodoItemID)

        if (existingToDoItem != null) {
            existingToDoItem.title = updatedTodoTitle
            existingToDoItem.description = updatedTodoDescription
            existingToDoItem.dueDate = updatedTodoDueDate
            existingToDoItem.tags = updatedTodoTags

            Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
                .document(toUpdateTodoItemID.toString()).set(existingToDoItem)

        }

    }

//    fun createDummyTodo(){
//
//        val item1 = addTodoItem(
//            "First todo",
//            "This is my first todo",
//            "2024-06-24",
//            listOf("tag1","tag2")
//        )
//        val item2 = addTodoItem(
//            "Second todo",
//            "This is my second todo",
//            "2024-07-24",
//            listOf("tag1","tag2","tag3")
//        )
//
//        val item3 = addTodoItem(
//            "Third todo",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
//                    "Phasellus vitae enim id dolor rhoncus vehicula vel nec ligula." +
//                    " Donec mollis leo interdum, condimentum dui a, pulvinar nulla.",
//            "2025-06-24",
//            listOf("tag3","tag2")
//        )
//
//    }

}