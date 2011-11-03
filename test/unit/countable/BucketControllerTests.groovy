package countable

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(BucketController)
class BucketControllerTests {

	void testShow() {
		controller.params.putAll(bucket: 'test', key: 'coffee')
		controller.show()
		assert 'Show: test/coffee' == controller.response.contentAsString
	}

	void testCheck() {
		controller.params.bucket = 'test'
		controller.check()
		assert 'Check: test' == controller.response.contentAsString
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
