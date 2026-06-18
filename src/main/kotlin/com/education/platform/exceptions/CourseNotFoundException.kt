import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class CourseNotFoundException(message: String) : RuntimeException(message) {

    companion object {
        fun withId(id: Long): CourseNotFoundException {
            return CourseNotFoundException("Course with id $id not found")
        }
    }
}