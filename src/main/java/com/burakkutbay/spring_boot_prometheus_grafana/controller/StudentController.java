package com.burakkutbay.spring_boot_prometheus_grafana.controller;

import com.burakkutbay.spring_boot_prometheus_grafana.model.Student;
import com.burakkutbay.spring_boot_prometheus_grafana.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        LOGGER.info("Saving student via POST request: {}", student);
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<Student> getStudent(@RequestParam Long id) {
        LOGGER.info("Retrieving student with ID via GET request: {}", id);
        return new ResponseEntity<>(studentService.getStudent(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudent() {
        LOGGER.info("Retrieving all students via GET request");
        return new ResponseEntity<>(studentService.getAllStudent(), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public void deleteStudent(@RequestParam Long id) {
        LOGGER.info("Deleting student with ID via POST request: {}", id);
        studentService.deleteStudent(id);
    }
}
