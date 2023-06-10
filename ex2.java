package java_package;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ex2 {
	private final static String url ="jdbc:postgresql://localhost/univdb";
    private final static String user = "postgres";
    private final static String password = "s1303";
        	
    
   public static void main(String[] args) {
    	
	   Scanner sc = new Scanner(System.in);
		System.out.print("Enter course id: ");
		String c_id = sc.next();
		    
try(Connection connection = DriverManager.getConnection(url,user,password);){
    		
    		if (connection!=null) {
    			System.out.println("Connected to PostgreSQL server successfully!!\n");
    			
    		}else {
    			System.out.println("Failed to connect PostgreSQL server");
    		}
    		
    		Statement statement = connection.createStatement();
    		int rs1 = statement.executeUpdate(
    				"drop view if exists view1; \n"+
        			"create view view1 as \n"+
    				"with recursive rec_prereq(course_id, prereq_id) as ("+
    						"select course_id, prereq_id  "+
    						"from prereq where prereq.course_id="+c_id+ "::varchar"+
    						"  union  "+
    						"select rec_prereq.course_id, prereq.prereq_id   "+
    						"from rec_prereq, prereq  "+
    						"where rec_prereq.prereq_id = prereq.course_id and prereq.prereq_id!="+c_id+"::varchar"+
    						")\n"+
    						"select * from rec_prereq;\n"
    			 
);

    		
    		//ResultSetMetaData rsmd = resultSet.getMetaData();
    		ResultSet rs = statement.executeQuery("select view1.prereq_id,title from course,view1 where course.course_id=view1.prereq_id;");
       while(rs.next()) {   			
    		System.out.println(rs.getString(1)+"\t\t"+rs.getString(2));
    			} 
    		
    	
    			
    			
    }	

    	 catch(SQLException e) {
    		e.printStackTrace();
    	}	
    }
}
