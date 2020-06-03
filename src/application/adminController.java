package application;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class adminController implements Initializable {

    @FXML
    private TableView<student> table;

    @FXML
    private TableColumn<student, String> col_id;

    @FXML
    private TableColumn<student, String> col_name;

    @FXML
    private TableColumn<student, String> col_dob;

    @FXML
    private TableColumn<student, String> col_regd;

    @FXML
    private TableColumn<student, String> col_course;
    
    @FXML
    private TableColumn<student, String> col_password;
    
     @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_dob;

    @FXML
    private TextField txt_course;
        
    @FXML
    private TextField txt_id;
    
    @FXML
    private TextField txt_regd;
    
    @FXML
    private TextField filterField;
    
       
    ObservableList<student> listM;
    ObservableList<student> dataList;
    
   
    
    int index = -1;
    
    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
     
    public void Add_users (){    
        conn = sqlconnect.ConnectDb();
        String sql = "insert into data (id,name,password,dob,regd,course)values(?,?,?,?,?,? )";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.setString(2, txt_username.getText());
            pst.setString(3, txt_password.getText());
            pst.setString(4, txt_dob.getText());
            pst.setString(5, txt_regd.getText());
            pst.setString(6, txt_course.getText());
            pst.execute();
            
            Alert a1=new Alert(Alert.AlertType.CONFIRMATION);
    		a1.setTitle("ADD");
    		a1.setContentText("User Added Sucessfully !!");
    		a1.setHeaderText(null);
    		a1.showAndWait();
            UpdateTable();
            search_user();
        } catch (Exception e) {
        	Alert a1=new Alert(Alert.AlertType.ERROR);
    		a1.setTitle("ADD");
    		a1.setContentText(e.toString());
    		a1.setHeaderText(null);
    		a1.showAndWait();
        }
    }
    

    			/* method select users */
    @FXML
    void getSelected (MouseEvent event){
    index = table.getSelectionModel().getSelectedIndex();
    if (index <= -1){
    
        return;
    }
    txt_id.setText(col_id.getCellData(index).toString());
    txt_username.setText(col_name.getCellData(index).toString());
    txt_password.setText(col_regd.getCellData(index).toString());
    txt_dob.setText(col_dob.getCellData(index).toString()); 
    txt_regd.setText(col_regd.getCellData(index).toString());
    txt_course.setText(col_course.getCellData(index).toString());
    
    }

    public void Edit (){   
        try {
            conn = sqlconnect.ConnectDb();
            String value1 = txt_id.getText();
            String value2 = txt_username.getText();
            String value3 = txt_password.getText();
            String value4 = txt_dob.getText();
            String value5 = txt_regd.getText();
            String value6 = txt_course.getText();
            
            String sql = "update data set id= '"+value1+"',name= '"+value2+"',password= '"+
                    value3+"',dob= '"+value4+"',regd= '"+value5+"',course='"+ value6 +"' where id='"+value1+"' ";
            pst= conn.prepareStatement(sql);
            pst.execute();
            Alert a1=new Alert(Alert.AlertType.CONFIRMATION);
    		a1.setTitle("STATUS");
    		a1.setContentText("Account Updated !!");
    		a1.setHeaderText(null);
    		a1.showAndWait();
            UpdateTable();
            search_user();
        } catch (Exception e) {
        	Alert a1=new Alert(Alert.AlertType.ERROR);
    		a1.setTitle("Update");
    		a1.setContentText(e.toString());
    		a1.setHeaderText(null);
    		a1.showAndWait();
        }
        
    }
    
    public void Delete(){
    conn = sqlconnect.ConnectDb();
    String sql = "delete from data where id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.execute();
            Alert a1=new Alert(Alert.AlertType.CONFIRMATION);
    		a1.setTitle("STATUS");
    		a1.setContentText("Account Deleted !");
    		a1.setHeaderText(null);
    		a1.showAndWait();
            UpdateTable();
            search_user();
        } catch (Exception e) {
        	Alert a1=new Alert(Alert.AlertType.ERROR);
    		a1.setTitle("DELETE");
    		a1.setContentText(e.toString());
    		a1.setHeaderText(null);
    		a1.showAndWait();
        }
    
    }

    
    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<student,String>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<student,String>("name"));
        col_password.setCellValueFactory(new PropertyValueFactory<student,String>("password"));    
        col_dob.setCellValueFactory(new PropertyValueFactory<student,String>("dob"));
        col_regd.setCellValueFactory(new PropertyValueFactory<student,String>("regd"));      
        col_course.setCellValueFactory(new PropertyValueFactory<student,String>("course"));
       
        listM = sqlconnect.getDatausers();
        table.setItems(listM);
    }
    
    

    
    @FXML
    void search_user() {          
        col_id.setCellValueFactory(new PropertyValueFactory<student,String>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<student,String>("name"));
        col_password.setCellValueFactory(new PropertyValueFactory<student,String>("password"));
        col_dob.setCellValueFactory(new PropertyValueFactory<student,String>("dob"));
        col_regd.setCellValueFactory(new PropertyValueFactory<student,String>("regd"));        
        col_course.setCellValueFactory(new PropertyValueFactory<student,String>("course")); 
        dataList = sqlconnect.getDatausers();
        table.setItems(dataList);
        FilteredList<student> filteredData = new FilteredList<>(dataList, b -> true);		
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(person -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (person.getname().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches username
				} else if (person.getPassword().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				}
				else if (String.valueOf(person.getRegd()).indexOf(lowerCaseFilter)!=-1)
				     return true;// Filter matches email
                                
				     else  
				    	 return false; // Does not match.
			});
		});		
		SortedList<student> sortedData = new SortedList<>(filteredData);		
		sortedData.comparatorProperty().bind(table.comparatorProperty());		
		table.setItems(sortedData);      
    }
  
 	@Override
 	public void initialize(URL url, ResourceBundle rb) {
    UpdateTable();
    search_user();
    } 
}
