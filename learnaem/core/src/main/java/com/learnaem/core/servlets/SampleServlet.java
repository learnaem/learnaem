package com.learnaem.core.servlets;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.learnaem.core.services.SampleService;

@Component(service=Servlet.class, property = {
        "sling.servlet.paths=/bin/sampleservlet", 
        "sling.servlet.resourceTypes=learnaem/components/ajaxTesting","sling.servlet.methods=GET","sling.servlet.selector=getnormaltext",
        "sling.servlet.extensions =json" })
public class SampleServlet extends SlingSafeMethodsServlet{
	private static final long serialVersionUID = 1L;
	
	@Reference
	SampleService  sampleService;
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		/*try {
			sampleService.getValues();
		} catch (LoginException | RepositoryException e) {
			e.printStackTrace();
		}*/
        response.setContentType("application/json");
        response.getWriter().write(sampleService.getOsgiPayment());
	}
	}

