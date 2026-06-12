package com.education.platform.utils

import com.education.platform.models.User
import com.education.platform.modules.CourseModule
import com.education.platform.modules.QuizModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class NotificationUtil {
    @Autowired
    private lateinit var javaMailSender: JavaMailSender

    @Autowired
    private lateinit var courseModule: CourseModule

    @Autowired
    private lateinit var quizModule: QuizModule

    fun sendCourseNotification(courseId: Int, message: String) {
        val course = courseModule.getCourseById(courseId)
        val students = course.students
        students.forEach { student ->
            sendEmail(student.email, "Course Notification", message)
            sendInAppMessage(student.id, message)
        }
    }

    fun sendQuizNotification(quizId: Int, message: String) {
        val quiz = quizModule.getQuizById(quizId)
        val students = quiz.students
        students.forEach { student ->
            sendEmail(student.email, "Quiz Notification", message)
            sendInAppMessage(student.id, message)
        }
    }

    private fun sendEmail(to: String, subject: String, body: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.setSubject(subject)
        message.setText(body)
        javaMailSender.send(message)
    }

    private fun sendInAppMessage(userId: Int, message: String) {
        // In-app messaging implementation
        // For simplicity, we'll just print the message to the console
        println("In-app message sent to user $userId: $message")
    }
}
```

```kotlin
// Example usage in CourseModule.kt
package com.education.platform.modules

import com.education.platform.utils.NotificationUtil

class CourseModule {
    @Autowired
    private lateinit var notificationUtil: NotificationUtil

    fun createCourse(course: Course) {
        // Create course logic...
        notificationUtil.sendCourseNotification(course.id, "Course created successfully")
    }
}
```

```kotlin
// Example usage in QuizModule.kt
package com.education.platform.modules

import com.education.platform.utils.NotificationUtil

class QuizModule {
    @Autowired
    private lateinit var notificationUtil: NotificationUtil

    fun createQuiz(quiz: Quiz) {
        // Create quiz logic...
        notificationUtil.sendQuizNotification(quiz.id, "Quiz created successfully")
    }
}