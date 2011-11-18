package tallyable

class SendEmailJob {
	static triggers = {
		simple name: 'every 15 seconds', startDelay: 15000, repeatInterval: 15000
	}

	def notificationService

	def execute() {
		notificationService.sendPending()
	}
}
