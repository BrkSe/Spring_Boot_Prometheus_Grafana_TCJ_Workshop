package com.burakkutbay.spring_boot_prometheus_grafana.service;

import com.burakkutbay.spring_boot_prometheus_grafana.model.Student;
import com.burakkutbay.spring_boot_prometheus_grafana.repository.StudentRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentRepository studentRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final Counter saveStudentCounter;


    @Autowired
    public StudentService(MeterRegistry meterRegistry) {
        this.saveStudentCounter = Counter.builder("save_student_requests_total")
                .description("Total number of save student requests.")
                .register(meterRegistry);
    }

    public Student saveStudent(Student student) {
        try {
            LOGGER.info("Saving student: {}", student);
            // Metrik
            saveStudentCounter.increment();
            return studentRepository.save(student);
        } catch (Exception e) {
            LOGGER.error("Error occurred while saving student: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Student getStudent(Long id) {
        try {
            LOGGER.info("Retrieving student with ID: {}", id);
            return studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("Error occurred while retrieving student"));
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving student: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Student> getAllStudent() {
        try {
            LOGGER.info("Retrieving all students");
            return studentRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving all students: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteStudent(Long id) {
        try {
            LOGGER.warn("Deleting student with ID: {}", id);
            studentRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("Error occurred while deleting student: {}", e.getMessage(), e);
            throw e;
        }
    }
}
