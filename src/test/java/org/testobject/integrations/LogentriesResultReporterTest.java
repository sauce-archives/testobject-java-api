package org.testobject.integrations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.lang.annotation.Annotation;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LogentriesResultReporterTest {

	private LogentriesResultReporter resultReporter;
	private Invocation.Builder builder;

	@Before
	public void setUp() throws Exception {
		Client client = mock(Client.class);
		WebTarget target = mock(WebTarget.class);
		this.builder = mock(Invocation.Builder.class);
		when(client.target(anyString())).thenReturn(target);
		when(target.path(anyString())).thenReturn(target);
		when(target.request()).thenReturn(builder);

		resultReporter = new LogentriesResultReporter(client, "mockToken");
	}

	@Test(expected = NullPointerException.class)
	public void failIfLogentriesTokenIsMissing() throws Exception {
		new LogentriesResultReporter();
	}

	@Test
	public void testSucceed() throws Exception {
		String testName = "TestClass";
		ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);

		Response response = mock(Response.class);
		when(response.getStatus()).thenReturn(204);
		when(builder.post(Entity.json(any()))).thenReturn(response);

		Description description = Description.createSuiteDescription( testName, (Annotation) null);
		resultReporter.starting(description);
		resultReporter.succeeded(description);
		resultReporter.finished(description);

		verify(builder).post(entityCaptor.capture());
		Entity e = entityCaptor.getValue();
		Map<String, Object> json = (Map<String, Object>) e.getEntity();
		assertEquals(testName, json.get("testName"));
		assertEquals(org.testobject.integrations.Test.State.SUCCESS.name(), json.get("result"));
	}

	@Test
	public void testFailed() throws Exception {
		String testName = "TestClass";
		ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);

		Response response = mock(Response.class);
		when(response.getStatus()).thenReturn(204);
		when(builder.post(Entity.json(any()))).thenReturn(response);

		Description description = Description.createSuiteDescription( testName, (Annotation) null);
		resultReporter.starting(description);
		resultReporter.failed(new RuntimeException(), description);
		resultReporter.finished(description);

		verify(builder).post(entityCaptor.capture());
		Entity e = entityCaptor.getValue();
		Map<String, Object> json = (Map<String, Object>) e.getEntity();
		assertEquals(testName, json.get("testName"));
		assertEquals(org.testobject.integrations.Test.State.FAILED.name(), json.get("result"));
	}

	@Test
	public void testSkipped() throws Exception {
		String testName = "TestClass";
		ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);

		Response response = mock(Response.class);
		when(response.getStatus()).thenReturn(204);
		when(builder.post(Entity.json(any()))).thenReturn(response);

		Description description = Description.createSuiteDescription( testName, (Annotation) null);
		resultReporter.starting(description);
		resultReporter.failed(new RuntimeException(), description);
		resultReporter.finished(description);

		verify(builder).post(entityCaptor.capture());
		Entity e = entityCaptor.getValue();
		Map<String, Object> json = (Map<String, Object>) e.getEntity();
		assertEquals(testName, json.get("testName"));
		assertEquals(org.testobject.integrations.Test.State.FAILED.name(), json.get("result"));
	}
}