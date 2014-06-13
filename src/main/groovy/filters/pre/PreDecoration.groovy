package filters.pre
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext

/**
 * @author mhawthorne
 */
class PreDecoration extends ZuulFilter {

    @Override
    int filterOrder() {
        return 5
    }

    @Override
    String filterType() {
        return "pre"
    }

    @Override
    boolean shouldFilter() {
        return true;
    }

    @Override
    Object run() {
        RequestContext ctx = RequestContext.getCurrentContext()

        // sets origin
        ctx.setRouteHost(new URL("http://mvnrepository.com/"));

        // sets custom header to send to the origin
        ctx.addOriginResponseHeader("cache-control", "max-age=3600");
    }

}
