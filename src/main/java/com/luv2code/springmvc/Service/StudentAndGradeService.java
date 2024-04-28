package com.luv2code.springmvc.Service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.studentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private studentDao studentdao;

    public void createStudent(String firstName, String lastName, String emailAddress){
        CollegeStudent student = new CollegeStudent(firstName,lastName,emailAddress);
        student.setId(1);
        studentdao.save(student);
    }

    public boolean checkIfStudetnIsNull(int id){
        Optional<CollegeStudent> student = studentdao.findById(id);

        if(student.isPresent()){
            return true;
        }
        return false;
    }

    public void deleteStudent(int i) {
        if(checkIfStudetnIsNull(i)){
            studentdao.deleteById(i);
        }
    }

    public Iterable<CollegeStudent> getGradebook() {
        Iterable<CollegeStudent> collegeStudents = studentdao.findAll();
        return collegeStudents;


    }
}
