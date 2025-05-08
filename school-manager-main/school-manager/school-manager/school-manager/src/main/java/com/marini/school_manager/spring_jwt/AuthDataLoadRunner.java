package com.marini.school_manager.spring_jwt;

import com.marini.school_manager.model.Student;
import com.marini.school_manager.model.Teacher;
import com.marini.school_manager.repository.StudentRepository;
import com.marini.school_manager.repository.TeacherRepository;
import com.marini.school_manager.spring_jwt.model.AuthUser;
import com.marini.school_manager.spring_jwt.model.ERole;
import com.marini.school_manager.spring_jwt.model.Role;
import com.marini.school_manager.spring_jwt.repository.AuthRoleRepository;
import com.marini.school_manager.spring_jwt.repository.AuthUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(1)
@Transactional    // Con @Transactional, tutte le operazioni dentro il metodo avvengono nella stessa transazione, quindi gli oggetti salvati restano gestiti (attached) fino alla fine.
public class AuthDataLoadRunner implements CommandLineRunner {

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    AuthRoleRepository authRoleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {

        // Ruoli
        Role r_teacher = new Role();
        r_teacher.setName(ERole.ROLE_TEACHER);
        authRoleRepository.save(r_teacher);

        Role r_student = new Role();
        r_student.setName(ERole.ROLE_STUDENT);
        authRoleRepository.save(r_student);

        // User Teacher
        AuthUser u_teacher = new AuthUser("user_teacher", "teacherEmail@gmail.com", encoder.encode("mygoodpassword"), "nomeTeacher", "cognomeTeacher", LocalDate.of(1985, 10, 23), r_teacher);
        authUserRepository.save(u_teacher);

        Teacher teacher = new Teacher();
        teacher.setUser(u_teacher);
        teacher.setSpecialisation("Mathematics");
        teacherRepository.save(teacher);

        // User Student 1
        AuthUser u_student = new AuthUser("user_student", "studentEmail@gmail.com", encoder.encode("mygoodpassword"), "nomeStudent", "cognomeStudent", LocalDate.of(1992, 8, 12), r_student);
        authUserRepository.save(u_student);

        Student student = new Student();
        student.setUser(u_student);
        studentRepository.save(student);

        // User Student 2
        AuthUser u_student1 = new AuthUser("marco", "marco@gmail.com", encoder.encode("mygoodpassword"), "Marco", "Facecchia", LocalDate.of(2004, 6, 12), r_student);
        authUserRepository.save(u_student1);

        Student student1 = new Student();
        student1.setUser(u_student1);
        studentRepository.save(student1);

        // User Student 3
        AuthUser u_student2 = new AuthUser("Mattia Mariano", "mattMariano@gmail.com", encoder.encode("mygoodpassword"), "Mattia", "Mariano", LocalDate.of(2004, 5, 7), r_student);
        authUserRepository.save(u_student2);

        Student student2 = new Student();
        student2.setUser(u_student2);
        studentRepository.save(student2);
    }
}




//@Component
//@Order(1)
//public class AuthDataLoadRunner implements CommandLineRunner {
//
//    @Autowired
//    AuthUserRepository authUserRepository;
//
//    @Autowired
//    AuthRoleRepository authRoleRepository;
//
//    @Autowired
//    PasswordEncoder encoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Role r_teacher = new Role();
//        r_teacher.setName(ERole.ROLE_TEACHER);
//        authRoleRepository.save(r_teacher);
//
//        Role r_student = new Role();
//        r_student.setName(ERole.ROLE_STUDENT);
//        authRoleRepository.save(r_student);
//
//        AuthUser u_teacher = new AuthUser("user_teacher", "teacherEmail@gmail.com", encoder.encode("mygoodpassword"), r_teacher);
//        authUserRepository.save(u_teacher);
//
//        AuthUser u_student = new AuthUser("user_student", "studentEmail@gmail.com", encoder.encode("mygoodpassword"), r_student);
//        authUserRepository.save(u_student);
//
//        AuthUser u_student1 = new AuthUser("marco", "marco@gmail.com", encoder.encode("mygoodpassword"), r_student);
//        authUserRepository.save(u_student1);
//
//        AuthUser u_student2 = new AuthUser("Mattia Mariano", "mattMariano@gmail.com", encoder.encode("mygoodpassword"), r_student);
//        authUserRepository.save(u_student2);
//    }
//}
