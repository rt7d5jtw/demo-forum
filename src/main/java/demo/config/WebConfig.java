package demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "demo.controllers" })
public class WebConfig implements WebMvcConfigurer {

  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    //templateResolver.setApplicationContext(this.applicationContext);
    templateResolver.setPrefix("classpath:/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCacheable(true);
    return templateResolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    return templateEngine;
  }

  @Bean
  public ThymeleafViewResolver viewResolver() {
    final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    return viewResolver;
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.viewResolver(viewResolver());
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry
      .addViewController("/").setViewName("index");
    registry
      .addViewController("/signin").setViewName("signin");
    registry
      .addViewController("/register").setViewName("register");
    registry
      .addViewController("/faq").setViewName("faq");
  }

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry
      .addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
    registry
      .addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
    registry
      .addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
  }

  @Bean
  public DateFormatter dateFormatter() {
    return new DateFormatter();
  }

  public void configureContentNegotation(ContentNegotiationConfigurer configurer) {
    configurer.ignoreAcceptHeader(true)
      .defaultContentType(MediaType.APPLICATION_JSON)
      .mediaType("html", MediaType.TEXT_HTML)
      .mediaType("htm", MediaType.TEXT_HTML)
      .mediaType("form-urlencoded", MediaType.APPLICATION_FORM_URLENCODED);
  }
}
