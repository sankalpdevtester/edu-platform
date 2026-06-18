import com.education.platform.models.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.id = :id")
    fun findCourseById(@Param("id") id: Long): Course?

    @Query("SELECT c FROM Course c WHERE c.name LIKE %:name%")
    fun findCoursesByName(@Param("name") name: String): List<Course>

    @Query("SELECT c FROM Course c WHERE c.description LIKE %:description%")
    fun findCoursesByDescription(@Param("description") description: String): List<Course>
}