package application;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
public class controller {
	public controller() {
		createConnection();
	}
	 Connection con;
	void createConnection() {
		try {
		Class.forName("com.mysql.jdbc.Driver");
		con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sms","root","qwerty123");
		}
		catch(ClassNotFoundException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,ex);
		}
		catch(SQLException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,ex);
		}
		
	}
	
		//sign up function 
	public void signup(ActionEvent event) {
		try {
			Stage primaryStage=new Stage();		//scene created for signup page
			Parent root=FXMLLoader.load(getClass().getResource("/application/SignUpPage.fxml"));
			Scene scene = new Scene(root,685,510);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	@FXML public TextField n1,r1,d1,idgen;
	public PasswordField p1;
	public void generate(ActionEvent event) {	//to generate unique 5 digit ID for each student
		String name,dob,gen;
		try {
		 name=n1.getText();		 
		 dob=d1.getText();		 
		 String s1[]=dob.split("/");
		 String s2[]=name.split(" ");
		 gen=s2[0].substring(0,2)+s1[0]+s1[1];
		 idgen.setText(gen);		
		}
		catch(Exception e){idgen.setText("ERROR");}	
		 	 }
	
	
	@FXML public RadioButton rb1;
	@FXML public RadioButton rb2;
	@FXML public RadioButton rb3;
	@FXML public RadioButton rb4;
	public void enroll(ActionEvent event) {
		try{
		String name=n1.getText();
		String regd=r1.getText();
		String dob=d1.getText();
		String pass=p1.getText();
		String gen=idgen.getText();
		String branch="";
		if(rb1.isSelected())
			branch=rb1.getText();
		else if(rb2.isSelected())
			branch=rb2.getText();
		else if(rb3.isSelected())
			branch=rb3.getText();
		else
			branch=rb4.getText();
		Statement stmt=(Statement) con.createStatement();
		String dbop="INSERT INTO DATA(id,password,dob,name,regd,course) VALUES('"+ gen +"','"+ pass +"','"+ dob +"','"+ name +"','"+ regd +"','"+ branch +"')";
		stmt.execute(dbop);
		stmt.close();	
		}
		catch(SQLException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,ex);
		}
		try {
			Stage primaryStage=new Stage();		//scene created for signup page
			Parent root=FXMLLoader.load(getClass().getResource("/application/LoginPage.fxml"));
			Scene scene = new Scene(root,685,510);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@FXML public TextField u1,pa1;
	static String fid;
	public void login(ActionEvent event) throws Exception {
		try {
			fid=u1.getText();
		Statement stmt=(Statement) con.createStatement();
		String dbop="SELECT * FROM data WHERE id = '"+ u1.getText()+"' AND password ='"+pa1.getText()+"'";
		ResultSet rs=stmt.executeQuery(dbop);

		if(rs.next()) {
			display();

		}
		else 
		{
			Alert a1=new Alert(Alert.AlertType.ERROR);
			a1.setTitle("ERROR");
			a1.setContentText("Wrong user ID or Password !!!");
			a1.setHeaderText(null);
			a1.showAndWait();
		}
		}
		catch(SQLException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,ex);
		}
	}
	
	
	
		
		public void display() throws Exception {
		try {
			Stage primaryStage = new Stage();        //scene created for signup page
			Parent root = FXMLLoader.load(getClass().getResource("/application/dashboard.fxml"));
			Scene scene = new Scene(root, 685, 510);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
		public void admin(ActionEvent event) throws Exception {
			if(u1.getText().equals("admin") && pa1.getText().equals("pass"))
			{
			Stage stage=new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
	        Scene scene = new Scene(root);      
	        stage.setScene(scene);
	        stage.show();
			}
			else {
				Alert a1=new Alert(Alert.AlertType.ERROR);
				a1.setTitle("ERROR");
				a1.setContentText("Wrong admin ID or Password !!!");
				a1.setHeaderText(null);
				a1.showAndWait();
				
			}
			
			
		}
		
}
