package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logic.Globals;
import ocsf.client.ClientGlobals;


public class TeacherMainFrame implements Initializable,ControlledScreen {
	private ScreensController myController;
	@FXML Button manageQuestionsB;
	@FXML Button manageExamsB;
	@FXML Button initiateExamB;
	@FXML Button lockExamB;
	@FXML Button requestTCB;
	@FXML Button generateRB;
	@FXML Button checkExamB;
	@FXML ListView<String> ActiveExamList;
	@FXML ListView<String> CompletedExamList;
	@FXML Tab myInfoTab;
	@FXML TabPane infoTabPane;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScreenParent(ScreensController parent) {
		myController = parent;
	}

	@Override
	public void runOnScreenChange() {
		ActiveExamController.getTeachersActiveExams(ClientGlobals.client.getUser());
		Globals.primaryStage.setHeight(700);
		Globals.primaryStage.setWidth(800);
	}
	
	@FXML
	public void gotToManageQuestions(ActionEvent event) {
		
	}
	
	@FXML
	public void goToManageExams(ActionEvent event) {
		
	}
	
	@FXML
	public void goToInitiateExam(ActionEvent event) {
		
	}
	
	@FXML
	public void lockExamClicked(ActionEvent event) {
		
	}
	
	@FXML
	public void requestTimeChangeClicked(ActionEvent event) {
		
	}
	
	@FXML
	public void goToGenerateReportClicked(ActionEvent event) {
		
	}
	
	@FXML
	public void goToCheckExams(ActionEvent event) {
		
	}
	
	@FXML
	public void completeExamsListViewClicked(ActionEvent event) {
		
	}
	
	@FXML
	public void activeExamsListViewClicked(ActionEvent event) {
		
	}

}
