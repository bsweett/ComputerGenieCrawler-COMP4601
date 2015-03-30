package edu.carleton.comp4601.project.database;

import java.net.UnknownHostException;

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
	private static DatabaseManager instance;

	public DatabaseManager() {

		try {
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
			col.insert(buildDBObject(product));
			
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 	
	}

	public boolean updateProduct(Product newProduct, Product oldProduct) {

		try {
			DBCollection col = getProductCollection();
			col.update(buildDBObject(oldProduct), buildDBObject(newProduct));

		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 
	}

	public Product removeUser(String id) {	

		try {
			BasicDBObject query = new BasicDBObject("id", id);
			DBCollection col = getProductCollection();
			DBObject result = col.findAndRemove(query);
			return new Product(result.toMap());
			
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return null;
		}
	}
	
	//TODO: Object Mapping for DB adding 
	public BasicDBObject buildDBObject(Product product) {
		
		BasicDBObject newObj = new BasicDBObject();
		/*newObj.put("authtoken", user.getAuthToken());
		newObj.put("id", user.getId());
		newObj.put("firstname", user.getFirstname());
		newObj.put("lastname", user.getLastname());
		newObj.put("email", user.getEmail());
		newObj.put("passwordhash", user.getPasswordHash());
		newObj.put("gender", user.getGender());
		newObj.put("birthday", user.getBirthday());
		newObj.put("lastlogintime", user.getLastLoginTime());
		newObj.put("productids", user.getProductIds());*/
		return newObj;
		
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
