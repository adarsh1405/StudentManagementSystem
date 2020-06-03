package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class sqlconnect {
	  Connection conn = null;
	    public static Connection ConnectDb(){
	        try {
	        	Class.forName("com.mysql.jdbc.Driver");
	            Connection conn =(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sms","root","qwerty123");
	           // JOptionPane.showMessageDialog(null, "Connection Established");
	            return conn;
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, e);
	            return null;
	        }
	    
	    }
	    
	    public static ObservableList<student> getDatausers(){
	        Connection conn = ConnectDb();
	        ObservableList<student> list = FXCollections.observableArrayList();
	        try {
	            PreparedStatement ps = conn.prepareStatement("select * from data");
	            ResultSet rs = ps.executeQuery();
	            
	            while (rs.next()){  
//	            	System.out.println(rs.getString("name"));
	                list.add(new student( rs.getString("id"),rs.getNString("name"), rs.getString("password"), rs.getString("dob"), rs.getString("regd"),rs.getString("course")));               
	            }
	        } catch (Exception e) {
	        }
	        return list;
	    }

}
