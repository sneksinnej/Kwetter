/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Rest.common.RestErrorHandler;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import service.UserService;

@Path("Users")
@Produces("application/json")
public class UserResource {

    @Inject
    UserService userService;

    @GET
    public Response getUsers() {
        List<User> users = this.userService.getUsers();
        if (users == null || users.isEmpty()) {
            return RestErrorHandler.getNotFound(User.class);
        }
        return Response.ok(users.stream().map(User::toJson).collect(Collectors.toList())).build();
    }

    @GET
    @Path("/{username}")
    public Response getUser(@PathParam("username") String username) {
        User user = this.userService.findUser(username);
        if (user == null) {
            return RestErrorHandler.getNotFound(User.class);
        }
        return Response.ok(user.toJson()).build();
    }

    @POST
    @Path("/createUser/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSimpleUser(String body, @Context UriInfo uriInfo) {
        JsonReader reader = Json.createReader(new StringReader(body));
        JsonObject jsonBody = reader.readObject();
        User user;
        if (!jsonBody.containsKey("username") || jsonBody.getString("username").isEmpty()) {
            return RestErrorHandler.getInvalidInput("no username");
        } else {
            user = this.userService.findUser(jsonBody.getString("username"));
            if(user != null){
                return RestErrorHandler.getAlreadyExisting(User.class);                
            }
        }
        this.userService.createSimpleUser(jsonBody.getString("username"), jsonBody.getString("password"));
        user = this.userService.findUser(jsonBody.getString("username"));
        return Response.created(uriInfo.getAbsolutePathBuilder().path(user.getUsername()).build()).entity(user.toJson()).build();

    }

    @POST
    @Path("/followUser/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response followUser(String body, @Context UriInfo uriInfo) {
        JsonReader reader = Json.createReader(new StringReader(body));
        JsonObject jsonBody = reader.readObject();
        User follower ;
        User following;
        if (!jsonBody.containsKey("username") || jsonBody.getString("username").isEmpty()) {
            return RestErrorHandler.getInvalidInput("no username");
        } else {
            follower = this.userService.findUser(jsonBody.getString("username"));
            if(follower == null){
                return RestErrorHandler.getAlreadyExisting(User.class);                
            }
        }
        if (!jsonBody.containsKey("following") || jsonBody.getString("following").isEmpty()) {
            return RestErrorHandler.getInvalidInput("no username");
        } else {
            following = this.userService.findUser(jsonBody.getString("following"));
            if(following == null){
                return RestErrorHandler.getAlreadyExisting(User.class);                
            }
        }
        if (follower == null || following == null) {
            return RestErrorHandler.getNotFound(User.class);
        }
        this.userService.followUser(follower, following);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(following.getUsername()).build()).entity(following.toJson()).build();
    }
}
