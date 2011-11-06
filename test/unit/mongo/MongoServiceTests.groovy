package mongo

import grails.test.mixin.*
import org.junit.*

import com.mongodb.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(MongoService)
class MongoServiceTests {

	@Before
	public void setup() {
		service.grailsApplication = [config: [mongo: [host: 'localhost', db: 'tallyable_test']]]
		def col = service.getCollection('_test_object', true)

		assert col.insert([name: 'Test'] as BasicDBObject)
	}

	@After
	public void tearDown() {
		service.getCollection('_test_object').drop()
		def remove = GroovySystem.metaClassRegistry.&removeMetaClass
		remove MongoService
	}

	void testGetCollection() {
		def coll = service.getCollection('_test_object')
		assert null != coll
	}

	void testGetCollectionNoName() {
		assert null == service.getCollection('')
		assert null == service.getCollection(null)
	}

	void testGetCollectionDoesNotExist() {
		def coll = service.getCollection('doesnotexist')
		assert null == coll
	}

	void testPropertyMissing() {
		assert null != service._test_object
		assert null == service.doesnotexist
	}

	void testDecoratesDBCollection() {
		def col = service._test_object

		// count
		assert 0 == col.count(name: 'Foo')
		assert 1 == col.count(name: 'Test')

		// list
		assert 1 == col.list().size()

		// find
		assert null == col.find(name: 'Foo')
		assert null != col.find(name: 'Test')

		// find w/ filter
		assert 'Test' == col.find(name: 'Test', [name: true]).name
		assert null == col.find(name: 'Test', [name: false]).name

		// add
		col.add(name: 'Test')
		assert 2 == col.count(name: 'Test')

		// findAll
		assert null != col.findAll(name: 'Test')

		// update
		def doc = col.find(name: 'Test')
		doc.name = 'Josh'
		col.update([name: 'Test'], doc)
		assert 1 == col.count(name: 'Josh')

		// remove
		assert 1 == col.count(name: 'Test')
		col.remove(name: 'Test')
		assert 0 == col.count(name: 'Test')


		// method missing
		try {
			col.doesNotExist()
			assert false
		} catch (e) {
			assert true
		}
	}
}
