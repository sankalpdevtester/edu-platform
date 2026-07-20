package com.education.platform.utils

import com.education.platform.models.User
import com.education.platform.models.Course
import com.education.platform.services.CourseService
import com.education.platform.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.util.*

@Component
class NotificationUtil {

    @Autowired
    private lateinit var courseService: CourseService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mailSender: JavaMailSender

    fun sendCourseCreationNotification(course: Course) {
        val users = userService.findAllUsers()
        users.forEach { user ->
            sendEmail(user, "New Course Created: ${course.name}", "A new course has been created: ${course.name}")
            sendInAppNotification(user, "New Course Created: ${course.name}", "A new course has been created: ${course.name}")
        }
    }

    fun sendAssignmentSubmissionNotification(assignmentId: Int, courseId: Int) {
        val course = courseService.findCourseById(courseId)
        val users = userService.findAllUsers()
        users.forEach { user ->
            sendEmail(user, "New Assignment Submission: ${course.name}", "A new assignment has been submitted for course: ${course.name}")
            sendInAppNotification(user, "New Assignment Submission: ${course.name}", "A new assignment has been submitted for course: ${course.name}")
        }
    }

    private fun sendEmail(user: User, subject: String, body: String) {
        val message = SimpleMailMessage()
        message.setTo(user.email)
        message.setSubject(subject)
        message.setText(body)
        mailSender.send(message)
    }

    private fun sendInAppNotification(user: User, title: String, message: String) {
        // Implement in-app notification logic here
        println("In-app notification sent to user: ${user.username} - $title: $message")
    }
}