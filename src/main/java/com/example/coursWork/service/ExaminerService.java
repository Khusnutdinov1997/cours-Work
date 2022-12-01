package com.example.coursWork.service;

import com.example.coursWork.model.Question;
import org.springframework.stereotype.Service;

import java.util.Collection;


public interface ExaminerService {
    Collection<Question> getQuestion(int amount);
}
