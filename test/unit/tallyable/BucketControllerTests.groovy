package tallyable

import grails.test.mixin.*
import org.junit.*

import mongo.MongoService

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(BucketController)
class BucketControllerTests {
	def bucketService
	def mongoService

	@Before
	void setUp() {
		mongoService = new MongoService('localhost', 'tallyable_test')
		mongoService.getCollection('buckets', true).add(name: 'test', token: 'secret')
		bucketService = new BucketService()
		bucketService.mongoService = mongoService
		controller.bucketService = bucketService
	}

	@After
	void tearDown() {
		mongoService?.buckets?.drop()
	}

	void testShow() {
		controller.params.putAll(bucket: 'test', key: 'coffee')
		request.contentType = 'text/html'
		controller.show()
		assert 200 == controller.response.status
	}

	void testCheckDoesNotExists() {
		controller.params.bucket = 'test-does-not-exists'
		controller.check()
		assert '{"exists":false}' == controller.response.contentAsString
	}

	void testCheckExists() {
		controller.params.bucket = 'test'
		controller.check()
		assert '{"exists":true}' == controller.response.contentAsString
	}

	void testCheckMalformed() {
		controller.check()
		assert '{"exists":false}' == controller.response.contentAsString
	}

	void testActivateNoBucket() {
		controller.params.secret = 'secret'
		controller.activate()
		assert 404 == controller.response.status
	}

	void testActivateNoSecret() {
		controller.params.bucket = 'test'
		controller.activate()
		assert 401 == controller.response.status
	}

	void testActivate() {
		controller.params.bucket = 'test'
		controller.params.secret = 'secret'
		controller.activate()
		assert 302 == controller.response.status
		assert '/bucket/admin?bucket=test&secret=secret' == controller.response.redirectedUrl
	}

	void testAdmin() {
		controller.params.bucket = 'test'
		controller.params.secret = 'secret'
		controller.admin()
		assert 200 == controller.response.status
	}

	void testPost() {
		controller.params.putAll(bucket: 'test', secret: 'secret', key: 'key', value: '1')
		controller.request.method = "POST"
		controller.post()
		assert 'Got it!' == controller.flash.message
	}

	void testCreate() {
		controller.params.putAll(bucket: 'refactr', email: 'test@example.com')
		controller.request.method = "POST"
		controller.create()
		assert 'Your bucket has been registered! Check your email for instructions on how to activate and start counting things' == controller.flash.message
		assert '/' == controller.response.redirectedUrl
	}

	void testCreateRequiresBucket() {
		controller.params.putAll(email: 'test@example.com')
		controller.request.method = "POST"
		controller.create()
		assert '/index' == view
		assert [bucket: null, email: 'test@example.com', errors: [bucket: 'A bucket name is required']] == model
	}

	void testCreateRequiresEmail() {
		controller.params.putAll(bucket: 'refactr')
		controller.request.method = "POST"
		controller.create()
		assert '/index' == view
		assert [bucket: 'refactr', email: null, errors: [email: 'An email address is required']] == model
	}

	void testCreateRequiresBucketAndEmail() {
		controller.request.method = "POST"
		controller.create()
		assert '/index' == view
		assert [bucket: null, email: null, errors: [bucket: 'A bucket name is required', email: 'An email address is required']] == model
	}

	void testCreateAlreadyExists() {
		controller.params.putAll(bucket: 'test', email: 'test@example.com')
		controller.request.method = "POST"
		controller.create()
		assert '/index' == view
		assert [bucket: 'test', email: 'test@example.com', errors: [bucket: 'That bucket is already registered']] == model
	}
}
