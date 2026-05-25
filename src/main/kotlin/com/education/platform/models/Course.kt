package com.education.platform.models

import javax.persistence.*
import java.util.*

@Entity
@Table(name = "courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val courseCode: String,

    @Column
    val courseName: String,

    @Column
    val courseDescription: String,

    @OneToMany(mappedBy = "course", cascade = [CascadeType.ALL], orphanRemoval = true)
    val assignments: MutableList<Assignment> = mutableListOf(),

    @OneToMany(mappedBy = "course", cascade = [CascadeType.ALL], orphanRemoval = true)
    val quizzes: MutableList<Quiz> = mutableListOf(),

    @ManyToMany
    @JoinTable(
        name = "course_instructors",
        joinColumns = [JoinColumn(name = "course_id")],
        inverseJoinColumns = [JoinColumn(name = "instructor_id")]
    )
    val instructors: MutableList<User> = mutableListOf(),

    @ManyToMany
    @JoinTable(
        name = "course_students",
        joinColumns = [JoinColumn(name = "course_id")],
        inverseJoinColumns = [JoinColumn(name = "student_id")]
    )
    val students: MutableList<User> = mutableListOf()
) {
    fun addAssignment(assignment: Assignment) {
        assignments.add(assignment)
        assignment.course = this
    }

    fun addQuiz(quiz: Quiz) {
        quizzes.add(quiz)
        quiz.course = this
    }

    fun addInstructor(instructor: User) {
        instructors.add(instructor)
    }

    fun addStudent(student: User) {
        students.add(student)
    }
}

@Entity
@Table(name = "assignments")
data class Assignment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    val assignmentName: String,

    @Column
    val assignmentDescription: String,

    @ManyToOne
    @JoinColumn(name = "course_id")
    val course: Course? = null
) {
    fun getCourse(): Course? {
        return course
    }
}

@Entity
@Table(name = "quizzes")
data class Quiz(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    val quizName: String,

    @Column
    val quizDescription: String,

    @ManyToOne
    @JoinColumn(name = "course_id")
    val course: Course? = null
) {
    fun getCourse(): Course? {
        return course
    }
}