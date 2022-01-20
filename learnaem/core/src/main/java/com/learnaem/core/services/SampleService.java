package com.learnaem.core.services;

import javax.jcr.ItemExistsException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;

public interface SampleService {

	public void getValues() throws LoginException, ItemExistsException, PathNotFoundException, NoSuchNodeTypeException,
			LockException, VersionException, ConstraintViolationException, RepositoryException, PersistenceException;

}
