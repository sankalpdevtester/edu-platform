// CourseModule.kt
package com.education.platform.modules

import com.education.platform.models.Course
import com.education.platform.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class CourseService {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var userService: UserService

    fun createCourse(course: Course): Course {
        entityManager.persist(course)
        return course
    }

    fun getCourse(id: Long): Course? {
        return entityManager.find(Course::class.java, id)
    }

    fun updateCourse(id: Long, course: Course): Course? {
        val existingCourse = getCourse(id)
        if (existingCourse != null) {
            existingCourse.name = course.name
            existingCourse.description = course.description
            entityManager.merge(existingCourse)
            return existingCourse
        }
        return null
    }

    fun deleteCourse(id: Long) {
        val course = getCourse(id)
        if (course != null) {
            entityManager.remove(course)
        }
    }

    fun submitAssignment(assignment: Assignment) {
        val course = getCourse(assignment.courseId)
        if (course != null) {
            course.assignments.add(assignment)
            entityManager.merge(course)
        }
    }

    fun gradeAssignment(assignmentId: Long, grade: Double) {
        val assignment = entityManager.find(Assignment::class.java, assignmentId)
        if (assignment != null) {
            assignment.grade = grade
            entityManager.merge(assignment)
        }
    }
}

// Assignment.kt
package com.education.platform.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String = ""
    var description: String = ""
    var courseId: Long = 0

    @ManyToOne
    var course: Course? = null

    var grade: Double = 0.0
}

// Course.kt (updated)
package com.education.platform.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String = ""
    var description: String = ""

    @OneToMany(mappedBy = "course")
    var assignments: MutableList<Assignment> = mutableListOf()
}

// UserService.kt (updated)
package com.education.platform.services

import com.education.platform.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class UserService {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun getUser(id: Long): User? {
        return entityManager.find(User::class.java, id)
    }

    fun createUser(user: User): User {
        entityManager.persist(user)
        return user
    }
}