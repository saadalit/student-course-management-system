package com.example.scms.service.impl;

import com.example.scms.dto.StudentRequest;
import com.example.scms.dto.StudentResponse;
import com.example.scms.entity.Student;
import com.example.scms.exception.DuplicateRecordException;
import com.example.scms.exception.ResourceNotFoundException;
import com.example.scms.repository.StudentRepository;
import com.example.scms.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponse createStudent(StudentRequest studentRequest){
        log.info("Creating a new student with code: {}", studentRequest.getStudentCode());
        if(studentRepository.existsByStudentCode(studentRequest.getStudentCode())){
            log.error("Student with code {} already exists", studentRequest.getStudentCode());
            throw new DuplicateRecordException("Student with this code already exists");
        }
        if(studentRepository.existsByEmail(studentRequest.getEmail())){
            log.error("Student with email {} already exists", studentRequest.getEmail());
            throw new DuplicateRecordException("Student with this email already exists");
        }

        Student student = mapToEntity(studentRequest);
        Student savedStudent = studentRepository.save(student);
        log.info("Student created successfully with ID: {}", savedStudent.getId());
        return mapToResponse(savedStudent);
    }

    @Override
    public List<StudentResponse> getAllStudents(){
        log.info("Fetching all students");
        return studentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponse getStudentById(Long id){
        log.info("Fetching student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student with ID {} not found", id);
                    return new ResourceNotFoundException("Student not found");
                });
        return mapToResponse(student);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest studentRequest){
        log.info("Updating student with ID: {}", id);
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student with ID {} not found", id);
                    return new ResourceNotFoundException("Student not found");
                });

        if(!existingStudent.getStudentCode().equals(studentRequest.getStudentCode()) &&
                studentRepository.existsByStudentCode(studentRequest.getStudentCode())){
            log.error("Student with code {} already exists", studentRequest.getStudentCode());
            throw new DuplicateRecordException("Student with this code already exists");
        }
        if(!existingStudent.getEmail().equals(studentRequest.getEmail()) &&
                studentRepository.existsByEmail(studentRequest.getEmail())){
            log.error("Student with email {} already exists", studentRequest.getEmail());
            throw new DuplicateRecordException("Student with this email already exists");
        }

        existingStudent.setFirstName(studentRequest.getFirstName());
        existingStudent.setLastName(studentRequest.getLastName());
        existingStudent.setEmail(studentRequest.getEmail());
        existingStudent.setPhone(studentRequest.getPhone());
        existingStudent.setDateOfBirth(studentRequest.getDateOfBirth());
        existingStudent.setGender(studentRequest.getGender());
        existingStudent.setStatus(studentRequest.getStatus());

        Student updatedStudent = studentRepository.save(existingStudent);
        log.info("Student updated successfully with ID: {}", updatedStudent.getId());
        return mapToResponse(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id){
        log.info("Deleting student with ID: {}", id);
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student with ID {} not found", id);
                    return new ResourceNotFoundException("Student not found");
                });
        studentRepository.delete(existingStudent);
        log.info("Student deleted successfully with ID: {}", id);
    }

    @Override
    public List<StudentResponse> searchStudentsByName(String name){
        log.info("Searching students by name: {}", name);
        return studentRepository.findByFirstNameIgnoreCaseOrLastNameIgnoreCase(name, name).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private StudentResponse mapToResponse(Student student) {
        return new StudentResponse(student.getId(),
                student.getStudentCode(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getDateOfBirth(),
                student.getGender(),
                student.getStatus());
    }

    private Student mapToEntity(StudentRequest studentRequest) {
        Student student = new Student();
        student.setStudentCode(studentRequest.getStudentCode());
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());
        student.setDateOfBirth(studentRequest.getDateOfBirth());
        student.setGender(studentRequest.getGender());
        student.setStatus(studentRequest.getStatus());
        return student;
    }
}