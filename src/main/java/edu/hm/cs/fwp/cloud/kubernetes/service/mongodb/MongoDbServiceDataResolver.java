/*
 * cna-kubernetes-cloud-connector:MongoDbServiceDataResolver.java
 */
package edu.hm.cs.fwp.cloud.kubernetes.service.mongodb;

import edu.hm.cs.fwp.cloud.kubernetes.service.AbstractServiceDataResolver;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceType;

/**
 * {@code ServiceDataResolver} for MongoDB specific service data.
 * 
 * @author theism
 * @version 1.0
 * @since 17.11.2017
 */
public final class MongoDbServiceDataResolver extends AbstractServiceDataResolver {

	public MongoDbServiceDataResolver() {
		super(ServiceType.MONGO, "MONGO_DB_HOST", "MONGO_DB_PORT", "MONGO_DB_NAME", "MONGO_DB_USER",
				"MONGO_DB_PASSWORD");
	}
}
