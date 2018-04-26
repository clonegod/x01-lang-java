/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package thymeleaf.gtvg.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;

import thymeleaf.gtvg.business.entities.User;
import thymeleaf.gtvg.web.application.GTVGApplication;
import thymeleaf.gtvg.web.controller.IGTVGController;


public class GTVGFilter implements Filter {

    
    private ServletContext servletContext;
    
    
    public GTVGFilter() {
        super();
    }
    
    
    
    private static void addUserToSession(final HttpServletRequest request) {
        // Simulate a real user session by adding a user object
        request.getSession(true).setAttribute("user", new User("John", "Apricot", "Antarctica", null));
    }




    public void init(final FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
    }




    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {
        addUserToSession((HttpServletRequest)request);
        if (!process((HttpServletRequest)request, (HttpServletResponse)response)) {
            chain.doFilter(request, response);
        }
    }




    public void destroy() {
        // nothing to do
    }

    


    private boolean process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        
        try {
            
            /*
             * Query controller/URL mapping and obtain the controller
             * that will process the request. If no controller is available,
             * return false and let other filters/servlets process the request.
             */
            IGTVGController controller = GTVGApplication.resolveControllerForRequest(request);
            if (controller == null) {
                return false;
            }

            /*
             * Obtain the TemplateEngine instance.
             */
            TemplateEngine templateEngine = GTVGApplication.getTemplateEngine();

            /*
             * Write the response headers
             */
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            /*
             * Execute the controller and process view template,
             * writing the results to the response writer. 
             */
            controller.process(
                    request, response, this.servletContext, templateEngine);
            
            return true;
            
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
    }
    
    
    
}
