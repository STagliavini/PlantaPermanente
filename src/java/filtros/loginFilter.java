/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtros;

import entidades.Usuario;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author santiagoat
 */
@WebFilter(filterName = "loginFilter", urlPatterns = {"/faces/vistas/*"})
public class loginFilter implements Filter {

    private static final boolean debug = true;
    public static final String LOGIN_PAGE_REDIRECT
            = "/faces/index.xhtml";

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public loginFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("loginFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("loginFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest
                = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse
                = (HttpServletResponse) servletResponse;
        Usuario usuarioActual = (Usuario) httpServletRequest
                .getSession().getAttribute("usuarioActual");
        httpServletResponse.addHeader("Pragma", "no-cache");
        httpServletResponse.addHeader("Cache-Control", "no-cache");
        httpServletResponse.addHeader("Cache-Control", "no-store");
        httpServletResponse.addHeader("Cache-Control", "must-revalidate");
        if (usuarioActual != null) {
            if ((httpServletRequest.getRequestURL().toString().contains("/faces/vistas/generarRecibo.xhtml")
                    || httpServletRequest.getRequestURL().toString().contains("/faces/vistas/alta_bajaPeriodo.xhtml"))
                    && !usuarioActual.getTipoUsuario().equals("Admin") && !usuarioActual.getTipoUsuario().equals("ARecibo")) {
                httpServletResponse.sendRedirect(
                        httpServletRequest.getContextPath() + "/faces/vistas/listarEmpleados.xhtml");
            } else {
                if ((httpServletRequest.getRequestURL().toString().contains("/faces/vistas/agregarCargo.xhtml")
                        || httpServletRequest.getRequestURL().toString().contains("/faces/vistas/modificarCargo.xhtml")
                        || httpServletRequest.getRequestURL().toString().contains("/faces/vistas/eliminarCargo.xhtml")
                        || httpServletRequest.getRequestURL().toString().contains("/faces/vistas/agregarOrganismo.xhtml")
                        || httpServletRequest.getRequestURL().toString().contains("/faces/vistas/modificarOrganismo.xhtml")
                        || httpServletRequest.getRequestURL().toString().contains("/faces/vistas/eliminarOrganismo.xhtml")
                        || httpServletRequest.getRequestURL().toString().contains("/faces/vistas/agregarCategoria.xhtml")
                        || httpServletRequest.getRequestURL().toString().contains("/faces/vistas/modificarCategoria.xhtml")
                        || httpServletRequest.getRequestURL().toString().contains("/faces/vistas/eliminarCategoria.xhtml"))
                        && !usuarioActual.getTipoUsuario().equals("Admin")) {
                    httpServletResponse.sendRedirect(
                            httpServletRequest.getContextPath() + "/faces/vistas/listarEmpleados.xhtml");
                } else {
                    if ((httpServletRequest.getRequestURL().toString().contains("/faces/vistas/alta_bajaPeriodo.xhtml")
                        || httpServletRequest.getRequestURL().toString().contains("/faces/vistas/generarRecibo.xhtml"))
                        && !usuarioActual.getTipoUsuario().equals("Admin")&&!usuarioActual.getTipoUsuario().equals("ARecibo")) {
                         httpServletResponse.sendRedirect(
                            httpServletRequest.getContextPath() + "/faces/vistas/listarEmpleados.xhtml");

                    } else {
                        chain.doFilter(servletRequest, servletResponse);
                    }

                }
            }
        } else {
            httpServletResponse.sendRedirect(
                    httpServletRequest.getContextPath() + LOGIN_PAGE_REDIRECT);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("loginFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("loginFilter()");
        }
        StringBuffer sb = new StringBuffer("loginFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
