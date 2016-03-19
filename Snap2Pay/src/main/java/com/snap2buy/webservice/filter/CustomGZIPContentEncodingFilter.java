package com.snap2buy.webservice.filter;

import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.spi.container.*;
import org.apache.log4j.Logger;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * A GZIP content encoding filter.
 * <p>
 * If the request contains a Content-Encoding header of "gzip" then the request
 * entity (if any) is uncompressed using gzip.
 * <p>
 * If the request contains a Accept-Encoding header that contains "gzip" then
 * the response entity (if any) is compressed using gzip and a Content-Encoding
 * header of "gzip" is added to the response.
 * <p>
 * When an application is deployed as a Servlet or Filter this Jersey filter can
 * be registered using the following initialization parameters: <blockquote>
 * <p>
 * <pre>
 *     &lt;init-param&gt;
 *         &lt;param-name&gt;com.sun.jersey.spi.container.ContainerRequestFilters&lt;/param-name&gt;
 *         &lt;param-value&gt;com.sun.jersey.api.container.filter.GZIPContentEncodingFilter&lt;/param-value&gt;
 *     &lt;/init-param&gt
 *     &lt;init-param&gt
 *         &lt;param-name&gt;com.sun.jersey.spi.container.ContainerResponseFilters&lt;/param-name&gt;
 *         &lt;param-value&gt;com.sun.jersey.api.container.filter.GZIPContentEncodingFilter&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 * </pre>
 * <p>
 * </blockquote>
 *
 * @author Paul.Sandoz@Sun.Com
 * @see com.sun.jersey.api.container.filter
 */
public class CustomGZIPContentEncodingFilter implements ContainerRequestFilter,
        ContainerResponseFilter {
    private static Logger LOGGER = Logger.getLogger("s2b");

    public ContainerRequest filter(ContainerRequest request) {
        LOGGER.info("Inside GZIP Filter ContainerRequest");
        if (request.getRequestHeaders().containsKey(
                HttpHeaders.CONTENT_ENCODING)) {
            if (request.getRequestHeaders()
                    .getFirst(HttpHeaders.CONTENT_ENCODING).trim()
                    .equals("gzip")) {
                request.getRequestHeaders()
                        .remove(HttpHeaders.CONTENT_ENCODING);
                try {
                    request.setEntityInputStream(new GZIPInputStream(request
                            .getEntityInputStream()));
                } catch (IOException ex) {
                    throw new ContainerException(ex);
                }
            }
        }
        return request;
    }

    public ContainerResponse filter(ContainerRequest request,
                                    ContainerResponse response) {
        LOGGER.info("Inside GZIP Filter ContainerResponse");
        if ((!request.getRequestUri().toString().contains("ReportsAPI"))) {
            LOGGER.info("UI Request");
            if (response.getEntity() != null
                    && request.getRequestHeaders().containsKey(
                    HttpHeaders.ACCEPT_ENCODING)
                    && !response.getHttpHeaders().containsKey(
                    HttpHeaders.CONTENT_ENCODING)) {
                LOGGER.info("Request has Accept-Encoding Header");
                if (request.getRequestHeaders()
                        .getFirst(HttpHeaders.ACCEPT_ENCODING).contains("gzip")) {
                    LOGGER.info("Request has Accept-Encoding:gzip Header");
                    LOGGER.info("Zipping Response");
                    response.getHttpHeaders().add(HttpHeaders.CONTENT_ENCODING,
                            "gzip");
                    response.setContainerResponseWriter(new Adapter(response
                            .getContainerResponseWriter()));
                } else {
                    LOGGER.info("Accept-Encoding Header header value doesnot contain 'gzip' hence Not Zipping Response");
                }
            } else {
                LOGGER.info("Accept-Encoding Header Not Present or Content-Encoding header present hence Not Zipping Response");
            }
        } else {
            LOGGER.info("API Request hence Not Zipping Response");

        }
        return response;
    }

    private static final class Adapter implements ContainerResponseWriter {
        private final ContainerResponseWriter crw;

        private GZIPOutputStream gos;

        Adapter(ContainerResponseWriter crw) {
            this.crw = crw;
        }

        public OutputStream writeStatusAndHeaders(long contentLength,
                                                  ContainerResponse response) throws IOException {
            gos = new GZIPOutputStream(crw.writeStatusAndHeaders(-1, response));
            return gos;
        }

        public void finish() throws IOException {
            gos.finish();
            crw.finish();
        }
    }
}