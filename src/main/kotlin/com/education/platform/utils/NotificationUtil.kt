package com.education.platform.utils

import com.education.platform.models.Course
import com.education.platform.models.User
import com.education.platform.services.CourseService
import com.education.platform.services.UserService
import kotlinx.html.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class NotificationUtil {
    @Autowired
    private lateinit var courseService: CourseService

    @Autowired
    private lateinit var userService: UserService

    fun sendCourseUpdateNotification(courseId: Int, message: String) {
        val course = courseService.getCourseById(courseId)
        if (course != null) {
            val students = course.students
            students.forEach { student ->
                val user = userService.getUserById(student.id)
                if (user != null) {
                    val notificationHtml = buildNotificationHtml(course, message)
                    // Send email or notification to the user
                    println("Sending notification to ${user.email}: $notificationHtml")
                }
            }
        }
    }

    private fun buildNotificationHtml(course: Course, message: String): String {
        return html {
            head {
                title("Course Update Notification")
            }
            body {
                h1 { text("Course Update: ${course.name}") }
                p { text(message) }
                p {
                    a(href = "/courses/${course.id}") {
                        text("View Course")
                    }
                }
            }
        }.toString()
    }
}

class NotificationScheduler {
    @Autowired
    private lateinit var notificationUtil: NotificationUtil

    fun scheduleCourseUpdateNotifications() {
        val courses = notificationUtil.courseService.getAllCourses()
        courses.forEach { course ->
            val lastUpdated = course.lastUpdated
            if (lastUpdated != null && lastUpdated > Date(System.currentTimeMillis() - 86400000)) {
                notificationUtil.sendCourseUpdateNotification(course.id, "Course updated: ${course.name}")
            }
        }
    }
}