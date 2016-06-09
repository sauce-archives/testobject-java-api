package org.testobject.rest.api;


public interface SessionReportResource {

    PaginationObject<SessionReport> getSessionReport(String user);

    PaginationObject<SessionReport> getSessionReport(String user, String userId,
                                                     long offset, int limit, int lastDays);
}
