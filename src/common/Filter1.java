package common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.UserBean;

@WebFilter(urlPatterns = "/*")
public class Filter1 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			 throws IOException, ServletException {


		HttpServletRequest req = (HttpServletRequest) request;

		//Servletのパスを取得
		String servletPath = req.getServletPath();

		//LoginServletがでないとき
		if(!"/LoginServlet".equals(servletPath)) {

			//セッションのデータをuserBean取得
			HttpSession session = req.getSession();
			UserBean sessionUserBean = (UserBean)session.getAttribute("USER_BEAN");

			//セッションが存在しないとき
			if (sessionUserBean == null) {

				//引き渡す値を設定
				request.setAttribute("err", "ログインしてください");

				//画面遷移(ログイン画面)
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig)
			throws ServletException {

	}

	@Override
	public void destroy() {

	}

}
