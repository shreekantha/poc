package in.emagna.poc.service;

import in.emagna.poc.domain.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher createTeacher(Teacher teacher);
    Teacher getTeacherById(Long id);
    List<Teacher> getAllTeachers();
    Teacher updateTeacher(Teacher teacher);
    void deleteTeacher(Long id);
}
