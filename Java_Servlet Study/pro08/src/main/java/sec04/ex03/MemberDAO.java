package sec04.ex03;
 
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
 
    // ✅ 회원 목록 조회
    public List<MemberVO> listMembers() {
        List<MemberVO> list = new ArrayList<>();
        try {
            con = dataFactory.getConnection();
            String query = "SELECT * FROM t_member ORDER BY joinDate DESC";
            System.out.println("prepareStatement : " + query);
            pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
 
            while (rs.next()) {
                System.out.println("▶ DAO에서 읽은 값: " + rs.getString("id") + ", " + rs.getString("name"));
 
                MemberVO vo = new MemberVO();
                vo.setId(rs.getString("id"));
                vo.setPwd(rs.getString("pwd"));
                vo.setName(rs.getString("name"));
                vo.setEmail(rs.getString("email"));
                vo.setJoinDate(rs.getDate("joinDate"));
                list.add(vo);
            }
 
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
 
    // ✅ 회원 등록 (INSERT)
    public void addMember(MemberVO vo) {
        try {
            con = dataFactory.getConnection();
            String id = vo.getId();
            String pwd = vo.getPwd();
            String name = vo.getName();
            String email = vo.getEmail();
 
            String query = "INSERT INTO t_member (id, pwd, name, email) VALUES (?, ?, ?, ?)";
            System.out.println("prepareStatement : " + query);
 
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            pstmt.setString(3, name);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
 
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    // ✅ 회원 삭제 (DELETE)
    public void delMember(String id) {
        try {
            con = dataFactory.getConnection();
            String query = "DELETE FROM t_member WHERE id = ?";
            System.out.println("prepareStatement: " + query);
 
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
 
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}