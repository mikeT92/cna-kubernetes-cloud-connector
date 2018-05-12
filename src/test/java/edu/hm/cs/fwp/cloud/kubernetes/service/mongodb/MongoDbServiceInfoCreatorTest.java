/*
 * cna-kubernetes-cloud-connector:MongoDbServiceInfoCreatorTest.java
 */
package edu.hm.cs.fwp.cloud.kubernetes.service.mongodb;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.cloud.service.common.MongoServiceInfo;

import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceData;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceType;
import edu.hm.cs.fwp.cloud.kubernetes.service.mongodb.MongoDbServiceInfoCreator;

/**
 * {@code Unit test} for {@link MongoDbServiceInfoCreator}.
 * 
 * @author theism
 * @version 1.0
 * @since 17.11.2017
 */
public class MongoDbServiceInfoCreatorTest {

	public static final String MONGO_SERVICE_NAME = "neo4j-mydata";

	public static final String MONGO_DB_HOST_ENVAR_NAME = "MONGO_DB_HOST";

	public static final String MONGO_DB_HOST_ENVAR_VALUE = "10.0.4.216";

	public static final String MONGO_DB_PORT_ENVAR_NAME = "MONGO_DB_PORT";

	public static final String MONGO_DB_PORT_ENVAR_VALUE = "27017";

	public static final String MONGO_DB_NAME_ENVAR_NAME = "MONGO_DB_NAME";

	public static final String MONGO_DB_NAME_ENVAR_VALUE = "mydata_mongo_db";

	public static final String MONGO_DB_USER_ENVAR_NAME = "MONGO_DB_USER";

	public static final String MONGO_DB_USER_ENVAR_VALUE = "mydata_mongo_user";

	public static final String MONGO_DB_PASSWORD_ENVAR_NAME = "MONGO_DB_PASSWORD";

	public static final String MONGO_DB_PASSWORD_ENVAR_VALUE = "s3cr3t";

	private final MongoDbServiceInfoCreator underTest = new MongoDbServiceInfoCreator();

	@Test
	public void createServiceInfoWithMongoDbServiceDataReturnsExpectedServiceInfo() {
		ServiceData serviceData = new ServiceData.Builder(ServiceType.MONGO, MONGO_SERVICE_NAME)
				.withVariable(MONGO_DB_HOST_ENVAR_NAME, MONGO_DB_HOST_ENVAR_VALUE)
				.withVariable(MONGO_DB_PORT_ENVAR_NAME, MONGO_DB_PORT_ENVAR_VALUE)
				.withVariable(MONGO_DB_NAME_ENVAR_NAME, MONGO_DB_NAME_ENVAR_VALUE)
				.withVariable(MONGO_DB_USER_ENVAR_NAME, MONGO_DB_USER_ENVAR_VALUE)
				.withVariable(MONGO_DB_PASSWORD_ENVAR_NAME, MONGO_DB_PASSWORD_ENVAR_VALUE).build();
		MongoServiceInfo serviceInfo = this.underTest.createServiceInfo(serviceData);
		assertNotNull("createServiceInfo() must not return null", serviceInfo);
		assertEquals("service ID must match", MONGO_SERVICE_NAME, serviceInfo.getId());
		assertEquals("service user must match", MONGO_DB_USER_ENVAR_VALUE, serviceInfo.getUserName());
		assertEquals("service password must match", MONGO_DB_PASSWORD_ENVAR_VALUE, serviceInfo.getPassword());
		assertEquals("service URI must match",
				"mongodb://" + MONGO_DB_USER_ENVAR_VALUE + ":" + MONGO_DB_PASSWORD_ENVAR_VALUE + "@"
						+ MONGO_DB_HOST_ENVAR_VALUE + ":" + MONGO_DB_PORT_ENVAR_VALUE + "/" + MONGO_DB_NAME_ENVAR_VALUE,
				serviceInfo.getUri());
	}
}
