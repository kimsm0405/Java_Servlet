package sec04.ex02;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/first7")
public class FirstServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");	
			response.setContentType("text/html;charset=utf-8");
			request.setAttribute("address", "서울시 성북구");
			RequestDispatcher dispatch = request.getRequestDispatcher("second7");
			dispatch.forward(request, response);
	}
}
