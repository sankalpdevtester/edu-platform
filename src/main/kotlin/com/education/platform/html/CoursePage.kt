import kotlinx.html.*
import kotlinx.html.stream.appendHTML

class CoursePage(private val course: Course) {
    fun render(): String {
        return buildString {
            appendHTML().html {
                head {
                    title("Course Page")
                }
                body {
                    h1 {
                        +course.name
                    }
                    p {
                        +course.description
                    }
                    ul {
                        course.students.forEach { student ->
                            li {
                                +student.name
                            }
                        }
                    }
                }
            }
        }
    }
}