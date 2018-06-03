package logic;

import java.io.Serializable;
import java.sql.Time;

/**
 * This Class is created to manage Time Change Requests made by Teachers in the system and approved by principles
 * @author Group-12
 *
 */
public class TimeChangeRequest implements Serializable{

	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = -1250807178967814960L;
	/**
	 * Time change request ID
	 */
	private int id;
	/**
	 * the New Time requested by the teacher to override the exam duration time
	 */
	private Time newTime;
	/**
	 * a String explaining why a time change is necessary to justify to the principle the request
	 */
	private String reasonForTimeChange;
	/**
	 * this holds the Status of the request if it was approved by the principle or not
	 */
	private boolean status;
	/**
	 * Active Exam holds the Active Exam that the teachers requested to update it time to the newTime variable
	 */
	private ActiveExam activeExam;
	/**
	 * the Teacher responsible for the time change request
	 */
	private Teacher teacher;
	
	/**
	 * Constructor will build an objet of this type TimeChangeRequest
	 * @param id - id of the request
	 * @param newTime - the new time requested to be updated to
	 * @param reason - the reason for the request
	 * @param status - the status of the request (aproved/notYetAprroved)
	 * @param activeExam - the ActiveExan to change the time to
	 * @param t - the teacher repsonsible for the time change
	 */
	public TimeChangeRequest(int id,Time newTime,String reason,boolean status,ActiveExam activeExam,Teacher t)
	{
		this.id=id;
		this.newTime=newTime;
		this.reasonForTimeChange=reason;
		this.status=status;
		this.activeExam=activeExam;
		this.teacher=t;
	}
	
	/**
	 * copy constructor
	 * @param time change request to copy
	 */
	public TimeChangeRequest(TimeChangeRequest t)/*Copy constructor/*/
	{
		id=t.id;
		newTime=t.newTime;
		reasonForTimeChange=new String(t.reasonForTimeChange);
		status=t.status;
		activeExam=new ActiveExam(t.activeExam);
		teacher=new Teacher(t.teacher);
	}
	
	/**
	 * 
	 * @return the id of the request
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * 
	 * @return the new time request value
	 */
	public Time getNewTime() {
		return this.newTime;
	}
	
	/**
	 * 
	 * @return the reson for the time change requested submitted by the teacher
	 */
	public String getReasonForTimeChange() {
		return this.reasonForTimeChange;
	}
	
	/**
	 * 
	 * @return the status of the request if it was approved or not yet
	 */
	public boolean getStatus() {
		return this.status;
	}
	
	/**
	 * 
	 * @returnthe Active Exam of with this timechangerequest belongs to
	 */
	public ActiveExam getActiveExam() {
		return this.activeExam;
	}
	/**
	 * 
	 * @return the teacher responsible for the time change request
	 */
	public Teacher getTeacher()
	{
		return this.teacher;
	}
}
