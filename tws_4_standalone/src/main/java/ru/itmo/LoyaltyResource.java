/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.itmo;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/loyalty")
@Produces({MediaType.APPLICATION_JSON})

public class LoyaltyResource {
    @GET
    public ArrayList<Loyalty> getAll() {
        return (ArrayList<Loyalty>) new PostgreSQLDAO().getAll();
    }
    
    @POST
    public String createLoyalty(
            @QueryParam("spbso") int spbso,
            @QueryParam("name") String name,
            @QueryParam("brigade") String brigade,
            @QueryParam("event") String event,
            @QueryParam("cash") int cash) {
        return new PostgreSQLDAO().createLoyalty(spbso, name, brigade, event, cash);
    }

    @DELETE
    public String deleteLoyalty(@QueryParam("rowId") int rowId) {
        return new PostgreSQLDAO().deleteLoyalty(rowId);
    }

    @PUT
    public String updateLoyalty(
            @QueryParam("rowId") int rowId,
            @QueryParam("spbso") String spbso,
            @QueryParam("name") String name,
            @QueryParam("brigade") String brigade,
            @QueryParam("event") String event,
            @QueryParam("cash") String cash) {

        List<String> updateArgs = new ArrayList<>();
        
        if (spbso != null && !spbso.trim().isEmpty())
            updateArgs.add("spbso = '" + spbso + "'");
        if (name != null && !name.trim().isEmpty()) updateArgs.add("name = '" + name + "'");
        if (brigade != null && !brigade.trim().isEmpty()) updateArgs.add("brigade = '" + brigade + "'");
        if (event != null && !event.trim().isEmpty())
            updateArgs.add("event = '" + event + "'");
        if (cash != null && !cash.trim().isEmpty()) updateArgs.add("cash = '" + cash + "'");

        return new PostgreSQLDAO().updateLoyalty(rowId, updateArgs);
    }
}