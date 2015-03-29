package edu.carleton.comp4601.project.database;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

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

	public DBCollection getUserCollection() {

		DBCollection col;

		if (db.collectionExists("users")) {
			col = db.getCollection("users");
			return col;
		} else {
			DBObject options = BasicDBObjectBuilder.start().add("capped", false).add("size", 1000000000l).get();
			col = db.createCollection("users", options);
			return col;
		}
	}

	/*
	public boolean addNewUser(User user) {

		try {
			DBCollection col = getUserCollection();
			col.insert(buildDBObject(user));
			
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 	
	}

	public boolean updateUser(User newUser, User oldUser) {

		try {
			DBCollection col = getUserCollection();
			col.update(buildDBObject(oldUser), buildDBObject(newUser));

		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return false;
		}

		return true; 
	}

	public User removeUser(String id) {	

		try {
			BasicDBObject query = new BasicDBObject("id", id);
			DBCollection col = getUserCollection();
			DBObject result = col.findAndRemove(query);
			return new User(result.toMap());
			
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return null;
		}
	}

	public User findUser(String id) {

		try {

			BasicDBObject query = new BasicDBObject("id", id);
			DBCollection col = getUserCollection();
			DBObject result = col.findOne(query);

			if(result != null) {
				return new User(result.toMap());
			}

			return null;
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return null;
		}

	}
	
	public User findUserByPasswordEmail(String email, String password) {
		try {

			BasicDBObject query = new BasicDBObject("email", email);
			query.append("password", password);
			DBCollection col = getUserCollection();
			DBObject result = col.findOne(query);

			if(result != null) {
				return new User(result.toMap());
			}

			return null;
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return null;
		}
	}
	
	public User findUserByToken(String authToken) {

		try {

			BasicDBObject query = new BasicDBObject("authtoken", authToken);
			DBCollection col = getUserCollection();
			DBObject result = col.findOne(query);

			if(result != null) {
				return new User(result.toMap());
			}

			return null;
		} catch (MongoException e) {
			System.out.println("MongoException: " + e.getLocalizedMessage());
			return null;
		}

	}
	
	public BasicDBObject buildDBObject(User user) {
		
		BasicDBObject newObj = new BasicDBObject();
		newObj.put("authtoken", user.getAuthToken());
		newObj.put("id", user.getId());
		newObj.put("firstname", user.getFirstname());
		newObj.put("lastname", user.getLastname());
		newObj.put("email", user.getEmail());
		newObj.put("passwordhash", user.getPasswordHash());
		newObj.put("gender", user.getGender());
		newObj.put("birthday", user.getBirthday());
		newObj.put("lastlogintime", user.getLastLoginTime());
		newObj.put("productids", user.getProductIds());
		return newObj;
		
	}*/

	public int getUserCollectionSize() {
		DBCollection col = this.getUserCollection();
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
