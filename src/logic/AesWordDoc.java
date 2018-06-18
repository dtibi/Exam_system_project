package logic;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.ArrayList;

public class AesWordDoc extends XWPFDocument implements Serializable {
	//comment
	private String description=null;
	private String fileName=null;	
	private int size=0;
	public  byte[] bytes;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9193230217538325507L;

	public AesWordDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AesWordDoc(InputStream is) throws IOException {
		super(is);
		// TODO Auto-generated constructor stub
	}

	public AesWordDoc(OPCPackage pkg) throws IOException {
		super(pkg);
		// TODO Auto-generated constructor stub
	}

	public void initArray(int size)
	{
		bytes = new byte [size];	
	}
	
	public AesWordDoc( String fileName) {
		this.fileName = fileName;
	}
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getbytes() {
		return bytes;
	}
	
	public byte getbyte(int i) {
		return bytes[i];
	}

	public void setbytes(byte[] bytes) {
		
		for(int i=0;i<bytes.length;i++)
		this.bytes[i] = bytes[i];
	}

	public String getdescription() {
		return description;
	}

	public void setdescription(String description) {
		description = description;
	}
	public MyFile CreateWordFile(ActiveExam activeExam,String path) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
	    writer.write("\t\t"+activeExam.getExam().getCourse().getName()+"\n");
	    writer.write("\tField: "+activeExam.getExam().getField().getName()+"\n");
	    writer.write("\tDate: "+activeExam.getDate()+"\n\n");
		
	    ArrayList<QuestionInExam> questionsInExam=activeExam.getExam().getQuestionsInExam();
	    int questionIndex=1;
		for(QuestionInExam qie:questionsInExam)//Sets all questions with their info on screen.
		{
			if(qie.getStudentNote()!=null)
			{
				writer.write("Note:" + qie.getStudentNote()+"\n");
			}
			writer.write(questionIndex+". "+qie.getQuestionString()+" ("+qie.getPointsValue()+" Points)\n");
			questionIndex++;
			for(int i=1;i<5;i++) {
				writer.write(i+". "+qie.getAnswer(i)+"\n");
			}
			questionIndex++;
		}
		writer.write("\n\nGood Luck!");
	    writer.close();
		return null;
		
		
		
		//doc.write(out);
		//out.close();
		//return doc;
	}

/*	
	public void OpenSaveFileDialog(ActiveExam activeExam) throws IOException
	{
		FileChooser saveWindow = new FileChooser();
		saveWindow.setTitle("Save exam");
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("docx files (*.docx)", "*.docx");
        saveWindow.getExtensionFilters().add(extFilter);
         
         //Show save file dialog
         File file = saveWindow.showSaveDialog(Globals.primaryStage);
         
         if(file != null)
             SaveFile(file, CreateWordFile(activeExam));
         
        
	}
	/*/
	/**
	 * Show for the user a save dialog where he can pick his saving path for the word file.
	 * @param file
	 * @param doc
	 * @throws IOException
	 */
	/*
	 private void SaveFile(File file, XWPFDocument doc) throws IOException{

            OutputStream outputStream = new FileOutputStream(file);
            doc.write(outputStream);
            outputStream.flush();
            outputStream.close();
            
            Alert alert=new Alert(AlertType.INFORMATION);
 			alert.setTitle("Download Succeed");
 			alert.setHeaderText(null);
 			alert.setContentText("You can open it and start your exam!");
 			alert.showAndWait();
 			
 }

	 
		/*/
	 /**
	  * When the student pressed on submit button and the exam is manual,he has to upload his exam back to the system.
	  */
	 	public AesWordDoc OpenUploadWordFileDialog() {
	 		// TODO Auto-generated method stub
	 		AesWordDoc doc;
	  		FileChooser Uploadwindow=new FileChooser();
	  		Uploadwindow.setTitle("Upload your exam");
	  		File file=Uploadwindow.showOpenDialog(Globals.primaryStage);
	  		if(file!=null)
	  			try {
	  				
	  				doc=new AesWordDoc(new FileInputStream(file)) ;
	  				//FileOutputStream out = new FileOutputStream(new File(file.getName())+".docx");
	  				//doc.write(out);
	  				//out.close();
	  				
	  				XWPFWordExtractor extract=new XWPFWordExtractor(doc);
	  				System.out.println("Your solved exam: \n"+extract.getText());
	  				
	  				
	  				System.out.println(file.getName());
	  				//return file.getName();
	  				doc.setFileName(file.getName());
	  				return doc;
	  				
	  			} catch (FileNotFoundException e) {
	  				// TODO Auto-generated catch block
	  				e.printStackTrace();
	  			} catch (IOException e) {
	  				// TODO Auto-generated catch block
	  				e.printStackTrace();
	  			}
	  		return null;
	 	}


	 
	 
}
