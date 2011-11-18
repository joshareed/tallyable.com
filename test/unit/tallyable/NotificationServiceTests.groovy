package tallyable



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(NotificationService)
class NotificationServiceTests {

	void testBucketCreated() {
		assert !service.pending

		service.bucketCreated(email: 'test@example.com', name: 'test')
		assert 1 == service.pending.size()
		assert [
			to: 'test@example.com',
			subject: "Activate Your Tallyable Bucket",
			view: "/email/createBucket",
			model: [bucket: [email: 'test@example.com', name: 'test']]
		] == service.pending[0]
	}

	void testTokenReset() {
		assert !service.pending

		service.tokenReset(email: 'test@example.com', name: 'test')
		assert 1 == service.pending.size()
		assert [
			to: 'test@example.com',
			subject: "Tallyable Token Reset",
			view: "/email/resetToken",
			model: [bucket: [email: 'test@example.com', name: 'test']]
		] == service.pending[0]
	}
}
