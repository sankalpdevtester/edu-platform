package com.education.platform.utils

import com.education.platform.models.Course
import com.education.platform.models.User
import com.education.platform.services.CourseService
import com.education.platform.services.UserService
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class NotificationUtil @Autowired constructor(
    private val courseService: CourseService,
    private val userService: UserService
) {

    fun sendCourseUpdateNotification(courseId: Int, message: String) {
        val course = courseService.getCourseById(courseId)
        if (course != null) {
            val students = course.students
            students.forEach { student ->
                sendNotification(student, course, message)
            }
        }
    }

    private fun sendNotification(student: User, course: Course, message: String) {
        val notificationHtml = buildString {
            appendHTML().html {
                head {
                    title("Course Update")
                }
                body {
                    h1 { +course.name }
                    p { +message }
                }
            }
        }
        // Send email or notification using a notification service
        println("Sending notification to ${student.email}: $notificationHtml")
    }

    fun sendWelcomeNotification(student: User, course: Course) {
        val welcomeMessage = "Welcome to ${course.name}! We're excited to have you on board."
        sendNotification(student, course, welcomeMessage)
    }
}

fun main() {
    val notificationUtil = NotificationUtil(
        CourseService(),
        UserService()
    )
    val courseId = 1
    val message = "New course material has been added."
    notificationUtil.sendCourseUpdateNotification(courseId, message)
}
``}