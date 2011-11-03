package mongo

import com.mongodb.*

class MongoService {
	def grailsApplication
	private def _mongo
	boolean transactional = false

	static {
		// some convenience methods
		DBCollection.metaClass {
			count << { LinkedHashMap query -> delegate.count(query as BasicDBObject) }
			list << {  -> delegate.find() }
			findAll << { LinkedHashMap query -> delegate.find(query as BasicDBObject) }
			findAll << { LinkedHashMap query, LinkedHashMap filter -> delegate.find(query as BasicDBObject, filter as BasicDBObject) }
			find << { LinkedHashMap query -> delegate.findOne(query as BasicDBObject) }
			find << { LinkedHashMap query, LinkedHashMap filter -> delegate.findOne(query as BasicDBObject, filter as BasicDBObject) }
			add << { LinkedHashMap doc -> delegate.insert(doc as BasicDBObject) }
			update << { BasicDBObject doc, LinkedHashMap op -> delegate.update(doc, op as BasicDBObject) }
			remove << { LinkedHashMap query -> delegate.remove(query as BasicDBObject) }
		}
	}

	/**
	 * Allow accessing collections like properties.
	 */
	def propertyMissing(name) {
		def collection = getCollection(name)
		if (collection) {
			MongoService.metaClass."$name" = collection
		}
		return collection
	}

	def getMongo() {
		if (!_mongo) {
			// create our Mongo instance if needed
			def host = grailsApplication?.config?.mongo?.host
			def servers = host.split(',').collect { new ServerAddress(it) }
			_mongo = new Mongo(servers)
		}
		_mongo
	}

	/**
	 * Get a collection by name.  If the collection doesn't exist, this method returns null.
	 */
	def getCollection(name) {
		if (!name) { return null }

		// get our database and collection
		def db = mongo.getDB(grailsApplication?.config?.mongo?.db)
		if (db.collectionExists(name)) {
			return db.getCollection(name)
		} else {
			return null
		}
	}
}
