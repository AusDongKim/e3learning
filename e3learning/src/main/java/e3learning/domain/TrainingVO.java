package e3learning.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name="training")
@Table(name="training")
public class TrainingVO implements Serializable {

	@Id
	@Column(name="id_training")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment",strategy="increment")
	private int idTraining;
	
	@Column(name="training_state",nullable=false)
	private String trainingState;
	@Column(name="grade")
	private String grade;
	@Column(name="start_date",nullable=false,insertable=true,updatable=false)
	private String startDate;
	@Column(name="end_date",nullable=true,insertable=false)
	private String endDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="insert_dttm",columnDefinition="DATETIME",nullable=false,insertable=true)
	private Date insertDttm;
	@Column(name="update_dttm",columnDefinition="DATETIME")
	private Date updateDttm;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_account",nullable=false)
	//@JsonBackReference("atref")
	private AccountVO account;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_course",nullable=false)
	//@JsonBackReference("ctref")	
	private CourseVO course;
	
	public TrainingVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TrainingVO(AccountVO account, CourseVO course, String trainingState, String startDate) {
		//this.idAccount=idAccount;
		//this.idCourse=idCourse;
		this.account = account;
		this.course = course;
		this.trainingState=trainingState;
		this.startDate=startDate;
		this.insertDttm = new Date();
	}
	
	
	public int getIdTraining() {
		return idTraining;
	}
	public void setIdTraining(int idTraining) {
		this.idTraining = idTraining;
	}
	
	public String getTrainingState() {
		return trainingState;
	}
	public void setTrainingState(String trainingState) {
		this.trainingState = trainingState;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public AccountVO getAccount() {
		return account;
	}
	public void setAccount(AccountVO account) {
		this.account = account;
	}
	public CourseVO getCourse() {
		return course;
	}
	public void setCourse(CourseVO course) {
		this.course = course;
	}
	public TrainingVO addInit() {
		setInsertDttm(new Date());
		setTrainingState("I");
		return this;
	}
	

}
