package com.example.coursWork;

import com.example.coursWork.exception.QuestionAlreadyExistException;
import com.example.coursWork.exception.QuestionNotFoundException;
import com.example.coursWork.model.Question;
import com.example.coursWork.service.JavaQuestionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
public class JavaQuestionServiceTest {
    private final JavaQuestionService javaQuestionService = new JavaQuestionService();

    @Test
    public void add1Test(){
        javaQuestionService.add(new Question("test", "test"));

        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(() -> javaQuestionService.add(new Question("test", "test")));
        assertThat(javaQuestionService.getAll()).containsExactlyInAnyOrder(new Question("test", "test"));
    }

    @Test
    public void add2Test(){
        String question = "test";
        String answer = "test";
        Question q = new Question(question, answer);
        javaQuestionService.add(question, answer);

        Assertions.assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(() -> javaQuestionService.add(question, answer));
        assertThat(javaQuestionService.getAll()).containsExactlyInAnyOrder(q);
    }

    @Test
    public void removeTest(){
        javaQuestionService.add(new Question("test", "test"));
        javaQuestionService.remove(new Question("test", "test"));

        Assertions.assertThatExceptionOfType(QuestionNotFoundException.class).isThrownBy(() -> javaQuestionService.remove(new Question("test", "test")));
    }

    @ParameterizedTest
    @MethodSource("question")
    public void getRandomQuestionTest(Set<Question> question) {
        question.forEach(javaQuestionService::add);

        assertThat(javaQuestionService.getRandomQuestion()).isIn(javaQuestionService.getAll());
    }

    public static Stream<Arguments> question1(){
        return Stream.of(
                Arguments.of(new Question("Question", "Answer")));
    }

    public static Stream<Arguments> question2(){
        return Stream.of(
                Arguments.of("Question", "Answer"));
    }

    public static Stream<Arguments> question(){
        return Stream.of(
                Arguments.of(
                        Set.of(
                                new Question("Question1", "Answer1"),
                                new Question("Question2", "Answer2"),
                                new Question("Question3", "Answer3")
                        )
                )
        );
    }
}
