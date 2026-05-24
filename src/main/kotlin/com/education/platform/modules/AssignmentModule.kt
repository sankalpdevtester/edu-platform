// src/main/kotlin/com/education/platform/modules/AssignmentModule.kt

package com.education.platform.modules

import com.education.platform.models.Assignment
import com.education.platform.models.Course
import com.education.platform.models.User
import com.education.platform.repositories.AssignmentRepository
import com.education.platform.repositories.CourseRepository
import com.education.platform.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class AssignmentModule {

    @Autowired
    private lateinit var assignmentRepository: AssignmentRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @PostMapping("/assignments")
    fun createAssignment(@RequestBody assignment: Assignment): Assignment {
        val course = courseRepository.findById(assignment.courseId).orElseThrow()
        assignment.course = course
        return assignmentRepository.save(assignment)
    }

    @GetMapping("/assignments/{id}")
    fun getAssignment(@PathVariable id: Long): Assignment {
        return assignmentRepository.findById(id).orElseThrow()
    }

    @PutMapping("/assignments/{id}")
    fun updateAssignment(@PathVariable id: Long, @RequestBody assignment: Assignment): Assignment {
        val existingAssignment = assignmentRepository.findById(id).orElseThrow()
        existingAssignment.name = assignment.name
        existingAssignment.description = assignment.description
        existingAssignment.dueDate = assignment.dueDate
        return assignmentRepository.save(existingAssignment)
    }

    @DeleteMapping("/assignments/{id}")
    fun deleteAssignment(@PathVariable id: Long) {
        assignmentRepository.deleteById(id)
    }

    @PostMapping("/assignments/{id}/submissions")
    fun submitAssignment(@PathVariable id: Long, @RequestBody submission: AssignmentSubmission): AssignmentSubmission {
        val assignment = assignmentRepository.findById(id).orElseThrow()
        val user = userRepository.findById(submission.userId).orElseThrow()
        submission.assignment = assignment
        submission.user = user
        return assignmentRepository.saveSubmission(submission)
    }

    @GetMapping("/assignments/{id}/submissions")
    fun getSubmissions(@PathVariable id: Long): List<AssignmentSubmission> {
        val assignment = assignmentRepository.findById(id).orElseThrow()
        return assignmentRepository.getSubmissions(assignment)
    }

    @PutMapping("/assignments/{id}/submissions/{submissionId}")
    fun gradeSubmission(@PathVariable id: Long, @PathVariable submissionId: Long, @RequestBody grade: Grade): Grade {
        val assignment = assignmentRepository.findById(id).orElseThrow()
        val submission = assignmentRepository.getSubmission(submissionId)
        submission.grade = grade
        return assignmentRepository.saveSubmission(submission)
    }
}

data class AssignmentSubmission(
    val id: Long? = null,
    val userId: Long,
    val assignmentId: Long,
    val submission: String,
    val grade: Grade? = null
)

data class Grade(
    val score: Double,
    val feedback: String
)