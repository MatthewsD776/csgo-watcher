package dev.darrenmatthews.csgo;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoConnector {
	
	private static MongoClient mongoClient;
	
	protected String dbName;
	protected String collectionName;
	
	protected MongoConnector(String dbName, String collectionName) {
		this.dbName = dbName;
		this.collectionName = collectionName;
	}
	
	protected void insert() {
		MongoCollection<Document> collection = getCollection();
		Document thisDocument = this.convertToDocument();
		
		collection.insertOne(thisDocument);
	}
	
	private Document convertToDocument() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String jsonString = gson.toJson(this);
		
		return Document.parse(jsonString);
	}

	private MongoCollection<Document> getCollection() {
		return getDatabase().getCollection(this.collectionName);
	}

	private MongoDatabase getDatabase() {
		return mongoClient.getDatabase(this.dbName);
	}

	protected static void createConnection(String connectionString) {
		if(mongoClient == null) {
			MongoClientSettings settings = getMongoClientSettings(connectionString);
			mongoClient = MongoClients.create(settings);
		}
	}

	private static MongoClientSettings getMongoClientSettings(String connectionString) {
		ConnectionString connection = new ConnectionString(connectionString);
		
		Builder settingsBuilder = MongoClientSettings.builder();
		settingsBuilder.applyConnectionString(connection);
		return settingsBuilder.build();
	}

}
