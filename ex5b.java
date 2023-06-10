package java_package;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ex5b {
	private final static String url ="jdbc:postgresql://localhost/univdb";
    private final static String user = "postgres";
    private final static String password = "s1303";
        	
    
   public static void main(String[] args) {
    	
	   Scanner sc = new Scanner(System.in);
		System.out.print("Enter department name: ");
		String name_of_dept = sc.nextLine();
		
		
		System.out.print("Enter k: ");
		int k = sc.nextInt();
		    
try(Connection connection = DriverManager.getConnection(url,user,password);){
    		
    		if (connection!=null) {
    			System.out.println("Connected to PostgreSQL server successfully!!\n");
    			
    		}else {
    			System.out.println("Failed to connect PostgreSQL server");
    		}
    		
    		Statement statement = connection.createStatement();
   
    		
    		//ResultSetMetaData rsmd = resultSet.getMetaData();
    		 String str = "select all_cgpa.t_id,all_cgpa.cgpa, rank() over (order by all_cgpa.cgpa desc) as s_rank\n"+
    		"from all_cgpa,student\n"+
    		 "where all_cgpa.t_id = student.ID and student.dept_name ='$dept_name'\n"+
    		 "\n"
    		  +"order by s_rank limit "+k+";";
    		
    	     
    		 ResultSet rs=statement.executeQuery(str.replace("$dept_name", name_of_dept));
       while(rs.next()) {   			    			
    	   
    			System.out.println(rs.getString(1)+"\t\t"+rs.getString(2));    
    			
    			        } 
    		
    	    			
    }	

    	 catch(SQLException e) {
    		e.printStackTrace();
    	}	
    }
}