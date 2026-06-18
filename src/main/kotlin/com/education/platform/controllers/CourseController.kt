import com.education.platform.models.Course
import com.education.platform.modules.CourseModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseController {

    @Autowired
    private lateinit var courseModule: CourseModule

    @GetMapping
    fun getAllCourses(): ResponseEntity<List<Course>> {
        val courses = courseModule.getAllCourses()
        return ResponseEntity.ok(courses)
    }

    @GetMapping("/{id}")
    fun getCourseById(@PathVariable id: Long): ResponseEntity<Course> {
        val course = courseModule.getCourseById(id)
        return if (course != null) {
            ResponseEntity.ok(course)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createCourse(@RequestBody course: Course): ResponseEntity<Course> {
        val newCourse = courseModule.createCourse(course)
        return ResponseEntity.status(HttpStatus.CREATED).body(newCourse)
    }

    @PutMapping("/{id}")
    fun updateCourse(@PathVariable id: Long, @RequestBody course: Course): ResponseEntity<Course> {
        val updatedCourse = courseModule.updateCourse(id, course)
        return if (updatedCourse != null) {
            ResponseEntity.ok(updatedCourse)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable id: Long): ResponseEntity<Void> {
        courseModule.deleteCourse(id)
        return ResponseEntity.noContent().build()
    }
}