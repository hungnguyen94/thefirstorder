package nl.tudelft.thefirstorder.web.filter;

import nl.tudelft.thefirstorder.config.JHipsterProperties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * This filter is used in production, to put HTTP cache headers with a long (1 month) expiration time.
 */
public class CachingHttpHeadersFilter implements Filter {

    // We consider the last modified date is the start up time of the server
    private static final long LAST_MODIFIED = System.currentTimeMillis();

    private static long CACHE_TIME_TO_LIVE = TimeUnit.DAYS.toMillis(1461L);

    private JHipsterProperties jHipsterProperties;

    /**
     * Constructor.
     * @param jHipsterProperties the properties of jhipster
     */
    public CachingHttpHeadersFilter(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    /**
     * Initialize the caching.
     * @param filterConfig the filter configuration
     * @throws ServletException when something goes wrong with the caching
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        CACHE_TIME_TO_LIVE = TimeUnit.DAYS.toMillis(jHipsterProperties.getHttp().getCache().getTimeToLiveInDays());
    }

    /**
     * Destroy the cache.
     */
    @Override
    public void destroy() {
        // Nothing to destroy
    }

    /**
     * Apply a filter.
     * @param request http request
     * @param response http response
     * @param chain chain of filters
     * @throws IOException Input or output is incorrect
     * @throws ServletException Something with http is going wrong
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Cache-Control", "max-age=" + CACHE_TIME_TO_LIVE + ", public");
        httpResponse.setHeader("Pragma", "cache");

        // Setting Expires header, for proxy caching
        httpResponse.setDateHeader("Expires", CACHE_TIME_TO_LIVE + System.currentTimeMillis());

        // Setting the Last-Modified header, for browser caching
        httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED);

        chain.doFilter(request, response);
    }
}
