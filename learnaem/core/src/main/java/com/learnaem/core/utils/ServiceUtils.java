package com.learnaem.core.utils;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.LoginException;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

public class ServiceUtils {

	public static final String WRITE_SERVICE_USER = "writeservice";

	/**
	 * @param resourceResolverFactory
	 *            factory
	 * @return new resource resolver for Sony service user
	 * @throws LoginException
	 *             if problems
	 * @throws org.apache.sling.api.resource.LoginException 
	 */
	public static ResourceResolver newResolver(ResourceResolverFactory resourceResolverFactory) throws org.apache.sling.api.resource.LoginException {
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ResourceResolverFactory.SUBSERVICE, WRITE_SERVICE_USER);

		// fetches the admin service resolver using service user.
		ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
		return resolver;
	}

}
