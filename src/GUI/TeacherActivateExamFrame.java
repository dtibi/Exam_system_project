package GUI;


import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import logic.ActiveExam;
import logic.Exam;
import logic.Globals;
import logic.Teacher;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;


	
public class TeacherActivateExamFrame implements ControlledScreen  {
	
		@FXML Label ExamId;
		@FXML Label Durationid;
		@FXML Label Field;
		@FXML Label CourseId;
		@FXML TextField Examcode;
		@FXML RadioButton ComputerizedExamId;
		@FXML RadioButton MonualExamId;
		@FXML Button Cancel;
		@FXML Button Active;
		//@FXML JFXDate Date;
		Exam examview;	
		int type;
		@Override
		public void runOnScreenChange() {
			ToggleGroup SelectType = new ToggleGroup();
			ExamId.setText("Exam id : "+examview.examIdToString());
			Durationid.setText("Duration: "+ examview.getDuration());
			Field.setText("Field: "+ examview.getField().getName());
			CourseId.setText("Course: "+ examview.getCourse().getName());
			ComputerizedExamId.setSelected(true);
			ComputerizedExamId.setId("whiteLabel");
			ComputerizedExamId.setToggleGroup(SelectType);
			MonualExamId.setToggleGroup(SelectType);
			MonualExamId.setId("whiteLabel");
		}

		public void setExam(Exam e) {
			examview=e;
			
		}
		@FXML
		public void ActiveButtonPressed(ActionEvent event)
		{
			String code=new String(Examcode.getText());
			if(Examcode.getText()==null)
			{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exam code error");
			alert.setHeaderText(null);
			alert.setContentText("You must put exam code in the right place!! ");
			alert.showAndWait();
			}
			else if(Examcode.getText().length()!=4)
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Exam code error");
				alert.setHeaderText(null);
				alert.setContentText("You must assign a 4-digit code");
				alert.showAndWait();
				
			}
			else
			{
				if(isValid(Examcode.getText()) && ActiveExamController.getActiveExam(Examcode.getText())==null)
				{
					if(ComputerizedExamId.isSelected())
					{
						type=1;
						ActiveExam activeExam = new ActiveExam(code,type,new Date(new java.util.Date().getTime()),examview,(Teacher) ClientGlobals.client.getUser());
						ActiveExamController.InitializeActiveExam(activeExam);
					}
						
					else
					{
						type=0;
						ActiveExam activeExam = new ActiveExam(code,type,new Date(new java.util.Date().getTime()),examview,(Teacher) ClientGlobals.client.getUser());
						ActiveExamController.InitializeActiveExam(activeExam);
						//ActiveExamController.CreateDocFile(activeExam);Word Files
					}
					
					 Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
					
				}
				else
				{
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Exam code error");
					alert.setHeaderText(null);
					alert.setContentText("You must change the exam code\n" 
					+"because the code exists or the code does not contain letters or numbers");
					alert.showAndWait();
					
				}
			}
			
		}
		public boolean isValid(String s) {
		    String n = ".*[0-9].*";
		    String A = ".*[A-Z].*";
		    String a = ".*[a-z].*";
		    return s.matches(n) &&( s.matches(a) || s.matches(A));
		}
		
		@FXML
	    public void CancelButtonPressed(ActionEvent event)
			    {
			     Globals.mainContainer.setScreen(ClientGlobals.TeacherInitializeExamID);
			    }
	

		

	}


