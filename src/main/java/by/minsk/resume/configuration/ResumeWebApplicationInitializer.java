package by.minsk.resume.configuration;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionTrackingMode;
import java.util.EnumSet;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;



import by.minsk.resume.filter.ResumeFilter;
import by.minsk.resume.listener.ApplicationListener;




public class ResumeWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = createWebApplicationContext(servletContext);

        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.addListener(context.getBean(ApplicationListener.class));

        registerFilters(servletContext, context);
        registerSpringMVCDispatcherServlet(servletContext,context);
    }

    private  void registerSpringMVCDispatcherServlet(ServletContext context, WebApplicationContext applicationContext){
        ServletRegistration.Dynamic servlet = context.addServlet("dispatcher", new DispatcherServlet(applicationContext));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
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
        registerFilter(context,new OpenEntityManagerInViewFilter());
        registerFilter(context, applicationContext.getBean(ResumeFilter.class));  // change
        registerFilter(context, new CharacterEncodingFilter("UTF-8", true));
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
