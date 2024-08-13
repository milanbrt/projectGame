package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class DeleteGamesRequest extends ContextBasedRequestView {
    public DeleteGamesRequest(RoutingContext ctx) {
        super(ctx);
    }
}
