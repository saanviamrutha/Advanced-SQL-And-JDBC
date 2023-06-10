package java_package;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ex5c {
	private final static String url ="jdbc:postgresql://localhost/univdb";
    private final static String user = "postgres";
    private final static String password = "s1303";
        	
    
   public static void main(String[] args) {
    	
	   Scanner sc = new Scanner(System.in);
		System.out.print("Enter course id: ");
		String c_id = sc.nextLine();
		System.out.print("Enter k: ");
		    int k = sc.nextInt();
try(Connection connection = DriverManager.getConnection(url,user,password);){
    		
    		if (connection!=null) {
    			System.out.println("Connected to PostgreSQL server successfully!!\n");
    			
    		}else {
    			System.out.println("Failed to connect PostgreSQL server");
    		}
    		
    		Statement statement = connection.createStatement();
    		ResultSet rs = statement.executeQuery(
    				
    			 "select takes.ID,all_cgpa.cgpa,rank() over (order by all_cgpa.cgpa desc) as s_rank"
    			 + " from all_cgpa,takes "
    			 + "where all_cgpa.t_id=takes.ID and takes.course_id = "+c_id+"::varchar\n"+
    			 "order by s_rank limit "+k+"::int;"
    			 
);

    		
    		//ResultSetMetaData rsmd = resultSet.getMetaData();
    		
       while(rs.next()) {   			
    		System.out.println(rs.getString(1)+"\t\t"+rs.getString(2));
    			} 
    		
    	
    			
    			
    }	

    	 catch(SQLException e) {
    		e.printStackTrace();
    	}	
    }
}
