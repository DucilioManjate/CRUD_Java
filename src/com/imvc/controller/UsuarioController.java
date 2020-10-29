package com.imvc.controller;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.imvc.modelo.Usuario;

public class UsuarioController {
	 private Usuario jsonToUsuario(JSONObject json) {
	    	
 		Usuario usuario = new Usuario();
	        
 		try { 
 			if(json.has("codigo"))
 				usuario.setCodigo(json.getInt("codigo"));
 			
        	usuario.setEmail(json.getString("email"));
            usuario.setSenha(json.getString("senha"));
            usuario.setCpf(json.getString("cpf"));    
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
    	return usuario;
        
    }
	  private JSONObject UsuarioToJson(Usuario usuario){
	        JSONObject json = new JSONObject();
	        
	        try { 
		        json.put("codigo", usuario.getCodigo());
		        json.put("email", usuario.getEmail());
		        json.put("senha", usuario.getSenha());
		        json.put("cpf", usuario.getCpf());
		        
	        }catch(JSONException e) {
	        	e.printStackTrace();
	        }
	        
	        return json;
	    }
	  public JSONObject create(JSONObject json) throws SQLException, Exception{
	        Usuario usuario = jsonToUsuario(json);
	        usuario.create();
	        return UsuarioToJson(usuario);
	    }
	    
	    public JSONObject show(int codigo) throws SQLException, JSONException{
	        Usuario usuario = new Usuario();
	        try{
	        	usuario.read(codigo);
	            return UsuarioToJson(usuario);
	       } catch (Exception ex) {
	            JSONObject json = new JSONObject();
	            json.put("erro", ex.getMessage());
	            return json;
	        }
	        
	        
	    }
	    public JSONArray index() throws SQLException{
	        Usuario usuario = new Usuario();
	        List<Usuario> usuarios = usuario.All();
	        JSONArray json = new JSONArray();
	        usuarios.forEach(user -> {
	            json.put(UsuarioToJson(user));
	        });
	        return json;
	    }
	    public JSONObject delete (int codigo) throws SQLException, JSONException{
	    	System.out.println("passou pelo controle");
	    	Usuario usuario = new Usuario();
	        try {
	        	usuario.read(codigo);
	        	System.out.println("deve encontrar : " +usuario);
	        	usuario.delete();
	            return UsuarioToJson(usuario);
	        } catch (Exception ex) {
	            JSONObject json = new JSONObject();
	            json.put("erro", ex.getMessage());
	            return json;
	        }

	    }
	    public JSONObject update(JSONObject json, int codigo) throws SQLException, Exception{
	        Usuario usuario = jsonToUsuario(json);
	        Usuario user = new Usuario(); 
	        user.read(codigo);
	        user.setEmail(usuario.getEmail());
	        user.setSenha(usuario.getSenha());
	        user.setCpf(usuario.getCpf());
	        user.update();
	       
	        return UsuarioToJson(user);
	    }
	    
}
