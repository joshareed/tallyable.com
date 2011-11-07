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
	}

	void testValidate() {
		assert !service.validate(null)
		assert !service.validate('')
		assert service.validate('._.')
		assert service.validate('Test-Bucket')
		assert service.validate('TEST.bucket')
		assert !service.validate('$invalid')
		assert !service.validate('&invalid')
		assert !service.validate('*invalid')
		assert !service.validate('@invalid')
		assert !service.validate('=invalid')
		assert !service.validate('Bucket')
		assert !service.validate('Index')
		assert !service.validate('test')
		assert !service.validate('Testing')
		assert !service.validate('tallyable')
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
