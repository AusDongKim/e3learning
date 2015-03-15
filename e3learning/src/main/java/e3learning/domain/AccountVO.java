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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity(name="account")
@Table(name="account",indexes={@Index(columnList="email")})
public class AccountVO implements Serializable {

	@Id
	@Column(name="id_account")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment",strategy="increment")
	private int idAccount;

	@NotNull
	@Column(name="first_name",nullable=false)
	private String firstName;
	@NotNull
	@Column(name="last_name",nullable=false)
	private String lastName;
	
	@Pattern(regexp="^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$")
	@Column(name="email",nullable=false)
	private String email;
	
	@Column(name="address_street")
	private String addressStreet;
	
	@Column(name="address_suburb")
	private String addressSuburb;
	
	@Column(name="address_state")
	private String addressState;
	
	@Column(name="address_postcode")
	private String addressPostCode;
	
	@Column(name="address_country")
	private String addressCountry;
	
	@Column(name="active_state")
	private String activeState;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="insert_dttm",columnDefinition="DATETIME",nullable=false,updatable=false)
	@JsonIgnore
	private Date insertDttm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_dttm",columnDefinition="DATETIME",updatable=true)
	@JsonIgnore
	private Date updateDttm;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="account",cascade=CascadeType.REMOVE)
	//@JsonManagedReference("atref")
	@JsonIgnore
	List<TrainingVO> trains;
	
	public AccountVO() {
		super();
	}

	public AccountVO(String firstName, String lastName, String email,
			String addressStreet, String addressSuburb, String addressState, String addressPostCode, String addressCountry) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=email;
		this.addressStreet=addressStreet;
		this.addressSuburb=addressSuburb;
		this.addressState=addressState;
		this.addressPostCode=addressPostCode;
		this.addressCountry=addressCountry;
		this.activeState="A";
		this.insertDttm=new Date();
	}
	
	public AccountVO addInit() {
		setInsertDttm(new Date());
		setActiveState("A");
		return this;
	}

	public AccountVO addUpdate() {
		setUpdateDttm(new Date());
		return this;
	}
	
	
	public int getIdAccount() {
		return idAccount;
	}
	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}
	public String getAddressSuburb() {
		return addressSuburb;
	}
	public void setAddressSuburb(String addressSuburb) {
		this.addressSuburb = addressSuburb;
	}
	
	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	public String getAddressPostCode() {
		return addressPostCode;
	}
	public void setAddressPostCode(String addressPostCode) {
		this.addressPostCode = addressPostCode;
	}
	public String getAddressCountry() {
		return addressCountry;
	}
	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}
	public String getActiveState() {
		return activeState;
	}
	public void setActiveState(String activeState) {
		this.activeState = activeState;
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
}
