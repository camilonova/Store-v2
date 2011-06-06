import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class SampleReport extends JRDefaultScriptlet {

	public static String user = "store";

	public static String pasw = "database";

	public static String driver = "com.mysql.jdbc.Driver";

	public static String url = "jdbc:mysql://localhost/store";

	public static ResultSet rs = null;

	public static Statement st = null;

	public static Connection cn = null;

	public static PreparedStatement pst = null;

	public static int per = 0;

	public static String hello() throws JRScriptletException {
		return "Hola Camilo";
	}
}