package SearchEngineApplication;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class HistoryFromDBImpl implements ISearchHistoryManagerImpl {

	private static final String MONGODB_URI = "mongodb+srv://sudhanshukrishna3:P1BighBRjjbisSj0@searchengine.q2r6tqk.mongodb.net/?retryWrites=true&w=majority&appName=SearchEngine";
	private static final String DATABASE_NAME = "sample_mflix";
	private static final String COLLECTION_NAME = "search_log";

	@Override
	public List<String> getOldSearchData(String fileName, List<String> driveToBeSearchedIn) throws SearchHistoryManagerException {

		try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
			// Connect to the database
			MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

			// Get the collection
			MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

			// Prepare the query
			Document query = new Document("file_name", fileName).append("drives", driveToBeSearchedIn);

			// Execute the query and check if any records were found
			Document result = collection.find(query).first();
			if (result != null) {
				return result.getList("path", String.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void storeSearchHistory(String fileName, List<String> drives, List<String> searchResults) {

		try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
			// Connect to the database
			MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

			// Get the collection
			MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

			// Create a document to store search history
			Document document = new Document("file_name", fileName).append("drives", drives).append("path",
					searchResults);

			// Insert the document into the collection
			collection.insertOne(document);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to view search history
	public List<Document> viewSearchHistory() {

		try (MongoClient mongoClient = MongoClients.create(MONGODB_URI)) {
			// Connect to the database
			MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

			// Get the collection
			MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

			// Find all documents in the collection
			return collection.find().into(new ArrayList<>());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
