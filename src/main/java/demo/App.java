package demo;

import javax.servlet.ServletException;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import io.undertow.Undertow;
import demo.config.AppConfig;
import demo.config.UndertowConfig;
import demo.config.WebInitializer;

public class App {

  private static Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String... args) {

    // Create the 'root' Spring application context
    AnnotationConfigWebApplicationContext rootContext =
      new AnnotationConfigWebApplicationContext();
    rootContext.register(AppConfig.class);
    rootContext.setConfigLocation("demo.config.AppConfig");

    try {
      Undertow http = UndertowConfig.configure(
          "localhost", 
          8080, 
          "/", 
          SpringServletContainerInitializer.class,
          Collections.singleton(WebInitializer.class),
          Undertow.class.getClassLoader(),
          rootContext
          );
      http.start();
    } catch (ServletException e) {
      rootContext.close();
      throw new RuntimeException(e);
    }

    rootContext.close();
  }
}
