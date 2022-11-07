package com.testehan.hibernate.basics.mappings.relationships.manyToMany.intermediaryEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "COURSE_STUDENT")
@org.hibernate.annotations.Immutable    // Declares class immutable..not modifiable after creation
public class CourseStudent {

    /*
    The primary key of the link table is the composite of CATEGORY_ID and ITEM_ID. Hence, the entity class
    also has a composite key, which you encapsulate in a static nested embeddable component class for convenience
     You can externalize this class into its own file, of course
     */
    @Embeddable
    public static class Id implements Serializable {    // Encapsulates composite key
        @Column(name = "COURSE_ID")
        protected int courseId;
        @Column(name = "STUDENT_ID")
        protected int studentId;

        public Id() {
        }
        public Id(int courseId, int studentId) {
            this.courseId = courseId;
            this.studentId = studentId;
        }

        public int getCourseId() {
            return courseId;
        }

        public int getStudentId() {
            return studentId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;
            Id id = (Id) o;
            return getCourseId() == id.getCourseId() && getStudentId() == id.getStudentId();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getCourseId(), getStudentId());
        }
    }

    @EmbeddedId      // maps the identifier property and its composite key columns to the entity’s table
    protected Id id = new Id();

    // another advantage of having a class to a link table used in many-to-many association is that you can have
    // other fields that might be relevant
    @Column(updatable = false, nullable = false)
    protected Date addedOn = new Date();


    /*
         Then two @ManyToOne properties, category G and item H, map columns that are
          already mapped in the identifier. The trick here is to make them read-only, with the
          updatable=false, insertable=false setting. This means Hibernate writes the values
          of these columns by taking the identifier value of CourseStudent
     */
    @ManyToOne
    @JoinColumn(
            name = "COURSE_ID",
            insertable = false, updatable = false)
    protected Course course;

    @ManyToOne
    @JoinColumn(
            name = "STUDENT_ID",
            insertable = false, updatable = false)
    protected Student student;

    /*
        You can also see that constructing a CourseStudent involves setting the values
        of the identifier—the application always assigns composite key values; Hibernate
        doesn’t generate them. Pay extra attention to the constructor and how it sets the field
        values and guarantees referential integrity by managing collections on both sides of
        the association
     */
    public CourseStudent(Course course, Student student) {
        this.course = course;
        this.student = student;

        this.id.courseId = course.getCourseId();
        this.id.studentId = student.getStudentId();

        // Guarantees referential integrity if made bidirectiona
        course.getCourseStudents().add(this);
        student.getCourseStudents().add(this);
    }

    // for hibernate only
    public CourseStudent(){}

    public Course getCourse() {
        return course;
    }


    public Student getStudent() {
        return student;
    }

}
