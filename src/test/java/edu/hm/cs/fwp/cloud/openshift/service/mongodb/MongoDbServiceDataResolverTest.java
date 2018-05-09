/*
 * cna-openshift-cloud-connector:MongoDbServiceDataResolverTest.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.openshift.service.mongodb;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.cloud.util.EnvironmentAccessor;

import edu.hm.cs.fwp.cloud.openshift.service.ServiceData;
import edu.hm.cs.fwp.cloud.openshift.service.ServiceType;
import edu.hm.cs.fwp.cloud.openshift.service.mongodb.MongoDbServiceDataResolver;

/**
 * {@code Unit test} for {@link MongoDbServiceDataResolver}.
 * 
 * @author theism
 * @version 1.0
 * @since 17.11.2017
 */
public class MongoDbServiceDataResolverTest {

	public static final String MONGO_DB_HOST_ENVAR_NAME = "MONGO_DB_HOST";

	public static final String MONGO_DB_HOST_ENVAR_VALUE = "10.0.4.214";

	public static final String MONGO_DB_PORT_ENVAR_NAME = "MONGO_DB_PORT";

	public static final String MONGO_DB_PORT_ENVAR_VALUE = "27017";

	public static final String MONGO_DB_NAME_ENVAR_NAME = "MONGO_DB_NAME";

	public static final String MONGO_DB_NAME_ENVAR_VALUE = "mydata_mongo_db";

	public static final String MONGO_DB_USER_ENVAR_NAME = "MONGO_DB_USER";

	public static final String MONGO_DB_USER_ENVAR_VALUE = "mydata_mongo_user";

	public static final String MONGO_DB_PASSWORD_ENVAR_NAME = "MONGO_DB_PASSWORD";

	public static final String MONGO_DB_PASSWORD_ENVAR_VALUE = "s3cr3t";

	private final MongoDbServiceDataResolver underTest = new MongoDbServiceDataResolver();

	@Test
	public void resolveReturnsMySqlServiceDataIfMySqlEnvVarsAreSet() {
		Map<String, String> valuesByName = new HashMap<>();
		valuesByName.put(MONGO_DB_HOST_ENVAR_NAME, MONGO_DB_HOST_ENVAR_VALUE);
		valuesByName.put(MONGO_DB_PORT_ENVAR_NAME, MONGO_DB_PORT_ENVAR_VALUE);
		valuesByName.put(MONGO_DB_NAME_ENVAR_NAME, MONGO_DB_NAME_ENVAR_VALUE);
		valuesByName.put(MONGO_DB_USER_ENVAR_NAME, MONGO_DB_USER_ENVAR_VALUE);
		valuesByName.put(MONGO_DB_PASSWORD_ENVAR_NAME, MONGO_DB_PASSWORD_ENVAR_VALUE);
		EnvironmentAccessor environment = Mockito.mock(EnvironmentAccessor.class);
		Mockito.when(environment.getEnv()).thenReturn(valuesByName);
		List<ServiceData> serviceData = this.underTest.resolve(environment);
		assertNotNull("resolve() must not return null", serviceData);
		assertTrue("resolve() must not return empty list", !serviceData.isEmpty());
		ServiceData mongoServiceData = serviceData.get(0);
		assertEquals("service type must match", ServiceType.MONGO, mongoServiceData.getType());
		assertEquals("service name must match", "mongo-unnamed", mongoServiceData.getName());
		assertEquals("database host must match", MONGO_DB_HOST_ENVAR_VALUE,
				mongoServiceData.getRequiredValue(MONGO_DB_HOST_ENVAR_NAME));
		assertEquals("database port must match", MONGO_DB_PORT_ENVAR_VALUE,
				mongoServiceData.getRequiredValue(MONGO_DB_PORT_ENVAR_NAME));
		assertEquals("database name must match", MONGO_DB_NAME_ENVAR_VALUE,
				mongoServiceData.getRequiredValue(MONGO_DB_NAME_ENVAR_NAME));
		assertEquals("database user must match", MONGO_DB_USER_ENVAR_VALUE,
				mongoServiceData.getRequiredValue(MONGO_DB_USER_ENVAR_NAME));
		assertEquals("database password must match", MONGO_DB_PASSWORD_ENVAR_VALUE,
				mongoServiceData.getRequiredValue(MONGO_DB_PASSWORD_ENVAR_NAME));
	}
}
