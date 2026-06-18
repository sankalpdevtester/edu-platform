import com.education.platform.models.Course
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CourseDto(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {

    companion object {
        fun fromCourse(course: Course): CourseDto {
            return CourseDto(
                id = course.id,
                name = course.name,
                description = course.description,
                createdAt = course.createdAt.toString(),
                updatedAt = course.updatedAt.toString()
            )
        }
    }
}