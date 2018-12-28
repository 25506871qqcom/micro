package net.xdclass.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class OrderRateLimiterFilter extends ZuulFilter {

    public static final String URI_ORDER = "/order/api/";
    public static final RateLimiter RATE_LIMITER = RateLimiter.create(3);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1 ;
    }

    /**
     * filter is worker ,some api need ,some apis not need
     * @return
     */
    @Override
    public boolean shouldFilter() {

        RequestContext requestContext = getHttpServletRequest();
        HttpServletRequest request = requestContext.getRequest();

        String requestURI = request.getRequestURI();
        System.out.println(requestURI);

        if(StringUtils.contains(requestURI,URI_ORDER)){
            System.out.println("order need filter");
            return true;
        }

        System.out.println("not order not need filter");
        return false;
    }

    private RequestContext getHttpServletRequest() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        return currentContext;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("loginfilter  --- pre "+this.getClass().getName());
        RequestContext requestContext = RequestContext.getCurrentContext();
        if(!RATE_LIMITER.tryAcquire()){
            System.out.println("=================get in rate limiter");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value() );
        }

        return null;
    }
}
