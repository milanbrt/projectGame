package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class GetGamesRequest extends ContextBasedRequestView {
    public GetGamesRequest(RoutingContext ctx) {
        super(ctx);
    }
        public String getPrefix(){
            return params.queryParameter("prefix").getString();
        }
        public Boolean getOnlyAccepting(){
            if (params.queryParameter("onlyAccepting") == null) {
                return false;
            }
            return params.queryParameter("onlyAccepting").getBoolean();
        }
}
