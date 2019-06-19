/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest.common;

import javax.ws.rs.core.Response;

//ErrorHelper class (gemaakt met hulp van Mark Slaats)
public class RestErrorHandler {

    public static Response getNotFound(Class className) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(String.format("{\"error_description\":\"%s not found\"}", className.getSimpleName()))
                .build();
    }

    public static Response getAlreadyExisting(Class className) {
        return Response.status(Response.Status.CONFLICT)
                .entity(String.format("{\"error_description\":\"%s already existing\"}", className.getSimpleName()))
                .build();
    }

    public static Response getInvalidInput(String error) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(String.format("{\"error_description\":\"%s invalid input: \"}", error))
                .build();
    }
}
