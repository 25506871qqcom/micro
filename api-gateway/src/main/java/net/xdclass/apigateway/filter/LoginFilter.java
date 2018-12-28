package net.xdclass.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class LoginFilter extends ZuulFilter {
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

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURI());
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("loginfilter  --- pre ");

        return null;
    }
}
