package demo.utils;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsrfTokenLogger implements Filter {

  private Logger logger = LoggerFactory.getLogger(CsrfTokenLogger.class.getName());

  interface CsrfToken extends Serializable {
    String getHeaderName();
    String getParameterName();
    String getToken();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
          throws IOException, ServletException {
          Object o = request.getAttribute("_csrf");
          CsrfToken token = (CsrfToken) o;
          logger.info("CSRF Token: " + token.getToken());
          filterChain.doFilter(request, response);
  }
}
