/*
 * cna-openshift-cloud-connector:ServiceDataTest.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.openshift.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.hm.cs.fwp.cloud.openshift.service.ServiceData;
import edu.hm.cs.fwp.cloud.openshift.service.ServiceType;

/**
 * {@code Unit test} for {@link ServiceData}.
 * 
 * @author theism
 * @version 1.0
 * @since 20.11.2017
 */
public class ServiceDataTest {

	private static final ServiceType SERVICE_TYPE = ServiceType.MYSQL;

	private static final String SERVICE_NAME = "service-name";

	private static final String STRING_VALUE_NAME = "MYSQL_DB_HOST";

	private static final String STRING_VALUE = "mysql-account-service";

	private static final String INT_VALUE_NAME = "MYSQL_DB_PORT";

	private static final String INT_VALUE = "3306";

	private static final String MISSING_VALUE_NAME = "MISSING";

	private ServiceData underTest;

	@Before
	public void onBefore() {
		this.underTest = new ServiceData.Builder(SERVICE_TYPE, SERVICE_NAME)
				.withVariable(STRING_VALUE_NAME, STRING_VALUE).withVariable(INT_VALUE_NAME, INT_VALUE).build();

	}

	@Test
	public void getTypeReturnsExpectedType() {
		assertEquals("service type must match", SERVICE_TYPE, this.underTest.getType());
	}

	@Test
	public void getNameReturnsExpectedName() {
		assertEquals("service name must match", SERVICE_NAME, this.underTest.getName());
	}

	@Test
	public void getValueWithExistingValueReturnsExpectedValue() {
		assertEquals("string value must match", STRING_VALUE, this.underTest.getValue(STRING_VALUE_NAME));
	}

	@Test
	public void getValueWithMissingValueReturnsNull() {
		assertNull("missing value must be null", this.underTest.getValue(MISSING_VALUE_NAME));
	}

	@Test
	public void getRequiredValueWithExistingValueReturnsExpectedValue() {
		assertEquals("string value must match", STRING_VALUE, this.underTest.getRequiredValue(STRING_VALUE_NAME));
	}

	@Test(expected = IllegalStateException.class)
	public void getRequiredValueWithMissingValueThrowsIllegalStateException() {
		String missing = this.underTest.getRequiredValue(MISSING_VALUE_NAME);
		fail("must throw IllegalStateException");
	}

	@Test
	public void getIntValueWithExistingValueReturnsExpectedValue() {
		assertEquals("int value must match", Integer.parseInt(INT_VALUE), this.underTest.getIntValue(INT_VALUE_NAME));
	}

	@Test
	public void getIntValueWithMissingValueReturnsZero() {
		assertEquals("missing value must be 0", 0, this.underTest.getIntValue(MISSING_VALUE_NAME));
	}

	@Test
	public void getRequiredIntValueWithExistingValueReturnsExpectedValue() {
		assertEquals("string value must match", Integer.parseInt(INT_VALUE),
				this.underTest.getRequiredIntValue(INT_VALUE_NAME));
	}

	@Test(expected = IllegalStateException.class)
	public void getRequiredIntValueWithMissingValueThrowsIllegalStateException() {
		int missing = this.underTest.getRequiredIntValue(MISSING_VALUE_NAME);
		fail("must throw IllegalStateException");
	}

}
