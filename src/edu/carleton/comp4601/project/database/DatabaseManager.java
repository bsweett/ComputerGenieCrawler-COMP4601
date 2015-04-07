package edu.carleton.comp4601.project.database;

import java.net.UnknownHostException;
import java.util.regex.Pattern;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import edu.carleton.comp4601.project.dao.Product;

public class DatabaseManager {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
	
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
	private Datastore ds;

	public DatabaseManager() {

		try {
			this.morphia = new Morphia();
			this.morphia.map(Product.class);
			this.mongoClient = new MongoClient( "localhost" );
			setDatabase(this.mongoClient.getDB( "computergenie" ));
			
			this.ds = this.morphia.createDatastore(this.mongoClient, "computergenie");
			ds.ensureIndexes();
			ds.ensureCaps();		
			
			logger.info("Initialized Database Manaerger");
		} catch (UnknownHostException e) {
			logger.warn("UnknownHostException: " + e.getLocalizedMessage());
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
			col.save(this.morphia.toDBObject(product));
			
		} catch (MongoException e) {
			logger.warn("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 	
	}

	public boolean updateProduct(Product newProduct, Product oldProduct) {

		try {
			DBCollection col = getProductCollection();
			col.update(this.morphia.toDBObject(oldProduct), this.morphia.toDBObject(newProduct));

		} catch (MongoException e) {
			logger.warn("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 
	}
	
	public Product findAndUpdateProductByTitle(Product product) {
		
		try {
			DBCollection col = getProductCollection();	
			DBObject ref = new BasicDBObject();
			ref.put("title", Pattern.compile(".*" + product.getTitle() +".*" , Pattern.CASE_INSENSITIVE));
			DBObject search = col.findOne(ref);

			if(search != null) {
				Product result = this.morphia.fromDBObject(Product.class, search);
				product.setId(result.getId());
				col.save(this.morphia.toDBObject(product));
			} else {
				this.addNewProduct(product);
			}
			return product;
			
		} catch (MongoException e) {
			logger.warn("MongoException: " + e.getLocalizedMessage());
			return null;
		}
	}

	public Product removeProduct(String id) {	

		try {
			BasicDBObject query = new BasicDBObject("id", id);
			DBCollection col = getProductCollection();
			DBObject result = col.findAndRemove(query);
			return morphia.fromDBObject(Product.class, result);
			
		} catch (MongoException e) {
			logger.warn("MongoException: " + e.getLocalizedMessage());
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
			logger.info("Connection to mongoDB has been closed");
		}

	}
}
