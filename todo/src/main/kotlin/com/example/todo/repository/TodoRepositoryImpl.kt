package com.example.todo.repository

import com.example.todo.database.Todo
import com.example.todo.database.TodoDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TodoRepositoryImpl(
    val todoDatabase: TodoDatabase
): TodoRepository {

//    @Autowired
//    lateinit var todoDatabase: TodoDatabase // init된 객체가 자동으로 주입된다.

    override fun save(todo: Todo): Todo? {

        // 1. index? -> update
        return todo.index?.let { index ->

            findOne(index)?.apply {
                this.title = todo.title
                this.description = todo.description
                this.schedule = todo.schedule
                this.updatedAt = LocalDateTime.now()
            }

            // null -> insert
        }?: kotlin.run {
            todo.apply {
                this.index = ++todoDatabase.index
                this.createdAt = LocalDateTime.now()
                this.updatedAt = LocalDateTime.now()
                todoDatabase.todoList.add(todo)
            }
        }
    }

    override fun saveAll(todoList: MutableList<Todo>): Boolean {
        return try {
            todoList.forEach {
                save(it)
            }
            true
        }catch (e: Exception) {
            false
        }
    }

//    override fun update(todo: Todo): Todo {
//        // JPA
//        // save db index
//
//    }

    override fun delete(index: Int): Boolean {

        return findOne(index)?.let {
            todoDatabase.todoList.remove(it)
            true
        }?:kotlin.run {
            false
        }
    }

    override fun findOne(index: Int): Todo? {
        return todoDatabase.todoList.first {
            it.index == index
        }
    }

    override fun findAll(): MutableList<Todo> {
        return todoDatabase.todoList
    }
}