package com.example.scms.service.impl;

import com.example.scms.dto.InstructorRequest;
import com.example.scms.dto.InstructorResponse;
import com.example.scms.entity.Instructor;
import com.example.scms.exception.DuplicateRecordException;
import com.example.scms.exception.ResourceNotFoundException;
import com.example.scms.repository.InstructorRepository;
import com.example.scms.service.InstructorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    @Override
    public InstructorResponse createInstructor(InstructorRequest instructorRequest) {
        log.info("Creating a new instructor with email: {}", instructorRequest.getEmail());
        if (instructorRepository.existsByEmail(instructorRequest.getEmail())) {
            log.error("Instructor with email {} already exists", instructorRequest.getEmail());
            throw new DuplicateRecordException("Instructor with this email already exists");
        }

        Instructor instructor = mapToEntity(instructorRequest);
        Instructor savedInstructor = instructorRepository.save(instructor);
        log.info("Instructor created successfully with ID: {}", savedInstructor.getId());
        return mapToResponse(savedInstructor);
    }

    @Override
    public List<InstructorResponse> getAllInstructors() {
        log.info("Fetching all instructors");
        return instructorRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InstructorResponse getInstructorById(Long id) {
        log.info("Fetching instructor with ID: {}", id);
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Instructor with ID {} not found", id);
                    return new ResourceNotFoundException("Instructor not found");
                });
        return mapToResponse(instructor);
    }

    @Override
    public InstructorResponse updateInstructor(Long id, InstructorRequest instructorRequest) {
        log.info("Updating instructor with ID: {}", id);
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Instructor with ID {} not found", id);
                    return new ResourceNotFoundException("Instructor not found");
                });

        if(!instructor.getEmail().equals(instructorRequest.getEmail()) && instructorRepository.existsByEmail(instructorRequest.getEmail())) {
            log.error("Instructor with email {} already exists", instructorRequest.getEmail());
            throw new DuplicateRecordException("Instructor with this email already exists");
        }

        instructor.setInstructorCode(instructorRequest.getInstructorCode());
        instructor.setName(instructorRequest.getName());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setPhone(instructorRequest.getPhone());
        instructor.setDepartment(instructorRequest.getDepartment());
        instructor.setDesignation(instructorRequest.getDesignation());

        Instructor updatedInstructor = instructorRepository.save(instructor);
        log.info("Instructor updated successfully with ID: {}", updatedInstructor.getId());
        return mapToResponse(updatedInstructor);
    }

    @Override
    public void deleteInstructor(Long id) {
        log.info("Deleting instructor with ID: {}", id);
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Instructor with ID {} not found", id);
                    return new ResourceNotFoundException("Instructor not found");
                });
        instructorRepository.delete(instructor);
        log.info("Instructor deleted successfully with ID: {}", id);
    }

    private InstructorResponse mapToResponse(Instructor instructor) {
        return new InstructorResponse(
                instructor.getId(),
                instructor.getInstructorCode(),
                instructor.getName(),
                instructor.getEmail(),
                instructor.getPhone(),
                instructor.getDepartment(),
                instructor.getDesignation()
        );
    }

    private Instructor mapToEntity(InstructorRequest instructorRequest) {
        Instructor instructor = new Instructor();
        instructor.setInstructorCode(instructorRequest.getInstructorCode());
        instructor.setName(instructorRequest.getName());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setPhone(instructorRequest.getPhone());
        instructor.setDepartment(instructorRequest.getDepartment());
        instructor.setDesignation(instructorRequest.getDesignation());
        return instructor;
    }
}
