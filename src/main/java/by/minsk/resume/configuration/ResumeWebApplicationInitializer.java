package by.minsk.resume.configuration;

import by.minsk.resume.controller.ProfileController;
import by.minsk.resume.filter.ApplicationFilter;
import by.minsk.resume.listener.ApplicationListener;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.*;
import java.util.EnumSet;

public class ResumeWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException  {
        WebApplicationContext context = createWebApplicationContext(servletContext);


        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.addListener(context.getBean(ApplicationListener.class));

        registerFilters(servletContext, context);
        registerServlet(servletContext, context.getBean(ProfileController.class), "/profile");
    }

    private void registerServlet(ServletContext context, Servlet servlet, String url) {
        ServletRegistration.Dynamic servletRegistration = context.addServlet(servlet.getClass().getSimpleName(), servlet);
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping(url);
    }

    private WebApplicationContext createWebApplicationContext(ServletContext context) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.scan("by.minsk.resume.configuration");
        applicationContext.setServletContext(context);
        applicationContext.refresh();
        return applicationContext;
    }

    private void registerFilter(ServletContext context, Filter filter, String... filterNames) {
        String filterName = filterNames.length > 0 ? filterNames[0] : filter.getClass().getSimpleName();
        context.addFilter(filterName, filter).addMappingForUrlPatterns(null, true, "/*");
    }

    private void registerFilters(ServletContext context, WebApplicationContext applicationContext) {
        registerFilter(context, new CharacterEncodingFilter("UTF-8", true));
        registerFilter(context, applicationContext.getBean(ApplicationFilter.class));
        registerFilter(context,buildConfigurableSiteMeshFilter(),"sitemesh");
    }

    private ConfigurableSiteMeshFilter buildConfigurableSiteMeshFilter(){

        return new ConfigurableSiteMeshFilter(){
            @Override
            protected void applyCustomConfiguration(SiteMeshFilterBuilder builder){
                builder.addDecoratorPath("/*","/WEB-INF/template/page-template.jsp");
            }
        };
    }


}
