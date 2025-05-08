package com.marini.school_manager.controller;

import com.marini.school_manager.model.Student;
import com.marini.school_manager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public  StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {

        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudent(Pageable pageable) {

        Page<Student> students =studentService.getAllStudent(pageable);
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public Student saveStudent(@RequestBody Student student) {

        return studentService.saveStudent(student);
    }

    //@PutMapping("/{id}")
    //public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
//
    //    Student updateStudent = studentService.updateStudent(id, student);
    //    return ResponseEntity.ok(updateStudent);
    //}
}
