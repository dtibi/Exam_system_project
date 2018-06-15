package GUI;

import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.ExamController;
import Controllers.QuestionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import logic.Exam;
import logic.Field;
import logic.Globals;
import logic.Question;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrincipalViewDataFrame implements Initializable , ControlledScreen {

    @FXML private TabPane m_dataTabPane;
    @FXML private Tab m_studentsTab;
    @FXML private ListView<String> m_studentsList;
    @FXML private Tab m_teachersTab;
    @FXML private ListView<String> m_teachersList;
    @FXML private Tab m_questionsTab;
    @FXML private ListView<String> m_questionsList;
    @FXML private Tab m_examsTab;
    @FXML private ListView<String> m_examsList;
    @FXML private Tab m_fieldsTab;
    @FXML private ListView<String> m_fieldsList;
    @FXML private Tab m_coursesTab;
    @FXML private ListView<String> m_coursesList;
    @FXML private TextField m_searchBox;
    @FXML private Button m_searchBtn;
    @FXML private Button m_backToMainBtn;

    private HashMap<Integer, Question> m_questionsMap;
    private HashMap<Integer, Exam> m_examsMap;
    private HashMap<Integer, Field> m_fieldsMap;

    @Override
    public void runOnScreenChange() {
        Globals.primaryStage.setHeight(445);
        Globals.primaryStage.setWidth(515);
    }

    @FXML
    public void viewData(ActionEvent event){

        PrincipalViewQuestionFrame viewQuestionFrame = (PrincipalViewQuestionFrame) Globals.mainContainer.getController(ClientGlobals.PrincipalViewQuestionID);
        PrincipalViewExamFrame viewExamFrame = (PrincipalViewExamFrame) Globals.mainContainer.getController(ClientGlobals.PrincipalViewExamID);

        if (!m_questionsList.getSelectionModel().isEmpty()){
            m_searchBox.setText("");
            String questionToBeDisplayed = m_questionsList.getSelectionModel().getSelectedItem();
            String[] splitedQuestion = questionToBeDisplayed.split(" ");
            viewQuestionFrame.setQuestion(m_questionsMap.get(Integer.parseInt(splitedQuestion[1])));
            Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewQuestionID);
        }else if(!m_examsList.getSelectionModel().isEmpty()){
            m_searchBox.setText("");
            String examToBeDisplayed = m_examsList.getSelectionModel().getSelectedItem();
            String[] splitedExam = examToBeDisplayed.split(" ");
            viewExamFrame.setExam(m_examsMap.get(Integer.parseInt(splitedExam[1])));
            Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewExamID);
        }
        if(!m_searchBox.getText().equals("")){
            if(isNumeric(m_searchBox.getText())){
                if (m_questionsMap.containsKey(Integer.parseInt(m_searchBox.getText()))) {
                    viewQuestionFrame.setQuestion(m_questionsMap.get(Integer.parseInt(m_searchBox.getText())));
                    Globals.mainContainer.setScreen(ClientGlobals.PrincipalViewQuestionID);
                }else if (m_examsMap.containsKey(Integer.parseInt(m_searchBox.getText()))){
                    viewExamFrame.setExam(m_examsMap.get(Integer.parseInt(m_searchBox.getText())));
                    Globals.mainContainer.setScreen(ClientGlobals.TeacherViewExamID);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "There is no such Entry in DataBase", ButtonType.OK);
                    Optional<ButtonType> result = alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You used Invalid characters, please enter Numerical ID.", ButtonType.OK);
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }

    @FXML
    public void backToMainMenu(ActionEvent event){
        Globals.mainContainer.setScreen(ClientGlobals.PrincipalMainID);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        m_questionsMap = new HashMap<>();
        m_examsMap = new HashMap<>();
        m_fieldsMap = new HashMap<>();
    }

    // method handles selection of text box in which you enter id to manually search for data ( selection disabled on the current tab )
    @FXML
    public void onTextBoxMouseClick(MouseEvent mouseEvent) {
        Node tabContent = m_dataTabPane.getSelectionModel().getSelectedItem().getContent();
        ListView currentListView = (ListView)tabContent;
        currentListView.getSelectionModel().clearSelection();
    }

    @FXML
    public void onStudentsTabSelection(Event event) {
        if(!m_studentsList.getSelectionModel().isEmpty())
            m_studentsList.getSelectionModel().clearSelection();
    }

    @FXML
    public void onTeachersTabSelection(Event event) {
        if(!m_teachersList.getSelectionModel().isEmpty())
            m_teachersList.getSelectionModel().clearSelection();
    }

    @FXML
    public void onQuestionsTabSelection(Event event) {
        if(m_questionsList.getItems().isEmpty())
            updateQuestionsList();
        if(!m_questionsList.getSelectionModel().isEmpty())
            m_questionsList.getSelectionModel().clearSelection();
    }

    @FXML
    public void onExamsTabSelection(Event event) {
        if(m_examsList.getItems().isEmpty())
            updateExamsList();
        if(!m_examsList.getSelectionModel().isEmpty())
            m_examsList.getSelectionModel().clearSelection();
    }

    @FXML
    public void onFieldsTabSelection(Event event) {
        if(m_fieldsList.getItems().isEmpty())
            updateFieldsList();
        if(!m_fieldsList.getSelectionModel().isEmpty())
            m_fieldsList.getSelectionModel().clearSelection();
    }

    @FXML
    public void onCoursesTabSelection(Event event) {
        if(!m_coursesList.getSelectionModel().isEmpty())
            m_coursesList.getSelectionModel().clearSelection();
    }

    /**
     *  method checks if the entered id is a number
     * @param str - ID entered manually by the user
     * @return True - string is a number, false - string has non-numerical characters in it
     */
    public static boolean isNumeric(String str)
    {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    private void updateQuestionsList() {
        ArrayList<Question> m_questionsToBeDisplayed = QuestionController.getAllQuestions();
        ArrayList<String> basicQuestionInfo = new ArrayList<>();
        if (m_questionsToBeDisplayed != null) {
            for (Question question : m_questionsToBeDisplayed) {
                m_questionsMap.put(Integer.parseInt(question.questionIDToString()), question);
                String questionInfo = "QuestionID: " + question.questionIDToString() + " | " + question.getQuestionString();
                basicQuestionInfo.add(questionInfo);
            }
            m_questionsList.getItems().clear();
            ObservableList<String> list = FXCollections.observableArrayList(basicQuestionInfo);
            m_questionsList.setItems(list);
        }
    }

    private void updateExamsList() {
        ArrayList<Exam> m_examsToBeDisplayed = ExamController.getAllExams();
        ArrayList<String> basicExamInfo = new ArrayList<>();
        if (m_examsToBeDisplayed != null) {
            for (Exam exam : m_examsToBeDisplayed) {
                m_examsMap.put(Integer.parseInt(exam.examIdToString()), exam);
                String questionInfo = "ExamID: " + exam.examIdToString() + " | Course: " + exam.getCourse().getName() + " Field: " + exam.getField().getName();
                basicExamInfo.add(questionInfo);
            }
        }
        m_examsList.getItems().clear();
        ObservableList<String> list1 = FXCollections.observableArrayList(basicExamInfo);
        m_examsList.setItems(list1);
    }

    private void updateFieldsList() {
        ArrayList<Field> m_fieldsToBeDisplayed = CourseFieldController.getAllFields();
        ArrayList<String> basicFieldInfo = new ArrayList<>();
        if (m_fieldsToBeDisplayed != null) {
            for (Field field : m_fieldsToBeDisplayed) {
                m_fieldsMap.put(Integer.parseInt(field.fieldIdToString()), field);
                String fieldInfo = "FieldID: " + field.fieldIdToString() + " | " + field.getName();
                basicFieldInfo.add(fieldInfo);
            }
        }
        m_fieldsList.getItems().clear();
        ObservableList<String> list1 = FXCollections.observableArrayList(basicFieldInfo);
        m_fieldsList.setItems(list1);
    }
}