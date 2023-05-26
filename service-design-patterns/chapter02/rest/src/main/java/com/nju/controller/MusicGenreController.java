package com.nju.controller;


import com.nju.entity.Artists;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

/**
 * @description 查询乐曲
 * @date 2023/5/26 23:51
 * @author: qyl
 */
@Path("/genre")
public class MusicGenreController {
    @GET
    @Path ("/{genreName}/{artistNameStartsWith}")
    @Produces(MediaType.APPLICATION_JSON)
    public Artists getArtistsInGenre(
            @PathParam("genreName") String genreName,
            @PathParam("artistNameStartsWith") String startsWith){
        /**
         * 假装查询辣
         */
        return new Artists (UUID.randomUUID ().toString (), "qyl", 11);
    }
}
