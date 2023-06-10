package java_package;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ex5a {
	private final static String url ="jdbc:postgresql://localhost/univdb";
    private final static String user = "postgres";
    private final static String password = "s1303";
        	
    
   public static void main(String[] args) {
    	
	   Scanner sc = new Scanner(System.in);
		System.out.printf("Enter k: ");
       int k = sc.nextInt();
		    
try(Connection connection = DriverManager.getConnection(url,user,password);){
    		
    		if (connection!=null) {
    			System.out.println("Connected to PostgreSQL server successfully!!\n");
    			
    		}else {
    			System.out.println("Failed to connect PostgreSQL server");
    		}
    		
    		Statement statement = connection.createStatement();
    		int rs = statement.executeUpdate(
    				"drop view if exists all_cgpa;\n"+
    				"create view all_cgpa as\n"+
    				"select t_id,(sum_grd_cred)/(sum_cred) as cgpa\n"
    	    				+ "from sum_grade;\n"
    				
    	    				
);

    		ResultSet rs1 = statement.executeQuery("select t_id,cgpa, rank() over (order by cgpa desc) as s_rank\n"
    				+ "  from all_cgpa\n"
    				+ " order by s_rank limit "+k+";");
    		//ResultSetMetaData rsmd = resultSet.getMetaData();
    		
       while(rs1.next()) {   			
    			
    			System.out.println(rs1.getString(1)+"\t\t"+rs1.getString(2));
    			} 
    		
    	
    			
    			
    }	

    	 catch(SQLException e) {
    		e.printStackTrace();
    	}	
    }
}