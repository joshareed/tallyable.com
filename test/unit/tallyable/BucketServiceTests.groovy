package tallyable

import grails.test.mixin.*
import org.junit.*

import mongo.MongoService

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BucketService)
class BucketServiceTests {
	def mongoService

	@Before
	void setUp() {
		mongoService = new MongoService('localhost', 'tallyable_test')
		mongoService.getCollection('buckets', true).add(name: 'test', token: 'secret')
		service.mongoService = mongoService
	}

	@After
	void tearDown() {
		mongoService?.buckets?.drop()
		mongoService.mongo.close()
	}

	void testValidateBucket() {
		assert !service.validateBucket(null)
		assert !service.validateBucket('')
		assert service.validateBucket('._.')
		assert service.validateBucket('Test-Bucket')
		assert service.validateBucket('TEST.bucket')
		assert !service.validateBucket('$invalid')
		assert !service.validateBucket('&invalid')
		assert !service.validateBucket('*invalid')
		assert !service.validateBucket('@invalid')
		assert !service.validateBucket('=invalid')
		assert !service.validateBucket('Bucket')
		assert !service.validateBucket('Index')
		assert !service.validateBucket('test')
		assert !service.validateBucket('Testing')
		assert !service.validateBucket('tallyable')
		assert !service.validateBucket('123456789012345678901234567890123456789012345678901234567890123456789012345678901')
	}

	void testGet() {
		assert !service.get('invalid')
		assert service.get('test')
	}

	void testCreate() {
		assert !service.get('josh')

		def bucket = service.create('josh', 'josh@refactr.com')
		assert bucket
		assert 'josh' == bucket.name
		assert 'josh@refactr.com' == bucket.email
		assert bucket.token
		assert bucket.enabled
		assert !bucket.activated
		assert bucket.'public'
		assert bucket.created instanceof Date
	}

	void testActivate() {
		def bucket = service.create('josh', 'josh@refactr.com')
		assert !bucket.activated
		service.activate('josh')
		bucket = service.get('josh')
		assert bucket.activated
	}

	void testNewToken() {
		assert 'secret' == service.get('test').token
		service.newToken('test')
		assert 'secret' != service.get('test').token
	}
}
