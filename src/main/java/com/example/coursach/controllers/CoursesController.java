package com.example.coursach.controllers;

import com.example.coursach.dto.CourseDto;
import com.example.coursach.dto.CourseDtoWithMentors;
import com.example.coursach.dto.PaginationCoursesDto;
import com.example.coursach.dto.PaginationCoursesWithMentorsDto;
import com.example.coursach.dto.picture.StatusDto;
import com.example.coursach.entity.enums.FilterBy;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.security.model.AuthorizedUser;
import com.example.coursach.service.CourseService;
import com.example.coursach.service.MinioStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("courses")
public class CoursesController {
    private final CourseService courseService;
    private final MinioStorageService minioStorageService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Long createCourse(@RequestBody CourseDto courseDto) {
       return courseService.createCourse(courseDto);
    }

//    @PutMapping
//    public Long update2(@RequestBody CourseDto courseDto) {
//       return courseService.updateCourse(courseDto);
//    }

    @PostMapping("/picture")
    @PreAuthorize("hasRole('ADMIN')")
    public StatusDto uploadPicture(@RequestParam("file") MultipartFile picture, @RequestParam("courseId") Long id) {
        return  minioStorageService.uploadCourseObj(picture,id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CourseDtoWithMentors get(@PathVariable("id") Long id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return courseService.getById(id,authorizedUser.getUuid());
    }

    @GetMapping("/byRole")
    @ResponseBody
    public PaginationCoursesWithMentorsDto getByRole(@RequestParam("number") Integer number,
                                                     @RequestParam("size") Integer size,
                                                     @RequestParam("role")String role,
                                                     @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return courseService.getAllByRole(number,size, authorizedUser.getUuid(), UserRole.valueOf(role));
    }

    @GetMapping("/all")
    @ResponseBody
    public PaginationCoursesDto getAll(@RequestParam("number") Integer number, @RequestParam("size") Integer size) {
        return courseService.getAllWithPagination(number,size);
    }

    @GetMapping("filter/{filter}")
    @ResponseBody
    public PaginationCoursesDto getByFiltering(@RequestParam("number") Integer number,
                                    @RequestParam("size") Integer size,
                                    @PathVariable("filter") String filter) {
        return courseService.getAllWithPaginationWithFiltering(number,size,FilterBy.valueOf(filter));
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@RequestBody CourseDto courseDto) {
        courseService.update(courseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable("id") Long id) {
         courseService.delete(id);
    }
}
