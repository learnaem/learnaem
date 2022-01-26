package com.learnaem.core.xmlreader.core.services.impl;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.learnaem.core.xmlreader.core.models.Item;
import com.learnaem.core.xmlreader.core.models.Items;
import com.learnaem.core.xmlreader.core.services.XMLWriterService;

/**
 * @author pinteshkc
 */
@Component(immediate = true, service = XMLWriterService.class)
public class XMLWriterServiceImpl implements XMLWriterService {

    // Logger
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // Injecting ResourceResolverFactory
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private String destFolderPath;

    @Reference
    private Replicator replicator;

    private List<String> paths = new ArrayList<>();

    /**
     * This method writes XML data to the JCR repository.
     */
    @Override
    public void writeXMLToJCR(Items items) {
        paths.clear();
        log.info("Writing XML data to nodes from.");
        try {
            ResourceResolver resourceResolver = getResolver();
            if (!items.getItem().isEmpty()) {
                createFolder(resourceResolver);
            }
            if (resourceResolver != null && StringUtils.isNotEmpty(destFolderPath)) {
                Node destFolderNode = null;
                Resource res = resourceResolver.getResource(destFolderPath);
                if (res != null) {
                    destFolderNode = res.adaptTo(Node.class);
                }

                // Getting the products from ProductList
                List<Item> item = items.getItem();

                // Iterate for each item present in the XML file
                for (Item i : item) {
                    if (destFolderNode != null) {
                        Node currentNode = null;
                        destFolderNode.addNode("item_" + i.getJobRef(), "nt:unstructured");
                        currentNode = destFolderNode.getNode("item_" + i.getJobRef());
                        currentNode.setProperty("jobTitle", i.getJobTitle());
                        currentNode.setProperty("jobRef", i.getJobRef());
                        currentNode.setProperty("jobLocation", i.getLocation());
                        currentNode.setProperty("applyURL", i.getApplyURL());
                        paths.add(currentNode.getPath());
                    }
                }
                // Saving the changes to JCR
                resourceResolver.commit();
            }

        } catch (RepositoryException e) {
            log.error("Exception while writing to JCR, {}", e.getMessage());
        } catch (PersistenceException e) {
            log.error("Exception while commit, {}", e.getMessage());
        }
        replicateFolders();
    }

    private void replicateFolders() {
        ResourceResolver resourceResolver = getResolver();
        try {
            if (resourceResolver != null) {
                /*replicator.replicate(resourceResolver.adaptTo(Session.class), ReplicationActionType.DEACTIVATE,
                        Constants.DESTINATION);
                if (!paths.contains(Constants.DESTINATION)) {
                    replicator.replicate(resourceResolver.adaptTo(Session.class), ReplicationActionType.ACTIVATE,
                            Constants.DESTINATION);
                }*/
            }
            for (String path : paths) {
                if (resourceResolver != null) {
                    replicator.replicate(resourceResolver.adaptTo(Session.class), ReplicationActionType.ACTIVATE, path);
                }
            }
            if (resourceResolver != null) {
                removeOldData(resourceResolver);
            }
        } catch (ReplicationException e) {
            log.error("Replication Exception, {}", e.getMessage());
        }
    }

    private void removeOldData(ResourceResolver resourceResolver) {
        Resource dest = resourceResolver.getResource("DESTINATION");
        try {
            if (dest != null) {
                Iterable<Resource> childItr = dest.getChildren();
                for (Resource res : childItr) {
                    Node resNode = res.adaptTo(Node.class);
                    if (resNode != null && !resNode.getPath().equalsIgnoreCase(destFolderPath)) {
                        resNode.remove();
                    }
                }
            }
            resourceResolver.commit();
        } catch (RepositoryException e) {
            log.error("Error while deleting old data from author instance, {}", e.getMessage());
        } catch (PersistenceException e) {
            log.error("Exception while commit, {}", e.getMessage());
        }
    }

    private void createFolder(ResourceResolver resourceResolver) {
        try {
            String pattern = "yyyy-MM-dd HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String jobFolderName = simpleDateFormat.format(new Date());
            jobFolderName = jobFolderName.replace(" ", "T").replace(":", "");
            String newFolderName = "import_" + jobFolderName;
            Resource dest = null;
            if (resourceResolver != null) {
            //    dest = resourceResolver.getResource(Constants.DESTINATION);
            }
            if (dest == null && resourceResolver != null) {
                Resource varBtPlc = resourceResolver.getResource("/var/bt-plc");
                /*if (varBtPlc != null) {
                    final Map<String, Object> nodeProperties = new HashMap<>();
                    nodeProperties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
                    nodeProperties.put(PROPERTY_RESOURCE_TYPE, BTPLC_JOBS_RESOURCE_TYPE);
                    dest = resourceResolver.create(varBtPlc, Constants.CONTRACT_CENTRE_JOBS_NODE_NAME, nodeProperties);
                    paths.add(dest.getPath());
                }*/
            }
            final Map<String, Object> folderProperties = new HashMap<>();
            folderProperties.put(JcrConstants.JCR_PRIMARYTYPE, JcrResourceConstants.NT_SLING_FOLDER);
            if (resourceResolver != null && dest != null) {
                Resource destResource = resourceResolver.create(dest, newFolderName, folderProperties);
                destFolderPath = destResource.getPath();
                paths.add(destFolderPath);
                resourceResolver.commit();
            }
        } catch (PersistenceException e) {
            log.error("Error while creating folder, {}", e.getMessage());
        }
    }

    private ResourceResolver getResolver() {
        ResourceResolver resourceResolver = null;
        try {
            Map<String, Object> xmlReaderMap = new HashMap<>();
            xmlReaderMap.put(ResourceResolverFactory.SUBSERVICE, "xmlReadWriteSubservice");
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(xmlReaderMap);
        } catch (LoginException e) {
            log.error("Error while getting resource resolver, {}", e.getMessage());
        }
        return resourceResolver;
    }
}
