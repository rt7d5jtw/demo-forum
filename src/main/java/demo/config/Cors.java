package demo.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Cors extends CorsFilter {

  public Cors() {
    super(configSource());
  }

  private static UrlBasedCorsConfigurationSource configSource() {

    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOrigin("http://localhost:8080");
    config.addAllowedOrigin("http://127.0.0.1:8080");
    config.addAllowedOrigin("http://127.0.0.1");
    config.setAllowCredentials(true);
    config.addAllowedHeader("Authentication");
    config.addAllowedHeader("Content-Type");
    config.addAllowedMethod(HttpMethod.OPTIONS);
    config.addAllowedMethod(HttpMethod.GET);
    config.addAllowedMethod(HttpMethod.POST);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
