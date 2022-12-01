package com.example.coursWork;

import com.example.coursWork.exception.NotEnoughQuestionsException;
import com.example.coursWork.model.Question;
import com.example.coursWork.service.ExaminerServiceImpl;
import com.example.coursWork.service.JavaQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {
    @Mock
    private JavaQuestionService javaQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final List<Question> javaQuestions = new ArrayList<>();

    @BeforeEach
    public void beforeEach(){
        javaQuestions.clear();

        javaQuestions.addAll(
                Stream.of(
                        new Question("Вопрос по java - 1", "Ответ по java - 1"),
                        new Question("Вопрос по java - 2", "Ответ по java - 2"),
                        new Question("Вопрос по java - 3", "Ответ по java - 3")
                ).collect(Collectors.toSet())
        );
        when(javaQuestionService.getAll()).thenReturn(javaQuestions);
    }

    @Test
    public void getQuestionNegativeTest(){
        assertThatExceptionOfType(NotEnoughQuestionsException.class)
                .isThrownBy(() -> examinerService.getQuestion(-1));
    }

    @Test
    public void getQuestionPositiveTest(){
        when(javaQuestionService.getRandomQuestion()).thenReturn(new Question("Вопрос по java - 1", "Ответ по java - 1"));

        assertThat(examinerService.getQuestion(1))
                .hasSize(1);
    }
}
