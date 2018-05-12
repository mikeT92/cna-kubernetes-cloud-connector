/*
 * cna-kubernetes-cloud-connector:PostgresqlServiceInfoCreator.java
 */
package edu.hm.cs.fwp.cloud.kubernetes.service.postgresql;

import org.springframework.cloud.service.common.PostgresqlServiceInfo;

import edu.hm.cs.fwp.cloud.kubernetes.service.AbstractServiceInfoCreator;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceData;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceType;

/**
 * {@code ServiceInfoCreator} for PostgreSQL-specific {@code ServiceInfo}.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public final class PostgresqlServiceInfoCreator extends AbstractServiceInfoCreator<PostgresqlServiceInfo> {

	public PostgresqlServiceInfoCreator() {
		super(ServiceType.POSTGRES);
	}

	/**
	 * @see edu.hm.cs.fwp.cloud.kubernetes.service.AbstractServiceInfoCreator#createServiceInfo(edu.hm.cs.fwp.cloud.kubernetes.service.ServiceData)
	 */
	@Override
	public PostgresqlServiceInfo createServiceInfo(ServiceData serviceData) {
		String hostName = serviceData.getRequiredValue("POSTGRES_DB_HOST");
		int portNumber = serviceData.getRequiredIntValue("POSTGRES_DB_PORT");
		String databaseName = serviceData.getRequiredValue("POSTGRES_DB_NAME");
		String databaseUser = serviceData.getRequiredValue("POSTGRES_DB_USER");
		String databasePassword = serviceData.getRequiredValue("POSTGRES_DB_PASSWORD");
		PostgresqlServiceInfo result = new PostgresqlServiceInfo(serviceData.getName(),
				buildCanonicalUri(hostName, portNumber, databaseName, databaseUser, databasePassword),
				buildJdbcUrl(hostName, portNumber, databaseName, databaseUser, databasePassword));
		this.logger.info("Returning service info [{}] for PostgreSQL service [{}]", result, result.getId());
		return result;
	}

	private String buildCanonicalUri(String hostName, int portNumber, String databaseName, String databaseUser,
			String databasePassword) {
		StringBuilder result = new StringBuilder();
		result.append("postgres://");
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
		result.append("postgresql://");
		result.append(hostName);
		if (portNumber > 0) {
			result.append(":").append(portNumber);
		}
		result.append("/").append(databaseName);
		result.append("?user=").append(databaseUser).append("&password=").append(databasePassword);
		return result.toString();
	}
}
