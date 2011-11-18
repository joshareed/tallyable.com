package tallyable

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class SendEmailJobTests {
	def notificationService
	def job

	void setUp() {
		notificationService = new NotificationService()
		notificationService.mailService = [sendMail: { closure -> }]

		job = new SendEmailJob()
		job.notificationService = notificationService
	}

	void tearDown() {
		// Tear down logic here
	}

	void testExecute() {
		notificationService.bucketCreated(email: 'test@example.com', name: 'test')
		assert 1 == notificationService.pending.size()

		job.execute()

		assert 0 == notificationService.pending.size()
	}
}
