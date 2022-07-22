package demo.config;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainerInitializerInfo;
import io.undertow.servlet.api.ServletStackTraces;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletException;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UndertowConfig {

  public static Undertow configure(
      String address, int port, String contextPath, 
      Class<? extends ServletContainerInitializer> servletContainerInitializerClass,
      Set<Class<?>> initializers,
      ClassLoader classLoader,
      ApplicationContext ctx
      ) throws ServletException {

    ServletContainerInitializerInfo servletContainerInitializerInfo = 
      new ServletContainerInitializerInfo(servletContainerInitializerClass, initializers);

    DeploymentInfo servletBuilder = deployment()
      .addServletContainerInitializer(servletContainerInitializerInfo)
      .setClassLoader(classLoader)
      .setContextPath(contextPath)
      .setDisplayName("wat")
      .setDeploymentName("webapp")
      .setServletStackTraces(ServletStackTraces.LOCAL_ONLY);

      DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
      manager.deploy();

      HttpHandler servletHandler = manager.start();

      // PrefixPath is the root mapping
      PathHandler paths = Handlers.path()
        .addPrefixPath(contextPath, servletHandler);

      Undertow http = Undertow.builder()
        .addHttpListener(port, address)
        .setHandler(paths)
        .build();

      return http;
  }
}
