package dev.katha.library.checkout.config;

import dev.katha.library.checkout.entity.Checkout;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class LibraryDataRestConfig implements RepositoryRestConfigurer {
    private String theAllowedOrigins = "http://localhost:3000";
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration configurer, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {
          HttpMethod.PATCH,
          HttpMethod.PUT,
          HttpMethod.DELETE
        };
        configurer.exposeIdsFor(Checkout.class);
        disableHttpMethods(Checkout.class, configurer, theUnsupportedActions);

        /* configure CORS mapping */
        cors.addMapping(configurer.getBasePath()+"/**").allowedOrigins(theAllowedOrigins);
    }
    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));
    }
}
