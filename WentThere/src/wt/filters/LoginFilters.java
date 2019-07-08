package wt.filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wt.mbeans.LoginBean;

/**
 * Servlet Filter implementation class LoginFilters
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
		DispatcherType.ERROR }, urlPatterns = { "/secure/*" })
public class LoginFilters implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilters() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();

		if (session.getAttribute("loginBean") == null) {
			res.sendRedirect("/" + req.getContextPath() + "/login.xhtml");
		} else {
			LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");

			// if (loginBean.getEmail() != null || loginBean.getPassword() != null) {
			//
			// res.sendRedirect("/" + req.getContextPath() + "/secure/visitedplaces.xhtml");
			// }
			if (loginBean.getEmail() == null || loginBean.getPassword() == null) {
				res.sendRedirect(req.getContextPath() + "/login.xhtml");
			}
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);

	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
