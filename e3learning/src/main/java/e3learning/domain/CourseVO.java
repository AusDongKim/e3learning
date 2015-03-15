package e3learning.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="course")
@Table(name="course",
	   indexes={@Index(columnList="course_title")},
	   uniqueConstraints={@UniqueConstraint(columnNames={"course_title"})})
public class CourseVO implements Serializable {

	@Id
	@Column(name="id_course")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment",strategy="increment")
	private int idCourse;
	@Column(name="course_title",nullable=false)
	private String courseTitle;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="insert_dttm",nullable=false,updatable=false,insertable=true)
	private Date insertDttm;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_dttm",updatable=true)
	private Date updateDttm;

	@OneToMany(fetch=FetchType.EAGER,mappedBy="course",cascade=CascadeType.REMOVE)
	//@JsonManagedReference("ctref")
	@JsonIgnore
	private List<TrainingVO> trains;
	
	public CourseVO(String courseTitle) {
		this.courseTitle=courseTitle;
		this.insertDttm=new Date();
	}
	public CourseVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(int idCourse) {
		this.idCourse = idCourse;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public Date getInsertDttm() {
		return insertDttm;
	}

	public void setInsertDttm(Date insertDttm) {
		this.insertDttm = insertDttm;
	}

	public Date getUpdateDttm() {
		return updateDttm;
	}

	public void setUpdateDttm(Date updateDttm) {
		this.updateDttm = updateDttm;
	}
	public List<TrainingVO> getTrains() {
		return trains;
	}
	public void setTrains(List<TrainingVO> trains) {
		this.trains = trains;
	}
	public CourseVO addInit() {
		this.insertDttm=new Date();
		return this;
	}
	

}
