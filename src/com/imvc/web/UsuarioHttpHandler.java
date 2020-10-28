package com.imvc.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.imvc.controller.UsuarioController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UsuarioHttpHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange)  throws IOException {
		  switch (httpExchange.getRequestMethod()) {        
          case "GET" : 
          	handleGetRequest(httpExchange);
          	break;
          case "POST" : 
          	handlePostRequest(httpExchange);
          	break;
      }
		
		// TODO Auto-generated method stub

	}
	  private void handleGetRequest(HttpExchange httpExchange) throws IOException {
	        UsuarioController controller = new UsuarioController();
	        
	        String request_uri = httpExchange.getRequestURI().toString();
	        OutputStream outStream = httpExchange.getResponseBody();
	        
	        JSONObject json = null;

	        int id = 0;        
	        
	        if(request_uri.split("/").length <= 2){
	            JSONArray json_array = null;
	            try {
	                json_array = controller.Index();
	                httpExchange.sendResponseHeaders(200, json_array.toString().length());
	                outStream.write(json_array.toString().getBytes());
	            } catch (SQLException ex1) {
	                json = new JSONObject();
	                
	                try {
						json.put("status", "not found");
					} catch (JSONException e) {
						e.printStackTrace();
					}
	                outStream.write(json.toString().getBytes());
	                Logger.getLogger(UsuarioHttpHandler.class.getName()).log(Level.SEVERE, null, ex1);
	            }

	            outStream.flush();
	            outStream.close();
	        } else {
	                
	            try {
	                id = Integer.valueOf(request_uri.split("/")[2]);
	                json = controller.show(id);
	                httpExchange.sendResponseHeaders(200, json.toString().length());
	            } catch (SQLException ex) {
	            	Logger.getLogger(UsuarioHttpHandler.class.getName()).log(Level.SEVERE, null, ex);
	                json = new JSONObject();
	                try {
						json.put("status", "not found");
					} catch (JSONException e) {
						e.printStackTrace();
					}
	                httpExchange.sendResponseHeaders(404, json.toString().length());
	            } catch (IOException ex) {
	            	Logger.getLogger(UsuarioHttpHandler.class.getName()).log(Level.SEVERE, null, ex);
	                
	            	json = new JSONObject();
	                
	                try {
						json.put("status", "server error");
					} catch (JSONException e) {
						e.printStackTrace();
					}
	                
	                httpExchange.sendResponseHeaders(500, json.toString().length());
	            }catch(JSONException e) {
	            	e.printStackTrace();
	            }
	            
	            outStream.write(json.toString().getBytes());
	            outStream.flush();
	            outStream.close();
	        }

	    }
	  private void handlePostRequest(HttpExchange httpExchange) throws IOException {
		  
		  	UsuarioController controller = new UsuarioController();
	        OutputStream outStream = httpExchange.getResponseBody();
	       
	        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
	        BufferedReader br = new BufferedReader(isr);
	        StringBuilder buf = new StringBuilder();
	        
	        int b;
	        while((b = br.read()) != -1 ){
	            buf.append((char) b);
	        }
	        
	        br.close();
	        isr.close();
	        try {
		        JSONObject json = new  JSONObject(buf.toString());		        
		        JSONObject jsonResponse = controller.create(json);
		        httpExchange.sendResponseHeaders(201,jsonResponse.toString().length());
		        outStream.write(jsonResponse.toString().getBytes()); 
	        }catch(Exception ex) {
	        	ex.printStackTrace();
	        }
	         
	        System.out.println(buf.toString());
	        outStream.flush();
	        outStream.close();
	        
	    }
}
