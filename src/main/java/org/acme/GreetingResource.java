package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import io.vertx.core.eventbus.EventBus;

@Path("/hello")
public class GreetingResource {

    private static final Logger LOG = Logger.getLogger(GreetingResource.class);

    @Inject
    EventBus eventBus;

    @GET
    public Response hello() {

        LOG.info("hello()");
        eventBus.send("my-consume-event", null);

        return Response
                .status(Response.Status.ACCEPTED)
                .build();

    }

    @io.quarkus.vertx.ConsumeEvent("my-consume-event")
    // @io.smallrye.common.annotation.Blocking("my-custom-pool")
    @io.smallrye.common.annotation.Blocking
    public void start(String value) {

        try {
            LOG.info("before the sleep");
            Thread.sleep(5000);
            LOG.info("after the sleep");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}