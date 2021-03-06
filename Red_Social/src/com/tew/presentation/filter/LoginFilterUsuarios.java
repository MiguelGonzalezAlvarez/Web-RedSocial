package com.tew.presentation.filter;
//Los import no se incluyen aqu? pero s? los ver?s en eclipse
/**
 * Servlet Filter implementation class LoginFilter
 */
import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tew.model.User;
import com.tew.presentation.BeanLoginManager;

/**
 * Servlet Filter implementation class LoginFilter
 */

@WebFilter(
		dispatcherTypes = {DispatcherType.REQUEST }, 
		description = "Filtro de seguridad", 
		urlPatterns = { 
				"/faces/usuarios/*", 
				"/usuarios/*"
		}, 
		initParams = { 
				@WebInitParam(name = "LoginParam", value = "/faces/index.xhtml", description = "Pagina de loggeo")
		})
public class LoginFilterUsuarios implements Filter {

	//Necesitamos acceder a los par?metros de inicializaci?n en
	//el m?todo doFilter por lo que necesitamos la variable
	//config que se inicializar? en init()
	FilterConfig config = null;
	/**
	 * Default constructor. 
	 */
	public LoginFilterUsuarios() 
	{
		
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() 
	{
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
		//Iniciamos la variable de instancia config
		config = fConfig;
	}
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException 
	{
		// Si no es petici?n HTTP nada que hacer
		if (!(request instanceof HttpServletRequest)){
			chain.doFilter(request, response);
			return;
		}
		// En el resto de casos se verifica que se haya hecho login previamente
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		ServletContext context = req.getServletContext();
		
		User usuario = (User)session.getAttribute("LOGGEDIN_USER");
		BeanLoginManager loginManager = (BeanLoginManager) context.getAttribute("loginManager");
		
		if (usuario == null || !usuario.getRol().equals("usuario") || !loginManager.getLogins().contains(usuario)) 
		{
			String loginForm = config.getInitParameter("LoginParam");
			res.sendRedirect(req.getContextPath() + loginForm);
			return;
		}
		
	    loginManager.getLogins().add(usuario);
	    
		chain.doFilter(request, response);
	}

}