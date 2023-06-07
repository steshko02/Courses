package com.example.coursach.service;

import com.example.coursach.converters.WorkConverter;
import com.example.coursach.dto.LessonDtoWithMentors;
import com.example.coursach.dto.WorkDto;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.Resource;
import com.example.coursach.entity.User;
import com.example.coursach.entity.Work;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.repository.LessonRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final LessonRepository lessonRepository;
    private final WorkConverter workConverter;
    private final WorkRepository workRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Long createWork(WorkDto workDto, String uuid) throws AccessDeniedException {

        Lesson lesson = lessonRepository.findById(workDto.getLessonId())
                .orElseThrow(RuntimeException::new);

        Optional<User> userById = userRepository.findUserById(uuid);

        if(userService.getByRoleOnCourse(uuid,lesson.getId(), UserRole.LECTURER).isEmpty()
                && userById.get().getRoles().stream().noneMatch(x->x.getName().equals(UserRole.ADMIN))){
            throw new AccessDeniedException("You has not permission for this operation");
        }

        Work newEntity = workConverter.toEntity(workDto);
        if(lesson.getWork()==null){
             workRepository.save(newEntity);
            newEntity.setLesson(lesson);
            lesson.setWork(newEntity);
            lessonRepository.save(lesson);
            return newEntity.getId();
        }else {
            Work work = lesson.getWork();
            work.setDeadline(newEntity.getDeadline());
            work.setDescription(newEntity.getDescription());
            work.setStatus(newEntity.getStatus());
            work.setTitle(newEntity.getTitle());
            workRepository.save(work);
            work.setResources(workDto.getResource().stream().map(resourceDto ->
                    Resource.builder()
                            .id(resourceDto.getId())
                            .url(resourceDto.getUrl())
                            .filename(resourceDto.getFilename())
                            .extension(resourceDto.getExtension())
                            .filename(resourceDto.getFilename())
                            .build()
            ).collect(Collectors.toList()));
            return workRepository.save(work).getId();
        }
    }

    public void deleteWork(Long id, String uuid) {

        workRepository.deleteById(id);
    }

    public void update(WorkDto workDto, String uuid) throws AccessDeniedException {
        Lesson lesson = lessonRepository.findById(workDto.getLessonId())
                .orElseThrow(RuntimeException::new);
        Optional<User> userById = userRepository.findUserById(uuid);

        if(userService.getByRoleOnCourse(uuid,lesson.getId(), UserRole.LECTURER).isEmpty()
                && userById.get().getRoles().stream().noneMatch(x->x.getName().equals(UserRole.ADMIN))){
            throw new AccessDeniedException("You has not permission for this operation");
        }

        Work work = workConverter.toEntity(workDto);
        work.setId(workDto.getId());

        work.setResources(workDto.getResource().stream().map(resourceDto ->
                        Resource.builder()
                                .id(resourceDto.getId())
                                .url(resourceDto.getUrl())
                                .filename(resourceDto.getFilename())
                                .extension(resourceDto.getExtension())
                                .filename(resourceDto.getFilename())
                                .build()
                ).collect(Collectors.toList()));

        workRepository.save(work);
    }

    public void checkAndSwitchStatus(){
        LocalDateTime now = LocalDateTime.now();
        workRepository.updateWorkByTime(now);
    }

    public WorkDto getBylessId(Long lessId, String uuid) {

        return lessonRepository.findById(lessId).map(Lesson::getWork).map(workConverter::toDto).orElse(WorkDto.builder().build());
    }
}
