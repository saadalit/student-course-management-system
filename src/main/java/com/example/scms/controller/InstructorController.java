package com.example.scms.controller;

import com.example.scms.dto.InstructorRequest;
import com.example.scms.dto.InstructorResponse;
import com.example.scms.service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping
    public ResponseEntity<InstructorResponse> createInstructor(@Valid @RequestBody InstructorRequest instructorRequest) {
        InstructorResponse instructorResponse = instructorService.createInstructor(instructorRequest);
        return new ResponseEntity<>(instructorResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<InstructorResponse>> getAllInstructors(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String instructorCode,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String designation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<InstructorResponse> instructors = instructorService.getAllInstructors(
                id, name, instructorCode, email, department, designation, pageable
        );
        return ResponseEntity.ok(instructors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorResponse> getInstructorById(@PathVariable Long id) {
        InstructorResponse instructorResponse = instructorService.getInstructorById(id);
        return ResponseEntity.ok(instructorResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorResponse> updateInstructor(@PathVariable Long id, @Valid @RequestBody InstructorRequest instructorRequest) {
        InstructorResponse instructorResponse = instructorService.updateInstructor(id, instructorRequest);
        return ResponseEntity.ok(instructorResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.ok("Instructor deleted successfully");
    }
}