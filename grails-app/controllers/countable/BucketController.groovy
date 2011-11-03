package countable

class BucketController {
	static allowedMethods = [post: 'POST', create: 'POST']

	def show() {
		render "Show: ${params.bucket}/${params.key}"
	}

	/* creation methods */
	def check() {
		// check if bucket exists
		render "Check: ${params.bucket}"
	}

	def create() {
		render "Create: ${params.bucket}"
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
