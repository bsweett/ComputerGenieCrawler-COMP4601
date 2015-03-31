package edu.carleton.comp4601.project.database;

import java.net.UnknownHostException;

import org.mongodb.morphia.Morphia;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import edu.carleton.comp4601.project.dao.Product;

public class DatabaseManager {

	public DB getDatabase() {
		return db;
	}

	public void setDatabase(DB db) {
		this.db = db;
	}

	public static void setInstance(DatabaseManager instance) {
		DatabaseManager.instance = instance;
	}

	private DB db;
	private MongoClient mongoClient;
	private Morphia morphia;
	private static DatabaseManager instance;

	public DatabaseManager() {

		try {
			this.morphia = new Morphia();
			this.morphia.map(Product.class);
			
			this.mongoClient = new MongoClient( "localhost" );
			setDatabase(this.mongoClient.getDB( "computergenie" ));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	public DBCollection getProductCollection() {

		DBCollection col;

		if (db.collectionExists("products")) {
			col = db.getCollection("products");
			return col;
		} else {
			DBObject options = BasicDBObjectBuilder.start().add("capped", false).add("size", 2000000000l).get();
			col = db.createCollection("products", options);
			return col;
		}
	}

	
	public boolean addNewProduct(Product product) {

		try {
			DBCollection col = getProductCollection();
			col.insert(this.morphia.toDBObject(product));
			
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 	
	}

	public boolean updateProduct(Product newProduct, Product oldProduct) {

		try {
			DBCollection col = getProductCollection();
			col.update(this.morphia.toDBObject(oldProduct), this.morphia.toDBObject(newProduct));

		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 
	}

	public Product removeProduct(String id) {	

		try {
			BasicDBObject query = new BasicDBObject("id", id);
			DBCollection col = getProductCollection();
			DBObject result = col.findAndRemove(query);
			return morphia.fromDBObject(Product.class, result);
			
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return null;
		}
	}
	
	public int getProductCollectionSize() {
		DBCollection col = this.getProductCollection();
		return (int) col.getCount();
	}

	public static DatabaseManager getInstance() {

		if (instance == null)
			instance = new DatabaseManager();
		return instance;

	}

	public void stopMongoClient() {

		if(this.mongoClient != null) {
			this.mongoClient.close();
		}

	}
}
