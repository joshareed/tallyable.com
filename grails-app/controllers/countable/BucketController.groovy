package countable

class BucketController {
	static allowedMethods = [post: 'POST', create: 'POST']

	def show() {
		render "Show: ${params.bucket}/${params.key}"
	}

	/* creation methods */
	def check() {
		// check if bucket exists
		render "Check: $params"
	}

	def create() {
		withForm {
			// create the bucket
			render "Create: ${params.bucket}"
			return
		}.invalidToken {
			render "Error"
			return
		}
	}

	/* admin services */
	def activate() {
		// check secret
		render "${params.secret}: Activated!"
	}

	def admin() {
		// check secret
		render "${params.secret}: Admin"
	}

	def post() {
		// check secret
		render "${params.secret}: Post"
	}
}
