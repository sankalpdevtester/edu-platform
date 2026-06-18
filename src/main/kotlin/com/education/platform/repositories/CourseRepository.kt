import com.education.platform.models.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.teacher.id = :teacherId")
    fun findByTeacherId(@Param("teacherId") teacherId: Long): List<Course>

    @Query("SELECT c FROM Course c WHERE c.students.id = :studentId")
    fun findByStudentId(@Param("studentId") studentId: Long): List<Course>

    fun findById(id: Long): Course?
}