package app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import app.config.Constants;
import app.dao.User;
import app.dao.UserAccount;
import app.utils.Logger;
import app.utils.ServiceUtil;
import app.utils.ServletUtil;

public class ViewAccounts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewAccounts() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger.info("Begin of <--ViewAccounts--> Servlet");
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		Logger.info("Fetching Data for User:" + user.getUsername());
		@SuppressWarnings("rawtypes")
		MultivaluedMap formData = new MultivaluedMapImpl();
		ServiceUtil.addAuthToken(formData);
		formData.add("username", user.getUsername());
		Logger.info("Calling the Brokerage-Service/" + Constants.USER_ACCOUNTS);
		ClientResponse restResponse = ServiceUtil.getBrokerageServiceHandle(Constants.USER_ACCOUNTS, formData);

		if (restResponse.getStatus() != 200) {
			Logger.error(new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus()));
			Logger.info("Redirecting to error page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			String result = restResponse.getEntity(String.class);
			List<UserAccount> accounts = ServletUtil.getUserAccounts(result);
			session.setAttribute("accounts", accounts);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ViewAccounts.jsp");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		}
		Logger.info("End of <--ViewAccounts--> Servlet");
	}

}
