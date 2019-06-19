/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Rest.common.RestErrorHandler;
import dao.HashtagDao;


import domain.Hashtag;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import service.HashtagService;

@Path("Hashtags")
public class HashtagResource {
    
    @Inject
    HashtagService hashtagService;
    
    @GET
    public List<JsonObject> getHashtags() {
        return this.hashtagService.getHashtags().stream().map(Hashtag::toJson).collect(Collectors.toList());
    }
    
    
    @GET
    @Path("{text}")
    public Response getHashTag(@PathParam("text") String text) {
        Hashtag hashtag = this.hashtagService.findHashtag(text);
        if(hashtag == null){
            return RestErrorHandler.getNotFound(Hashtag.class);
        }
        return Response.ok(hashtag.toJson()).build();
    }


}
