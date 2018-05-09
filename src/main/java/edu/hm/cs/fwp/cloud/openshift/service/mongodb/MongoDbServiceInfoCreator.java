/*
 * cna-openshift-cloud-connector:MongoDbServiceInfoCreator.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.openshift.service.mongodb;

import org.springframework.cloud.service.common.MongoServiceInfo;

import edu.hm.cs.fwp.cloud.openshift.service.AbstractServiceInfoCreator;
import edu.hm.cs.fwp.cloud.openshift.service.ServiceData;
import edu.hm.cs.fwp.cloud.openshift.service.ServiceType;

/**
 * {@code ServiceInfoCreator} for MongoDB-specific {@code MongoServiceInfo}.
 * 
 * @author theism
 * @version 1.0
 * @since 17.11.2017
 */
public final class MongoDbServiceInfoCreator extends AbstractServiceInfoCreator<MongoServiceInfo> {

	public MongoDbServiceInfoCreator() {
		super(ServiceType.MONGO);
	}

	/**
	 * @see edu.hm.cs.fwp.cloud.openshift.service.AbstractServiceInfoCreator#createServiceInfo(edu.hm.cs.fwp.cloud.openshift.service.ServiceData)
	 */
	@Override
	public MongoServiceInfo createServiceInfo(ServiceData serviceData) {
		String hostName = serviceData.getRequiredValue("MONGO_DB_HOST");
		int portNumber = serviceData.getRequiredIntValue("MONGO_DB_PORT");
		String databaseName = serviceData.getRequiredValue("MONGO_DB_NAME");
		String databaseUser = serviceData.getRequiredValue("MONGO_DB_USER");
		String databasePassword = serviceData.getRequiredValue("MONGO_DB_PASSWORD");
		MongoServiceInfo result = new MongoServiceInfo(serviceData.getName(),
				buildCanonicalUri(hostName, portNumber, databaseName, databaseUser, databasePassword));
		this.logger.info("Returning service info [{}] for MongoDB service [{}]", result, result.getId());
		return result;
	}

	private String buildCanonicalUri(String hostName, int portNumber, String databaseName, String databaseUser,
			String databasePassword) {
		StringBuilder result = new StringBuilder();
		result.append("mongodb://");
		result.append(databaseUser).append(":").append(databasePassword).append("@");
		result.append(hostName);
		if (portNumber > 0) {
			result.append(":").append(portNumber);
		}
		result.append("/").append(databaseName);
		return result.toString();
	}
}
