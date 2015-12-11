import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connect {

	private static String url = "jdbc:oracle:thin:@//127.0.0.1:1521/xe";
	private static String user = "nadia";
	private static String passwd = "nadia";
	private static Connection conn;
	
		
	public static Connection getInstance(){
		if(conn == null){
			try{Class.forName("oracle.jdbc.driver.OracleDriver"); 
				conn = DriverManager.getConnection(url, user,passwd);
			}
			catch(SQLException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			   }
		return conn;
		}
		
}
