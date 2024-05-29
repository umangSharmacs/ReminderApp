package com.example.reminderapp

import androidx.compose.runtime.snapshots.SnapshotStateList
import java.time.Instant
import java.time.LocalDate
import java.util.Date

object TodoManager {

    private var todoList = SnapshotStateList<todo_item>()

    fun getAllToDo(): SnapshotStateList<todo_item> {

        return todoList
    }

    fun getToDoItem(id: Int): todo_item? {
        return todoList.find { it.id == id }
    }

    fun addTodoItem(
        title: String = " ToDo",
        description: String = "Hello World. THis is a description",
        dueDate: LocalDate = LocalDate.now(),
        tags: List<String> = listOf("tag1","tag2")
    ){
        todoList.add(todo_item(
            id = System.currentTimeMillis().toInt(),
            title = title,
            createdOn = Date.from(Instant.now()),
            description = description,
            dueDate = dueDate,
            tags = tags))
    }

    fun deleteTodoItem(id : Int){
        todoList.removeIf{
            it.id == id
        }

    }

    fun updateTodoItem(
        updatedTodoTitle : String,
        updatedTodoDescription : String,
        updatedTodoDueDate : LocalDate,
        updatedTodoTags : List<String>,
        toUpdateTodoItemID: Int
                       ){
        val existingToDoItem = getToDoItem(toUpdateTodoItemID)

        if (existingToDoItem != null) {
            existingToDoItem.title = updatedTodoTitle
            existingToDoItem.description = updatedTodoDescription
            existingToDoItem.dueDate = updatedTodoDueDate
            existingToDoItem.tags = updatedTodoTags
        }

    }

    fun createDummyTodo(){

        val item1 = addTodoItem(
            "First todo",
            "This is my first todo",
            LocalDate.parse("2024-06-24"),
            listOf("tag1","tag2")
        )
        val item2 = addTodoItem(
            "Second todo",
            "This is my second todo",
            LocalDate.parse("2024-07-24"),
            listOf("tag1","tag2","tag3")
        )

        val item3 = addTodoItem(
            "Third todo",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Phasellus vitae enim id dolor rhoncus vehicula vel nec ligula." +
                    " Donec mollis leo interdum, condimentum dui a, pulvinar nulla.",
            LocalDate.parse("2025-06-24"),
            listOf("tag3","tag2")
        )

    }

}