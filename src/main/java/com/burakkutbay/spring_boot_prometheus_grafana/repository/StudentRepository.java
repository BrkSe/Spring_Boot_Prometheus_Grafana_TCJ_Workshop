package com.burakkutbay.spring_boot_prometheus_grafana.repository;

import com.burakkutbay.spring_boot_prometheus_grafana.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
