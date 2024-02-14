package com.org.studentmanagement.service.impl;

import com.org.studentmanagement.dto.CourseDTO;
import com.org.studentmanagement.dto.PaginationDTO;
import com.org.studentmanagement.exception.CourseAlreadyExistsException;
import com.org.studentmanagement.model.Course;
import com.org.studentmanagement.repository.CourseRepository;
import com.org.studentmanagement.repository.CourseRepositoryPagination;
import com.org.studentmanagement.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepositoryPagination courseRepositoryPagination;
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepositoryPagination courseRepositoryPagination, CourseRepository courseRepository) {
        this.courseRepositoryPagination = courseRepositoryPagination;
        this.courseRepository = courseRepository;
    }

    @Override
    public Page<Course> getAllCourses(PaginationDTO paginationDTO) {
        Sort sort = Sort.by(paginationDTO.getSortDirection(), paginationDTO.getSortBy());
        Pageable pageable = PageRequest.of(paginationDTO.getPageNumber(),
                paginationDTO.getPageSize(), sort);
        return courseRepositoryPagination.findAll(pageable);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepositoryPagination.findById(id);
    }


    @Transactional
    public List<Course> saveCourses(List<CourseDTO> courseDtos) {

        List<Course> newCourses = courseDtos.stream()
                .map(this::convertCourseDtoToCourse)
                .collect(Collectors.toList());

        // Verify if any of the course already exist by Name
        List<Course> existingCourses = courseRepository.findAllByNameIgnoreCaseIn(newCourses.stream()
                        .map(Course::getName)
                        .collect(Collectors.toList()));

        if (!existingCourses.isEmpty()) {
            throw new CourseAlreadyExistsException("Courses already exist with the following Names: " + existingCourses);
        }

        // Save all new courses
         return courseRepository.saveAll(newCourses);
    }

    private Course convertCourseDtoToCourse(CourseDTO courseDTO){
        Course course = new Course();
        course.setCost(courseDTO.getCost());
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setStudentLimit(courseDTO.getStudentLimit());
        return course;
    }


}
