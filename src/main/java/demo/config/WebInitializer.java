package demo.config;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/* 
  All classes implementing the WebApplicationInitializer interface will be automatically detected by
  the org.springframework.web.SpringServletContainerInitializer class (which implements Servlet 3.0’s
  javax.servlet.ServletContainerInitializer interface), which bootstraps automatically in any Servlet 3.0
  containers. This class is an implementation of the ServletContainerInitializer interface and scans 
  the classpath for implementations of a WebApplicationInitializer interface.
*/
@Service
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] { AppConfig.class };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] { WebConfig.class };
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }
}
