/*******************************************************************************
 * Copyright (c) 2018, 2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package mpRestClient11.produceConsume;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.Test;

import componenttest.app.FATServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/ProduceConsumeTestServlet")
public class ProduceConsumeTestServlet extends FATServlet {
    private final static Logger _log = Logger.getLogger(ProduceConsumeTestServlet.class.getName());

    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier() {

            @Override
            public boolean verify(String hostname,
                    javax.net.ssl.SSLSession sslSession) {
                if (hostname.contains("localhost")) {
                    return true;
                }
                return false;
            }
        });
    }
 
    /**
     * Tests that MP Rest Client's <code>@Produces</code> annotation affects the value transmitted in
     * the <code>Accept</code> header, and that it's <code>@Consumes</code> annotation affects the
     * value transmitted in the <code>Content-Type</code> header when it is applied to the method.
     * Note that this is opposite of what you would expect for JAX-RS resources. 
     */
    @Test
    public void testProducesConsumesAnnotationOnClientInterfaceMethod(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        final String m = "testProducesConsumesAnnotationOnClientInterfaceMethod";
        MyClient client = RestClientBuilder.newBuilder()
                                           .baseUri(URI.create("http://localhost:23/null"))
                                           .register(Filter.class)
                                           .build(MyClient.class);
        
        _log.info(m + " @Produce(application/json) @Consume(application/xml)");
        Response r = client.produceJSONConsumeXML(new Widget("foo", 1));
        String acceptHeader = r.getHeaderString("Sent-Accept");
        _log.info(m + "Sent-Accept: " + acceptHeader);
        String contentTypeHeader = r.getHeaderString("Sent-ContentType");
        _log.info(m + "Sent-ContentType: " + contentTypeHeader);
        assertEquals(MediaType.APPLICATION_JSON, acceptHeader);
        assertEquals(MediaType.APPLICATION_XML, contentTypeHeader);
        
        _log.info(m + " @Produce(application/xml) @Consume(application/json)");
        r = client.produceXMLConsumeJSON(new Widget("bar", 2));
        acceptHeader = r.getHeaderString("Sent-Accept");
        _log.info(m + "Sent-Accept: " + acceptHeader);
        contentTypeHeader = r.getHeaderString("Sent-ContentType");
        _log.info(m + "Sent-ContentType: " + contentTypeHeader);
        assertEquals(MediaType.APPLICATION_XML, acceptHeader);
        assertEquals(MediaType.APPLICATION_JSON, contentTypeHeader);
    }

    /**
     * Tests that MP Rest Client's <code>@Produces</code> annotation affects the value transmitted in
     * the <code>Accept</code> header, and that it's <code>@Consumes</code> annotation affects the
     * value transmitted in the <code>Content-Type</code> header when it is applied to the interface, but
     * not specified in the method.
     * Note that this is opposite of what you would expect for JAX-RS resources. 
     */
    @Test
    public void testProducesConsumesAnnotationOnClientInterfaceType(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        final String m = "testProducesConsumesAnnotationOnClientInterfaceType";
        MyClient client = RestClientBuilder.newBuilder()
                                           .baseUri(URI.create("http://localhost:23/null"))
                                           .register(Filter.class)
                                           .build(MyClient.class);
        
        _log.info(m + " @Produce(text/html) @Consume(text/plain)");
        Response r = client.produceHTMLConsumeTEXT(new Widget("baz", 3));
        String acceptHeader = r.getHeaderString("Sent-Accept");
        _log.info(m + "Sent-Accept: " + acceptHeader);
        String contentTypeHeader = r.getHeaderString("Sent-ContentType");
        _log.info(m + "Sent-ContentType: " + contentTypeHeader);
        assertEquals(MediaType.TEXT_HTML, acceptHeader);
        assertEquals(MediaType.TEXT_PLAIN, contentTypeHeader);
    }
}
