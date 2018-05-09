/*
 * cna-openshift-cloud-connector:MySqlServiceInfoCreator.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.openshift.service.mysql;

import org.springframework.cloud.service.common.MysqlServiceInfo;

import edu.hm.cs.fwp.cloud.openshift.service.AbstractServiceInfoCreator;
import edu.hm.cs.fwp.cloud.openshift.service.ServiceData;
import edu.hm.cs.fwp.cloud.openshift.service.ServiceType;

/**
 * {@code ServiceInfoCreator} for MySQL-specific {@code ServiceInfo}.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public final class MySqlServiceInfoCreator extends AbstractServiceInfoCreator<MysqlServiceInfo> {

	public MySqlServiceInfoCreator() {
		super(ServiceType.MYSQL);
	}

	/**
	 * @see edu.hm.cs.fwp.cloud.openshift.service.AbstractServiceInfoCreator#createServiceInfo(edu.hm.cs.fwp.cloud.openshift.service.ServiceData)
	 */
	@Override
	public MysqlServiceInfo createServiceInfo(ServiceData serviceData) {
		String hostName = serviceData.getRequiredValue("MYSQL_DB_HOST");
		int portNumber = serviceData.getRequiredIntValue("MYSQL_DB_PORT");
		String databaseName = serviceData.getRequiredValue("MYSQL_DB_NAME");
		String databaseUser = serviceData.getRequiredValue("MYSQL_DB_USER");
		String databasePassword = serviceData.getRequiredValue("MYSQL_DB_PASSWORD");
		MysqlServiceInfo result = new MysqlServiceInfo(serviceData.getName(),
				buildCanonicalUri(hostName, portNumber, databaseName, databaseUser, databasePassword),
				buildJdbcUrl(hostName, portNumber, databaseName, databaseUser, databasePassword));
		this.logger.info("Returning service info [{}] for MySQL service [{}]", result, result.getId());
		return result;
	}

	private String buildCanonicalUri(String hostName, int portNumber, String databaseName, String databaseUser,
			String databasePassword) {
		StringBuilder result = new StringBuilder();
		result.append("mysql://");
		result.append(databaseUser).append(":").append(databasePassword).append("@");
		result.append(hostName);
		if (portNumber > 0) {
			result.append(":").append(portNumber);
		}
		result.append("/").append(databaseName);
		return result.toString();
	}

	private String buildJdbcUrl(String hostName, int portNumber, String databaseName, String databaseUser,
			String databasePassword) {
		StringBuilder result = new StringBuilder();
		result.append("jdbc:");
		result.append("mysql://");
		result.append(hostName);
		if (portNumber > 0) {
			result.append(":").append(portNumber);
		}
		result.append("/").append(databaseName);
		result.append("?user=").append(databaseUser).append("&password=").append(databasePassword);
		return result.toString();
	}
}
