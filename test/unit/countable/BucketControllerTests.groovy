package countable

import grails.test.mixin.*
import org.junit.*
import mongo.MongoService

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(BucketController)
class BucketControllerTests {
	def mongoService

	@Before
	void setUp() {
		mongoService = new MongoService('localhost', 'countable_test')
		mongoService.getCollection('buckets', true).add(name: 'test')
		controller.mongoService = mongoService
	}

	@After
	void tearDown() {
		mongoService?.buckets?.drop()
	}

	void testShow() {
		controller.params.putAll(bucket: 'test', key: 'coffee')
		controller.show()
		assert 'Show: test/coffee' == controller.response.contentAsString
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

	void testActivated() {
		controller.params.secret = 'secret'
		controller.activate()
		assert 'secret: Activated!' == controller.response.contentAsString
	}

	void testAdmin() {
		controller.params.secret = 'secret'
		controller.admin()
		assert 'secret: Admin' == controller.response.contentAsString
	}

	void testPost() {
		controller.params.secret = 'secret'
		controller.request.method = "POST"
		controller.post()
		assert 'secret: Post' == controller.response.contentAsString
	}

	void testCreate() {
		controller.params.putAll(bucket: 'test', email: 'test@example.com')
		controller.request.method = "POST"
		controller.create()
		assert 'Create: test' == controller.response.contentAsString
	}
}
