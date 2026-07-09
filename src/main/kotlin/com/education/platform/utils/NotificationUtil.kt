package com.education.platform.utils

import com.education.platform.models.Course
import com.education.platform.models.User
import com.education.platform.services.CourseService
import com.education.platform.services.UserService
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class NotificationUtil {
    @Autowired
    private lateinit var courseService: CourseService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mailSender: JavaMailSender

    fun sendCourseUpdateNotification(courseId: Long) {
        val course = courseService.getCourseById(courseId)
        val students = course.students

        students.forEach { student ->
            val message = SimpleMailMessage()
            message.setTo(student.email)
            message.setSubject("Course Update: ${course.name}")
            message.setText(buildNotificationEmailBody(course, student))

            mailSender.send(message)
        }
    }

    private fun buildNotificationEmailBody(course: Course, student: User): String {
        return buildString {
            appendHTML().html {
                body {
                    h2 { text("Course Update: ${course.name}") }
                    p { text("Dear ${student.name},") }
                    p { text("This is to inform you that the course ${course.name} has been updated.") }
                    p { text("Please visit the course page to view the latest updates.") }
                    a(href = "/courses/${course.id}") { text("View Course") }
                }
            }
        }
    }
}
```

```kotlin
// Example usage in CourseController
package com.education.platform.controllers

import com.education.platform.utils.NotificationUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CourseController {
    @Autowired
    private lateinit var courseService: CourseService

    @Autowired
    private lateinit var notificationUtil: NotificationUtil

    @PutMapping("/courses/{id}")
    fun updateCourse(@PathVariable id: Long) {
        courseService.updateCourse(id)
        notificationUtil.sendCourseUpdateNotification(id)
    }
}