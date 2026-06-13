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

    fun sendNotification(user: User, course: Course, message: String) {
        // Send email notification
        val email = SimpleMailMessage()
        email.setTo(user.email)
        email.setSubject("Notification from ${course.name}")
        email.setText(message)
        javaMailSender.send(email)

        // Send in-app notification
        // Assuming we have a notification repository
        val notificationRepository = NotificationRepository()
        val notification = Notification(user, course, message)
        notificationRepository.save(notification)
    }

    fun sendAssignmentNotification(user: User, course: Course, assignmentName: String, dueDate: String) {
        val message = "Assignment $assignmentName is due on $dueDate for course ${course.name}"
        sendNotification(user, course, message)
    }

    fun sendQuizNotification(user: User, course: Course, quizName: String, dueDate: String) {
        val message = "Quiz $quizName is due on $dueDate for course ${course.name}"
        sendNotification(user, course, message)
    }
}

class NotificationRepository {
    fun save(notification: Notification) {
        // Save notification to database
        // For simplicity, we'll assume we have a MySQL database
        val dbConnection = DatabaseConfig().getConnection()
        val statement = dbConnection.prepareStatement("INSERT INTO notifications (user_id, course_id, message) VALUES (?, ?, ?)")
        statement.setInt(1, notification.user.id)
        statement.setInt(2, notification.course.id)
        statement.setString(3, notification.message)
        statement.executeUpdate()
        dbConnection.close()
    }
}

class Notification(val user: User, val course: Course, val message: String)

// Example usage
fun main() {
    val notificationUtil = NotificationUtil()
    val user = User(1, "John Doe", "john@example.com")
    val course = Course(1, "Math 101")
    notificationUtil.sendNotification(user, course, "Hello from Math 101!")
}