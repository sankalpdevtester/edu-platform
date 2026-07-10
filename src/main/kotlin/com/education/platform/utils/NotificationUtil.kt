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

    fun sendCourseUpdateNotification(courseId: Long, message: String) {
        val course = courseService.getCourseById(courseId)
        if (course != null) {
            val students = course.students
            students.forEach { student ->
                sendNotification(student, course, message)
            }
        }
    }

    private fun sendNotification(student: User, course: Course, message: String) {
        val html = buildString {
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
        // Send email using a mail service (e.g. Spring Mail)
        // For simplicity, this example just prints the notification to the console
        println("Sending notification to ${student.email}: $html")
    }

    fun sendWelcomeNotification(student: User, course: Course) {
        val html = buildString {
            appendHTML().html {
                head {
                    title("Welcome to ${course.name}")
                }
                body {
                    h1 { +course.name }
                    p { +"Welcome to the course! You have been successfully enrolled." }
                }
            }
        }
        // Send email using a mail service (e.g. Spring Mail)
        // For simplicity, this example just prints the notification to the console
        println("Sending welcome notification to ${student.email}: $html")
    }
}
```
```kotlin
// Example usage in CourseController
package com.education.platform.controllers

import com.education.platform.models.Course
import com.education.platform.models.User
import com.education.platform.services.CourseService
import com.education.platform.utils.NotificationUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CourseController @Autowired constructor(
    private val courseService: CourseService,
    private val notificationUtil: NotificationUtil
) {

    @PostMapping("/courses/{courseId}/update")
    fun updateCourse(@PathVariable courseId: Long, @RequestBody course: Course) {
        courseService.updateCourse(courseId, course)
        notificationUtil.sendCourseUpdateNotification(courseId, "The course has been updated.")
    }

    @PostMapping("/courses/{courseId}/enroll")
    fun enrollStudent(@PathVariable courseId: Long, @RequestBody student: User) {
        courseService.enrollStudent(courseId, student)
        notificationUtil.sendWelcomeNotification(student, courseService.getCourseById(courseId)!!)
    }
}