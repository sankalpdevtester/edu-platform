package com.education.platform.utils

import com.education.platform.models.User
import com.education.platform.models.Course
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class NotificationUtil {
    @Autowired
    private lateinit var javaMailSender: JavaMailSender

    fun sendWelcomeEmail(user: User) {
        val message = SimpleMailMessage()
        message.setTo(user.email)
        message.setSubject("Welcome to Education Platform")
        message.setText("Dear ${user.name},\n" +
                "Welcome to Education Platform. We are excited to have you on board.\n" +
                "Best regards,\n" +
                "Education Platform Team")
        javaMailSender.send(message)
    }

    fun sendCourseEnrollmentNotification(user: User, course: Course) {
        val message = SimpleMailMessage()
        message.setTo(user.email)
        message.setSubject("Course Enrollment Notification")
        message.setText("Dear ${user.name},\n" +
                "You have been enrolled in the course ${course.name}.\n" +
                "Best regards,\n" +
                "Education Platform Team")
        javaMailSender.send(message)
    }

    fun sendInAppNotification(user: User, message: String) {
        // Send in-app notification using a notification service
        println("Sending in-app notification to ${user.name}: $message")
    }

    fun sendQuizSubmissionNotification(user: User, course: Course, quizName: String) {
        val message = SimpleMailMessage()
        message.setTo(user.email)
        message.setSubject("Quiz Submission Notification")
        message.setText("Dear ${user.name},\n" +
                "You have submitted the quiz $quizName in the course ${course.name}.\n" +
                "Best regards,\n" +
                "Education Platform Team")
        javaMailSender.send(message)
    }

    fun sendAssignmentSubmissionNotification(user: User, course: Course, assignmentName: String) {
        val message = SimpleMailMessage()
        message.setTo(user.email)
        message.setSubject("Assignment Submission Notification")
        message.setText("Dear ${user.name},\n" +
                "You have submitted the assignment $assignmentName in the course ${course.name}.\n" +
                "Best regards,\n" +
                "Education Platform Team")
        javaMailSender.send(message)
    }
}