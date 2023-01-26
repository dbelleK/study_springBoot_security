package com.sp.fc.user.service;

import com.sp.fc.user.domain.School;
import com.sp.fc.user.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    //학교가 있는경우 없는경우
    public School save(School school){
        if(school.getSchoolId() == null){
            school.setCreated(LocalDateTime.now());
        }
        school.setUpdated(LocalDateTime.now());
        return schoolRepository.save(school);
    }

    public Optional<School> findSchool(Long shcoolId){
        return schoolRepository.findById(shcoolId);
    }

    public Page<School> list(int pageNum, int size){
        return schoolRepository.findAllByOrderByCreatedDesc(PageRequest.of(pageNum-1, size));
    }

    public List<School> getSchoolList(String city){
        return schoolRepository.findAllByCity(city);
    }

    // test2. 학교 이름을 수정한다.
    public Optional<School> updateName(Long schoolId, String name){
        return schoolRepository.findById(schoolId).map(school -> {
            school.setName(name);
            schoolRepository.save(school);
            return school;
        });
    }

    // test3. 지역 목록을 가져온다. (어느 지역의 학교인지)
    public List<String> cities(){
        return schoolRepository.getCities();
    }

    // test4. 지역으로 학교 목록을 가져온다.
    public List<School> findAllByCity(String city) {
        return schoolRepository.findAllByCity(city);
    }

    public long count() {
        return schoolRepository.count();
    }

}
