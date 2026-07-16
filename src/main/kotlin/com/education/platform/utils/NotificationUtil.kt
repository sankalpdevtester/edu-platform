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
                val notification = createNotificationHTML(course, message)
                // Send notification to student via email or other channels
                println("Sending notification to ${student.email}: $notification")
            }
        }
    }

    private fun createNotificationHTML(course: Course, message: String): String {
        return buildString {
            appendHTML().html {
                head {
                    title("Course Update")
                }
                body {
                    h1 { +course.name }
                    p { +message }
                    p { +"Course ID: ${course.id}" }
                    p { +"Updated at: ${Date()}" }
                }
            }
        }
    }

    fun sendWelcomeNotification(studentId: Int, courseId: Int) {
        val student = userService.getUserById(studentId)
        val course = courseService.getCourseById(courseId)
        if (student != null && course != null) {
            val notification = createWelcomeNotificationHTML(course, student)
            // Send notification to student via email or other channels
            println("Sending welcome notification to ${student.email}: $notification")
        }
    }

    private fun createWelcomeNotificationHTML(course: Course, student: User): String {
        return buildString {
            appendHTML().html {
                head {
                    title("Welcome to ${course.name}")
                }
                body {
                    h1 { +course.name }
                    p { +"Welcome, ${student.name}!" }
                    p { +"You have been enrolled in ${course.name}" }
                    p { +"Course ID: ${course.id}" }
                    p { +"Start date: ${course.startDate}" }
                }
            }
        }
    }
}