package sec05.ex01;
 
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
 
public class MemberDAO {
    private Connection con;
    private PreparedStatement pstmt;
    private DataSource dataFactory;
 
    public MemberDAO() {
        try {
            Context ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Boolean isExisted(MemberVO memberVO) {
    	boolean result = false;
    	String id = memberVO.getId();
    	String pwd = memberVO.getPwd();
    	try {
    		con = dataFactory.getConnection();
    		String query = "select decode(count(*),1,'true','false') as result from t_member";
    			query += " where id=? and pwd=?";
    		pstmt = con.prepareStatement(query);
    		pstmt.setString(1, id);
    		pstmt.setString(2, pwd);
    		ResultSet rs = pstmt.executeQuery();
    		rs.next();
    		result = Boolean.parseBoolean(rs.getString("result"));
    		System.out.println("result=" + result);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return result;
    }
}
