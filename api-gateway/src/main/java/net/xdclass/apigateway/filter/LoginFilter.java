package net.xdclass.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


@Component
public class LoginFilter extends ZuulFilter {

    public static final String TOKEN = "token";

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

        if(StringUtils.contains(requestURI,"order/api/")){
            System.out.println("not need filter");
            return false;
        }

        System.out.println(" need filter");
        return true;
    }

    private RequestContext getHttpServletRequest() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        return currentContext;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("loginfilter  --- pre ");

        RequestContext requestContext = getHttpServletRequest();
        HttpServletRequest request = requestContext.getRequest();

        String token = request.getHeader(TOKEN);

        if(StringUtils.isEmpty(token)){
            token = request.getParameter(TOKEN);
        }

        System.out.println("token="+token);

        if(StringUtils.isEmpty(token)){
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value() );
        }

        return null;
    }
}
