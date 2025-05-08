package com.marini.school_manager.service;

import com.marini.school_manager.exception.ResourceNotFoundException;
import com.marini.school_manager.model.Student;
import com.marini.school_manager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /*
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }
    */

    // Restituisci tutti gli studenti.
    public Page<Student> getAllStudent(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    // Trova un utente specifico tramite il suo Id.
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with this id: " + id + " not found."));
    }

    // Aggiungi un nuovo studente.
    public Student saveStudent(Student student) {

        return studentRepository.save(student);
    }

    //// Elimina uno studente.
    //public void deleteStudentById(Long id) {
    //    if (studentRepository.existsById(id)) {
    //        studentRepository.deleteById(id);
    //    } else {
    //        throw new ResourceNotFoundException("Student with this id: " + id + " not found.");
    //    }
    //}

    // Aggiorna uno studente.
    public Student updateStudent(Long id, Student student) {
        Optional<Student> existingStudentOptional = studentRepository.findById(id);

        if (!existingStudentOptional.isPresent()) {
            throw new ResourceNotFoundException("Student with this id: " + id + " not found.");
        }

        Student existingStudent = existingStudentOptional.get();
        existingStudent.setUser(student.getUser());
        existingStudent.setEnrollement(student.getEnrollement());

        return studentRepository.save(existingStudent);
    }

}
