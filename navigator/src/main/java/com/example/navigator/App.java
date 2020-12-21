package com.example.navigator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//public class App implements WebApplicationInitializer {
//    public void onStartup(ServletContext container) throws ServletException {
//
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.setConfigLocation(null);
//        context.register(AppConfig.class);
//        container.addListener(new ContextLoaderListener(context));
//
//        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(context));
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/");
//
//        // java.io.FileNotFoundException: Could not open ServletContext resource [/WEB-INF/DispatcherServlet-servlet.xml]
//    }
//}

@SpringBootApplication()
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}

