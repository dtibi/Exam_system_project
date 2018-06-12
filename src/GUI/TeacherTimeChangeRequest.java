package GUI;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.ActiveExam;
import logic.Globals;
import logic.Teacher;
import logic.TimeChangeRequest;
import ocsf.client.ClientGlobals;

public class TeacherTimeChangeRequest implements Initializable, ControlledScreen{

	@FXML TextField SelectNewTime;
	@FXML TextField RequestExplenation;
	@FXML Label Errortime;
	@FXML Label Errorrequest;
	ActiveExam activeexamselect;
	
	@Override
	public void runOnScreenChange() 
	{
		Globals.primaryStage.setHeight(700); 
		Globals.primaryStage.setWidth(650);	
		SelectNewTime.setText("");
		RequestExplenation.setText("");
		Errorrequest.setVisible(false);
		Errortime.setVisible(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	@FXML
	private void SubmitRequest (ActionEvent event)
	{
		//If some of the fields is empty, then the teacher get an error.
		if(SelectNewTime.getText().isEmpty()) {
			Errortime.setVisible(true);
		}
		else if(RequestExplenation.getText().isEmpty())
		{
			 Errorrequest.setVisible(true);
		}
	    else
	    {
	    	TimeChangeRequest tc=new TimeChangeRequest((Long.valueOf(SelectNewTime.getText())),RequestExplenation.getText(),false,activeexamselect,(Teacher) ClientGlobals.client.getUser());
	    	System.out.println(tc);
	    	ActiveExamController.requestNewTimeChangeForActiveExam(tc);
	    	Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	    }
	}

	@FXML
	public void CancelButtonPressed(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}

	public void SetActiveExam(ActiveExam selectedItem) {
		activeexamselect=selectedItem;
		
	}



}





