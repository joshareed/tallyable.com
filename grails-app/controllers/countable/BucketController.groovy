package countable

import grails.converters.JSON

class BucketController {
	static allowedMethods = [post: 'POST', create: 'POST']

	def mongoService

	def show() {
		render "Show: ${params.bucket}/${params.key}"
	}

	/* creation methods */
	def check() {
		def bucket = getBucket(params.bucket)
		render ([exists: (bucket != null)] as JSON)
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

	private getBucket(bucket) {
		if (bucket) {
			mongoService.getCollection('buckets', true).find(name: bucket)
		} else {
			null
		}
	}
}
