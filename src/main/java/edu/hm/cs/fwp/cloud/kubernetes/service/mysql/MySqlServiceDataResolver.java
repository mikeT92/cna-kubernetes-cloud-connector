/*
 * cna-kubernetes-cloud-connector:MySqlServiceDataResolver.java
 */
package edu.hm.cs.fwp.cloud.kubernetes.service.mysql;

import edu.hm.cs.fwp.cloud.kubernetes.service.AbstractServiceDataResolver;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceType;

/**
 * {@code ServiceDataResolver} for MySQL specific service data.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public final class MySqlServiceDataResolver extends AbstractServiceDataResolver {

	/**
	 * Constructs a {@code AbstractServiceDataResolver} with all
	 * MySQL-specific name patterns.
	 */
	public MySqlServiceDataResolver() {
		super(ServiceType.MYSQL, "MYSQL_DB_HOST", "MYSQL_DB_PORT", "MYSQL_DB_NAME", "MYSQL_DB_USER",
				"MYSQL_DB_PASSWORD");
	}
}
