package application;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
public class studentController implements Initializable {
	
	
	static Connection con;
	static ResultSet rs;
	static String s;
	static String u;
	public static String[] search() {
		String res[]=new String [6];
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

		controller obj=new controller();
		 String u=obj.fid;
        Statement stmt;
		try {
			String dbop="select * from data where id='"+u+"'";
			stmt = (Statement) con.createStatement();
			 rs=stmt.executeQuery(dbop);
			while(rs.next()) {
			res[0]="Hello ! , "+rs.getNString("name")+ "('" + rs.getString("id")+"')";	
			res[1]="You have enrolled in  "+rs.getString("course")+"  course";
			res[2]=calc(rs.getString("course"));
			s=rs.getString("course");			
			}       
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return res;		
	}
	
			public static String calc(String s) {
				String fee="";
				if(s.equalsIgnoreCase("CE"))
					fee="Rs. 1,80,000.00";
				else if(s.equalsIgnoreCase("CSE/IT"))
					fee="Rs. 2,35,000.00";
				else if(s.equalsIgnoreCase("EE/ECE/EEE"))
					fee="Rs. 2,00,000.00";
				else if(s.equalsIgnoreCase("ME"))
					fee="Rs. 2,15,000.00";
				else
					fee="not find your course. PLEASE SELECT A VALID COURSE";
				String res="FEES:- "+ fee;
				return res;		
			}
	
				public static double calc_fee(double value,ResultSet rs) {		
					double fee=0;
						if(s.equalsIgnoreCase("CE"))
							fee= 180000.00-value;
						else if(s.equalsIgnoreCase("CSE/IT"))
							fee= 235000.00-value;
						else if(s.equalsIgnoreCase("EE/ECE/EEE"))
							fee= 200000.00-value;
						else if(s.equalsIgnoreCase("ME"))
							fee= 215000.00-value;
					return fee;			
				}

	
	@FXML public Label a;
	@FXML public Label b;
	@FXML public Label course;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		String str[]=search();	
		try {
			a.setText(str[0]);
			b.setText(str[1]);
			course.setText(str[2]);			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}	
	}
	
	
	@FXML public TextField fee;
	public void status(ActionEvent event) throws Exception {
		double value=Double.parseDouble(fee.getText());
		double fee1=calc_fee(value,rs);
		Alert a1=new Alert(Alert.AlertType.INFORMATION);
		a1.setTitle("STATUS");
		a1.setContentText("Your remaining fee is Rs. "+ fee1);
		a1.setHeaderText(null);
		a1.showAndWait();
		}

}
