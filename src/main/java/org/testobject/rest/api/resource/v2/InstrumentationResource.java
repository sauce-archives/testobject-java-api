package org.testobject.rest.api.resource.v2;

import org.testobject.rest.api.model.InstrumentationRequestData;
import org.testobject.rest.api.model.XcuiTestReport;

public interface InstrumentationResource {

	long createAndStartXCUITestInstrumentation(String userName, String apiKey,
			InstrumentationRequestData requestData);

	String getJUnitReport(String userName, String apiKey, long reportId);

	XcuiTestReport getTestReport(String userName, String apiKey, long reportId);
}
