package logic;

import java.util.ArrayList;

public class Principle extends User {

	private ArrayList<TimeChangeRequest> timeChangeList;
	
	public Principle(String userName, String Password, String Name) /*Constructor/*/
	{
		super(userName, Password, Name);
		this.timeChangeList=new ArrayList<TimeChangeRequest>();
		// TODO Auto-generated constructor stub
	}
	public Principle(Principle p)/*Copy constructor/*/ 
	{
		super(p.getUserName(),p.getPassword(),p.getName());
		timeChangeList = new ArrayList<TimeChangeRequest>();
		timeChangeList=p.timeChangeList;
	}

	
	public ArrayList<TimeChangeRequest> getTimeChangeList() {
		/*Getter for all time change requests/*/
		return timeChangeList;
	}

	public void setTimeChangeList(TimeChangeRequest TimeChangeRequest) {
		/*Add new time change request for the list of request
		 * (If there is a new request for TimeChange we update it on database and add it to principle's time change requests list)/*/
		timeChangeList.add(TimeChangeRequest);
	}
	
	public boolean getTimeChangeRequest(TimeChangeRequest t)
	{
		/*The principle can check if request for time change is exist or not in order to approve it or not/*/
		
		for (int i=0;i<timeChangeList.size();i++) {
			if (t.getId()==timeChangeList.get(i).getId()) {
				return true;
			}
		}
		return false;
	}


	
	

}