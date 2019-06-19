/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Rest.common.RestErrorHandler;
import domain.Kweet;
import domain.User;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import service.KweetService;
import service.UserService;

@Path("Kweets")
public class KweetResource {

    @Inject
    KweetService kweetService;

    @Inject
    UserService userService;

    @GET
    public Response getKweets() {
        List<Kweet> kweets = this.kweetService.getKweets();
        if (kweets == null || kweets.isEmpty()) {
            return RestErrorHandler.getNotFound(Kweet.class);
        }
        return Response.ok(kweets.stream().map(Kweet::toJson).collect(Collectors.toList())).build();
    }

    @GET
    @Path("{id}")
    public Response getKweetById(@PathParam("id") String id) {
        Kweet kweet = this.kweetService.getKweet(id);
        if (kweet == null) {
            return RestErrorHandler.getNotFound(Kweet.class);
        }
        return Response.ok(kweet.toJson()).build();
    }

    @POST
    @Path("createKweet/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createKweet(String body, @Context UriInfo uriInfo) {
        JsonReader reader = Json.createReader(new StringReader(body));
        JsonObject jsonBody = reader.readObject();
        if (!jsonBody.containsKey("username") || jsonBody.getString("username").isEmpty()) {
            return RestErrorHandler.getInvalidInput(" no username");
        }
        User kweetUser = userService.findUser(jsonBody.getString("username"));
        if (kweetUser == null) {
            return RestErrorHandler.getNotFound(User.class);
        }
        if (!jsonBody.containsKey("text") || jsonBody.getString("text").isEmpty()) {
            return RestErrorHandler.getInvalidInput(" no kweet text");
        }
        Kweet kweet = this.kweetService.createKweet(jsonBody.getString("text"), kweetUser);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(kweet.getId().toString()).build()).entity(kweet.toJson()).build();
    }

    @POST
    @Path("likeKweet/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response likeKweet(String body, @Context UriInfo uriInfo) {
        JsonReader reader = Json.createReader(new StringReader(body));
        JsonObject jsonBody = reader.readObject();
        User likeUser;
        Kweet kweet;
        if (!jsonBody.containsKey("username") || jsonBody.getString("username").isEmpty()) {
            return RestErrorHandler.getInvalidInput(" no username");
        } else {
            likeUser = this.userService.findUser(jsonBody.getString("username"));
            if (likeUser == null) {
                return RestErrorHandler.getNotFound(User.class);
            }
        }
        if (!jsonBody.containsKey("kweet") || jsonBody.getString("kweet").isEmpty()) {
            return RestErrorHandler.getInvalidInput(" no kweet");
        } else {
            kweet = this.kweetService.getKweet(jsonBody.getString("kweet"));
            if (kweet == null) {
                return RestErrorHandler.getNotFound(Kweet.class);
            }
        }
        this.kweetService.likeKweet(kweet, likeUser);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(kweet.getId().toString()).build()).entity(kweet.toJson()).build();
    }

}
