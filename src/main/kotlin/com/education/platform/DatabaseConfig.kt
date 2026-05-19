package com.education.platform

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackages = ["com.education.platform.repository"])
@EnableTransactionManagement
class DatabaseConfig {

    @Autowired
    private lateinit var environment: Environment

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"))
        dataSource.setUrl(environment.getProperty("spring.datasource.url"))
        dataSource.setUsername(environment.getProperty("spring.datasource.username"))
        dataSource.setPassword(environment.getProperty("spring.datasource.password"))
        return dataSource
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactory = LocalContainerEntityManagerFactoryBean()
        entityManagerFactory.setDataSource(dataSource())
        entityManagerFactory.setPackagesToScan("com.education.platform.model")
        val jpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter)
        val properties = HashMap<String, Any>()
        properties["hibernate.hbm2ddl.auto"] = environment.getProperty("spring.jpa.hibernate.ddl-auto")
        properties["hibernate.dialect"] = environment.getProperty("spring.jpa.properties.hibernate.dialect")
        properties["hibernate.show_sql"] = environment.getProperty("spring.jpa.show-sql")
        properties["hibernate.format_sql"] = environment.getProperty("spring.jpa.format-sql")
        entityManagerFactory.setJpaProperties(properties)
        return entityManagerFactory
    }

    @Bean
    fun transactionManager(): JpaTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject())
        return transactionManager
    }
}
``` 
```kotlin
package com.education.platform.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val description: String
)
``` 
```kotlin
package com.education.platform.repository

import com.education.platform.model.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Long>
``` 
```kotlin
package com.education.platform

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ComprehensiveEducationPlatformApplication

fun main() {
    SpringApplication.run(ComprehensiveEducationPlatformApplication::class.java, *args)
}