package org.testobject.rest.api;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;


public class SessionReportResourceImpl implements SessionReportResource {

    private final WebTarget target;

    public SessionReportResourceImpl(WebTarget target) {
        this.target = target;
    }


    @Override
    public PaginationObject<SessionReport> getSessionReport(String user) {
        return target
                .path("v1").path("users").path(user).path("session").path("reports")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<PaginationObject<SessionReport>>() {
                });
    }


    @Override
    public PaginationObject<SessionReport> getSessionReport(String user, String userId,
                                                            long offset, int limit, int lastDays) {
        return target
                .path("v1").path("users").path(user).path("session").path("reports")
                .queryParam("userId", userId).queryParam("offset", offset).queryParam("limit", limit)
                .queryParam("lastDays", lastDays)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<PaginationObject<SessionReport>>() {
                });
    }
}
