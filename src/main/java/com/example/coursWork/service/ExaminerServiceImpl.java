package com.example.coursWork.service;

import com.example.coursWork.exception.NotEnoughQuestionsException;
import com.example.coursWork.model.Question;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestion(int amount){
        if(questionService.getAll().size() < amount || amount < 1){
            throw new NotEnoughQuestionsException();
        }
        Set<Question> result = new HashSet<>();
        while (result.size() < amount){
            result.add(questionService.getRandomQuestion());
        }
        return result;
    }
}
