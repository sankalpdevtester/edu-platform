package com.education.platform.utils

import com.education.platform.models.Course
import com.education.platform.models.User
import com.education.platform.services.CourseService
import com.education.platform.services.UserService
import kotlinx.html.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.util.Properties

@Component
class NotificationUtil {
    @Autowired
    private lateinit var courseService: CourseService

    @Autowired
    private lateinit var userService: UserService

    fun sendCourseUpdateNotification(courseId: Int) {
        val course = courseService.getCourseById(courseId)
        if (course != null) {
            val students = course.students
            students.forEach { student ->
                sendEmailNotification(student, course)
            }
        }
    }

    private fun sendEmailNotification(student: User, course: Course) {
        val properties = Properties()
        properties["mail.smtp.host"] = "smtp.gmail.com"
        properties["mail.smtp.port"] = "587"
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"

        val session = Session.getInstance(properties, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
                return javax.mail.PasswordAuthentication("your-email@gmail.com", "your-password")
            }
        })

        try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress("your-email@gmail.com"))
            message.setRecipients(Message.RecipientType.TO, InternetAddress(student.email))
            message.subject = "Course Update: ${course.name}"
            message.setContent(buildHtmlContent(course), "text/html; charset=utf-8")
            Transport.send(message)
        } catch (e: MessagingException) {
            println("Error sending email notification: ${e.message}")
        }
    }

    private fun buildHtmlContent(course: Course): String {
        return buildString {
            appendHTML().html {
                head {
                    title("Course Update")
                }
                body {
                    h1 { +course.name }
                    p { +course.description }
                    a(href = "/courses/${course.id}") { +course.name }
                }
            }
        }
    }
}