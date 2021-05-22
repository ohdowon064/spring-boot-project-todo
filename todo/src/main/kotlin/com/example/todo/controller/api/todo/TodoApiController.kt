package com.example.todo.controller.api.todo

import com.example.todo.model.http.TodoDto
import com.example.todo.service.TodoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/todo")
class TodoApiController(
    val todoService: TodoService
) {

    // R
    @GetMapping(path=[""])
    fun read(@RequestParam index:Int): TodoDto? {
        return todoService.read(index)
    }

    // R
    @GetMapping(path=["/all"])
    fun readAll(): MutableList<TodoDto> {
        return todoService.readAll()
    }

    // C
    @PostMapping(path=[""])
    fun create(@Valid @RequestBody todoDto: TodoDto): TodoDto? {
        return todoService.create(todoDto)
    }

    // U TODO create = 201, update = 200
    @PutMapping(path=[""])
    fun update(@Valid @RequestBody todoDto: TodoDto): TodoDto? {
        return todoService.create(todoDto)
    }

    // D
    @DeleteMapping(path=["/{index}"])
    fun delete(@PathVariable(name="index") _index:Int): ResponseEntity<Any> {

        if(!todoService.delete(_index))
            return ResponseEntity.status(500).build()

        return ResponseEntity.ok().build()
    }
}