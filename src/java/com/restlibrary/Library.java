/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.restlibrary;

import RestLibrary.Object.Book;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 *
 * @author Serjay
 */
@Path("/service")
public class Library {
    
    @GET
    @Path("/get-book")
    @Produces("application/json")

    public Response getBooks() throws Exception {

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("library");

            DBCollection books = db.getCollection("books");

            DBCursor cursor = books.find();
            int i = 1;

            ArrayList documents = new ArrayList();

            while(cursor.hasNext()) {
                documents.add(cursor.next());
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", documents);
            return Response.status(200).entity(jsonObject.toString()).build();
        }
        catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Failed", e);
            return Response.status(200).entity(jsonObject.toString()).build();
        }
    }

    @POST
    @Path("/add-book")
    @Produces("application/json")
    @Consumes("application/json")

    public Response addBook(String book) throws Exception {
            //JSONObject jsonObject = new JSONObject();
            //jsonObject.put("result", book);
            Gson g = new Gson();
            Book add = g.fromJson(book, Book.class);
            //Object obj = (Object) book;
            //Book book2 = (Book) book;
            //Book obj2 = (Book) obj;
            try {

                MongoClient mongoClient = new MongoClient("localhost", 27017);
                DB db = mongoClient.getDB("library");

                DBCollection books = db.getCollection("books");

                BasicDBObject doc = new BasicDBObject("title", add.title)
                        .append("author", add.author)
                        .append("published_year", add.published_year)
                        .append("country", add.country);

                books.insert(doc);
                
                return Response.status(200).entity(add).build();
            }
            catch(Exception e) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Failed", e);
                return Response.status(200).entity(jsonObject.toString()).build();
            }
    }
}
