package com.learnaem.core.services.impl;

import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.learnaem.core.config.PaymentConfig;
import com.learnaem.core.services.SampleService;
import com.learnaem.core.utils.ServiceUtils;

@Component(service=SampleService.class)
@Designate(ocd=PaymentConfig.class)
public class SampleServiceImpl implements SampleService{

	@Reference
	ResourceResolverFactory resourceResolverFactory;
	
	ResourceResolver rr;
	
	String path="/content/learnaem";

	@Override
	public void getValues() throws LoginException, ItemExistsException, PathNotFoundException, NoSuchNodeTypeException,
			LockException, VersionException, ConstraintViolationException, RepositoryException, PersistenceException {
		ResourceResolver rr = ServiceUtils.newResolver(resourceResolverFactory);
		Resource r = rr.getResource(path);
		Node node = r.adaptTo(Node.class);
		node.addNode("Heloo", "nt:unstructured");
		node.setProperty("hel", "someValue");
		rr.commit();
		rr.close();
	}

}
