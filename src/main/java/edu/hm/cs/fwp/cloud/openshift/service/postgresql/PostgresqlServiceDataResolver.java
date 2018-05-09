/*
 * cna-openshift-cloud-connector:PostgresqlServiceDataResolver.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.openshift.service.postgresql;

import edu.hm.cs.fwp.cloud.openshift.service.AbstractServiceDataResolver;
import edu.hm.cs.fwp.cloud.openshift.service.ServiceType;

/**
 * {@code ServiceDataResolver} for PostgreSQL specific service data.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public final class PostgresqlServiceDataResolver extends AbstractServiceDataResolver {

	/**
	 * Constructs a {@code AbstractServiceDataResolver} with all PostgreSQL-specific
	 * name patterns.
	 */
	public PostgresqlServiceDataResolver() {
		super(ServiceType.POSTGRES, "POSTGRES_DB_HOST", "POSTGRES_DB_NAME", "POSTGRES_DB_USER", "POSTGRES_DB_PASSWORD");
	}
}
