import com.education.platform.models.Course
import com.education.platform.modules.CourseModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CourseService {

    @Autowired
    private lateinit var courseModule: CourseModule

    @Transactional
    fun createCourse(course: Course): Course {
        return courseModule.createCourse(course)
    }

    @Transactional
    fun updateCourse(id: Long, course: Course): Course {
        return courseModule.updateCourse(id, course)
    }

    @Transactional
    fun deleteCourse(id: Long) {
        courseModule.deleteCourse(id)
    }

    fun getAllCourses(): List<Course> {
        return courseModule.getAllCourses()
    }

    fun getCourseById(id: Long): Course {
        return courseModule.getCourseById(id)
    }
}