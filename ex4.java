package java_package;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ex4 {
	private final static String url ="jdbc:postgresql://localhost/univdb";
    private final static String user = "postgres";
    private final static String password = "s1303";
        	
    
	
    
    
   public static void main(String[] args) {
    	
	   Scanner sc = new Scanner(System.in);
	   System.out.print("Enter a student id : ");
		String roll_num = sc.next();
		
		    
try(Connection connection = DriverManager.getConnection(url,user,password);){
    		
    		if (connection!=null) {
    			System.out.println("Connected to PostgreSQL server successfully!!\n");
    			
    		}else {
    			System.out.println("Failed to connect PostgreSQL server");
    		}
    		
    		Statement statement = connection.createStatement();
    		
    		int rs =statement.executeUpdate(
    				"drop view if exists takes_v cascade;\n"
    				+ "create view takes_v as\n"
    				+ "select * ,\n"
    				+ "case when grade = 'A+' then 10.00\n"
    				+ "when grade = 'A ' then 9.00\n"
    				+ "when grade = 'A-' then 8.00\n"
    				+ "when grade = 'B+' then 7.00\n"
    				+ "when grade = 'B ' then 6.00\n"
    				+ "when grade = 'B-' then 5.00\n"
    				+ "when grade = 'C+' then 4.00\n"
    				+ "when grade = 'C ' then 3.00\n"
    				+ "when grade = 'C-' then 2.00\n"
    				+ "else 0.00\n"
    				+ "end as grade_point\n"
    				+ "from takes;\n"+
    				
    				"create view sum_grade as\n"
    				+ " select takes_v.ID as t_id,sum(takes_v.grade_point * course.credits) as sum_grd_cred,sum(course.credits) as sum_cred\n"
    				+ " from takes_v,course\n"
    				+ " where takes_v.course_id = course.course_id\n"
    				+ " group by (takes_v.ID);\n"
    				
    				 );
    		
    		
    		ResultSet rs2= statement.executeQuery("select t_id,(sum_grd_cred)/(sum_cred) as cgpa\n"
    				+ "from sum_grade where t_id="+roll_num+"::varchar;");
    	
    	int count =0;
    		while(rs2.next()) {
    			System.out.print(rs2.getString(2)+"\n");
    			count++;
    		    		
    	}
    		if(count==0) {
    			System.out.print("Student id does not exist");
    		}
 	}

     

    	 catch(SQLException e) {
    		e.printStackTrace();
    	}	
    }
}