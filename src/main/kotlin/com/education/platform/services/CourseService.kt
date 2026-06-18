import com.education.platform.models.Course
import com.education.platform.modules.CourseModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CourseService {

    @Autowired
    private lateinit var courseModule: CourseModule

    fun getAllCourses(): List<Course> {
        return courseModule.getAllCourses()
    }

    fun getCourseById(id: Long): Course? {
        return courseModule.getCourseById(id)
    }

    fun createCourse(course: Course): Course {
        return courseModule.createCourse(course)
    }

    fun updateCourse(id: Long, course: Course): Course? {
        return courseModule.updateCourse(id, course)
    }

    fun deleteCourse(id: Long) {
        courseModule.deleteCourse(id)
    }

    fun getCourseByTeacherId(teacherId: Long): List<Course> {
        return courseModule.getCourseByTeacherId(teacherId)
    }

    fun getCourseByStudentId(studentId: Long): List<Course> {
        return courseModule.getCourseByStudentId(studentId)
    }
}