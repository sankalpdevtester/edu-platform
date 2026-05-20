package com.education.platform.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Column
import javax.persistence.UniqueConstraint
import javax.persistence.OneToMany
import javax.persistence.FetchType
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import java.util.ArrayList
import java.util.Date

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["username", "email"])])
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotBlank
    @Size(max = 20)
    @Column(name = "username", nullable = false)
    val username: String,

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email", nullable = false)
    val email: String,

    @NotBlank
    @Size(max = 120)
    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "role", nullable = false)
    val role: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: Date = Date(),

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val courses: MutableList<Course> = ArrayList()
)

@Entity
@Table(name = "courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotBlank
    @Size(max = 50)
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: Date = Date(),

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)

@Entity
@Table(name = "assignments")
data class Assignment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotBlank
    @Size(max = 50)
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: Date = Date(),

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    val course: Course
)