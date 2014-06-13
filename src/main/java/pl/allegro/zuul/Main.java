package pl.allegro.zuul;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.context.ContextLifecycleFilter;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.netflix.zuul.http.ZuulServlet;
import com.netflix.zuul.monitoring.MonitoringHelper;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;

public class Main {

    public static void main(final String[] args) throws ServletException {

        startServerInitialization();

        MonitoringHelper.initMocks();
        HttpHandler handler = getZuulServletAsHandler();

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(handler)
                .build();
        server.start();
    }

    private static void startServerInitialization() {
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());

        try {
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            FilterFileManager.init(5, "src/main/groovy/filters/pre", "src/main/groovy/filters/route", "src/main/groovy/filters/post");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpHandler getZuulServletAsHandler() throws ServletException {
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(Main.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("test")
                .addServlets(
                        Servlets.servlet("MessageServlet", ZuulServlet.class)
                                .addMapping("/*")
                )
                .addFilter(new FilterInfo("ContextLifecycleFilter", ContextLifecycleFilter.class))
                .addFilterUrlMapping("ContextLifecycleFilter", "/*", DispatcherType.REQUEST);

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        return manager.start();
    }
}
