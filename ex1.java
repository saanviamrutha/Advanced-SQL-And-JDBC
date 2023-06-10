package java_package;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ex1 {
	private final String url ="jdbc:postgresql://localhost/univdb";
    private final String user = "postgres";
    private final String password = "s1303";
    
    
    private void print_table(String str,int k) {
    	try(Connection connection = DriverManager.getConnection(url,user,password);){
    		
    		if (connection!=null) {
    			System.out.println("Connected to PostgreSQL server successfully!!\n");
    			
    		}else {
   			System.out.println("Failed to connect PostgreSQL server");
    		}
    		
    		Statement statement = connection.createStatement();
    		ResultSet resultSet = statement.executeQuery("SELECT * FROM "+str+" LIMIT "+k); 
    		ResultSetMetaData rsmd = resultSet.getMetaData();
    		
    		for(int j=1;j<=rsmd.getColumnCount();j++) {
				 System.out.printf("%-25s",rsmd.getColumnName(j));
				 System.out.print("\t");
			}
    		System.out.print("\n");
    		while(resultSet.next()==true) {
    			
    			for(int i =1; i<=rsmd.getColumnCount();i++) {
    				
    			System.out.printf("%-25s",resultSet.getString(i));
    			System.out.print("\t");
    		}
    			System.out.print("\n");
    		}
    	
    	
    	
    	
    	
    	    	
    	
    }
    		
    	 catch(SQLException e) {
    		e.printStackTrace();
    	}
    
    }
    
    
    public static void main(String[] args) {
    	ex1 sqlConnect = new ex1();
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.printf("Enter table name: ");
		String name = sc.next();
		
		System.out.printf("Enter k value: ");
		int k = sc.nextInt();
    
    	sqlConnect.print_table(name,k);
    	
    }
}
