package com.education.platform.utils

import com.education.platform.models.User
import com.education.platform.models.Course
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import javax.mail.internet.InternetAddress

@Component
class NotificationUtil {
    @Autowired
    private lateinit var javaMailSender: JavaMailSender

    fun sendEmailNotification(user: User, subject: String, message: String) {
        val mailMessage = SimpleMailMessage()
        mailMessage.setFrom("education.platform@example.com")
        mailMessage.setTo(user.email)
        mailMessage.setSubject(subject)
        mailMessage.setText(message)
        javaMailSender.send(mailMessage)
    }

    fun sendInAppNotification(user: User, message: String) {
        // Implement in-app notification logic here
        println("Sending in-app notification to user ${user.id} with message: $message")
    }

    fun notifyCourseEnrollment(user: User, course: Course) {
        val subject = "Enrolled in Course: ${course.name}"
        val message = "Dear ${user.name}, you have been enrolled in the course ${course.name}."
        sendEmailNotification(user, subject, message)
        sendInAppNotification(user, "You have been enrolled in the course ${course.name}.")
    }

    fun notifyAssignmentSubmission(user: User, assignmentName: String) {
        val subject = "Assignment Submission: $assignmentName"
        val message = "Dear ${user.name}, you have submitted the assignment $assignmentName."
        sendEmailNotification(user, subject, message)
        sendInAppNotification(user, "You have submitted the assignment $assignmentName.")
    }

    fun notifyQuizResult(user: User, quizName: String, score: Int) {
        val subject = "Quiz Result: $quizName"
        val message = "Dear ${user.name}, you have scored $score in the quiz $quizName."
        sendEmailNotification(user, subject, message)
        sendInAppNotification(user, "You have scored $score in the quiz $quizName.")
    }
}