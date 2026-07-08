package com.education.platform.utils

import com.education.platform.models.Course
import com.education.platform.models.Quiz
import com.education.platform.models.User
import com.education.platform.modules.CourseModule
import com.education.platform.modules.QuizModule

class QuizResultCalculator(private val courseModule: CourseModule, private val quizModule: QuizModule) {

    fun calculateQuizResult(quizId: Int, userId: Int): Double {
        val quiz = quizModule.getQuizById(quizId)
        val user = courseModule.getUserById(userId)
        val course = courseModule.getCourseById(quiz.courseId)

        if (quiz == null || user == null || course == null) {
            throw Exception("Quiz, user or course not found")
        }

        val quizQuestions = quizModule.getQuizQuestions(quizId)
        val userAnswers = quizModule.getUserAnswers(userId, quizId)

        var correctAnswers = 0
        for (question in quizQuestions) {
            val userAnswer = userAnswers.find { it.questionId == question.id }
            if (userAnswer != null && userAnswer.answer == question.correctAnswer) {
                correctAnswers++
            }
        }

        val quizResult = (correctAnswers.toDouble() / quizQuestions.size) * 100
        return quizResult
    }

    fun getQuizResultsForCourse(courseId: Int): Map<Int, Double> {
        val quizzes = quizModule.getQuizzesForCourse(courseId)
        val quizResults = mutableMapOf<Int, Double>()

        for (quiz in quizzes) {
            val quizResult = calculateQuizResult(quiz.id, quiz.userId)
            quizResults[quiz.id] = quizResult
        }

        return quizResults
    }

    fun getQuizResultsForUser(userId: Int): Map<Int, Double> {
        val quizzes = quizModule.getQuizzesForUser(userId)
        val quizResults = mutableMapOf<Int, Double>()

        for (quiz in quizzes) {
            val quizResult = calculateQuizResult(quiz.id, userId)
            quizResults[quiz.id] = quizResult
        }

        return quizResults
    }
}
```
```kotlin
// Example usage in CourseModule
class CourseModule {
    private val quizResultCalculator = QuizResultCalculator(this, QuizModule())

    fun getQuizResultsForCourse(courseId: Int): Map<Int, Double> {
        return quizResultCalculator.getQuizResultsForCourse(courseId)
    }

    fun getQuizResultsForUser(userId: Int): Map<Int, Double> {
        return quizResultCalculator.getQuizResultsForUser(userId)
    }
}
```
```kotlin
// Example usage in QuizModule
class QuizModule {
    private val quizResultCalculator = QuizResultCalculator(CourseModule(), this)

    fun calculateQuizResult(quizId: Int, userId: Int): Double {
        return quizResultCalculator.calculateQuizResult(quizId, userId)
    }
}