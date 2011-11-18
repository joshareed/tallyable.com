package tallyable

class NotificationService {
	def mailService

	private pending = []

	def bucketCreated(bucket) {
		pending << [
			to: bucket.email,
			subject: "Activate Your Tallyable Bucket",
			view: "/email/createBucket",
			model: [bucket: bucket]
		]
	}

	def tokenReset(bucket) {
		pending << [
			to: bucket.email,
			subject: "Tallyable Token Reset",
			view: "/email/resetToken",
			model: [bucket: bucket]
		]
	}

	def sendPending() {
		while (pending) {
			try {
				def params = pending[0]
				mailService.sendMail {
					to params.to
					subject params.subject
					body(view: params.view, model: (params.model ?: [:]))
				}
				pending.remove(params)
			} catch (e) {
				log.error e
			}
		}
	}
}
