package com.ddp.nfl.web.data.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;


public class SeasonTypeDeserializer extends StdDeserializer<Type>{

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_REG_SEASON_TYPE = 2;

	public SeasonTypeDeserializer() { 
		this(null); 
	} 

	public SeasonTypeDeserializer(Class<Type> t) {
		super(t); 
	}

	@Override
	public Type deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		JsonNode node 	= jsonParser.getCodec().readTree(jsonParser);
		
		String id	 	=  nodeToString(node, "id");
        int type		= nodeToInteger(node, "type", DEFAULT_REG_SEASON_TYPE);
        String name 	=  nodeToString(node, "name");
        String abbvr 	=  nodeToString(node, "abbreviation");
        String state 	=  nodeToString(node, "state");
        boolean completed= nodeToBoolean(node, "completed");
        String description 	=  nodeToString(node, "description");
        String detail 	=  nodeToString(node, "detail");
        String shortDetail 	=  nodeToString(node, "shortDetail");
        String shortName 	= nodeToString(node, "shortName");
        
		return new Type(id, type, name, abbvr, state, completed, description, detail, shortDetail, shortName);
	}


	public static String nodeToString( JsonNode parentNode, String fieldName ) {
	     JsonNode node 	= parentNode.get(fieldName);
	     return ( node == null ) ? "" : node.asText("");
	}
	
	
	public static int nodeToInteger( JsonNode parentNode, String fieldName, int defaultValue ) {
	     JsonNode node 	= parentNode.get(fieldName);
	     return ( node == null ) ? defaultValue : node.asInt(defaultValue);
	}	
	
	public static boolean nodeToBoolean( JsonNode parentNode, String fieldName){
	     JsonNode node 	= parentNode.get(fieldName);
	     return ( node == null ) ? false : node.asBoolean(false);
	}	
	
	
}


