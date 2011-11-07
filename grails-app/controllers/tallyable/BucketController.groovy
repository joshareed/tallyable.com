package tallyable

import grails.converters.JSON

class BucketController {
	static allowedMethods = [post: 'POST', create: 'POST']

	def bucketService

	def show() {
		render "Show: ${params.bucket}/${params.key}"
	}

	/* creation methods */
	def check() {
		def bucket = bucketService.get(params.bucket)
		render ([exists: (bucket != null)] as JSON)
	}

	def create() {
		// validate the request
		def errors = [:]
		if (!params.bucket) {
			errors.bucket = 'A bucket name is required'
		} else if (!bucketService.validate(params.bucket)) {
			errors.bucket = 'Invalid bucket name'
		}
		if (!params.email) { errors.email = 'An email address is required' }

		// get the bucket
		def bucket = bucketService.get(params.bucket)
		if (bucket) { errors.bucket = 'That bucket is already registered' }

		// bail if errors
		if (errors) {
			render view: '/index', model: [bucket: params.bucket, email: params.email, errors: errors]
			return
		}

		// create the bucket
		bucket = bucketService.create(params.bucket, params.email)
		if (bucket) {
			flash.message = "Your bucket has been registered! Check your email for instructions on how to activate and start counting things"
		} else {
			flash.error = "Oops! Something unexpected happened while creating your bucket"
		}
		redirect uri:"/"
	}

	/* admin services */
	def activate() {
		withBucket(params) { bucket ->
			bucketService.activate(params.bucket)
			flash.message = 'Bucket activated!'
			redirect controller: 'bucket', action: 'admin', params: [bucket: bucket.name, secret: bucket.token]
		}
	}

	def admin() {
		withBucket(params) { bucket ->
			[bucket: bucket]
		}
	}

	def token() {
		withBucket(params) { bucket ->
			bucket = bucketService.newToken(params.bucket)
			flash.message = 'New token generated!'
			redirect controller: 'bucket', action: 'admin', params: [bucket: bucket.name, secret: bucket.token]
		}
	}

	def post() {
		// check secret
		render "${params.secret}: Post"
	}

	private withBucket(Map params, Closure closure) {
		def bucket = bucketService.get(params.bucket)
		if (!bucket) {
			response.status = 404
			render 'Invalid bucket'
			return
		}

		if (bucket.token != params.secret) {
			response.status = 401
			render 'Invalid token'
			return
		}

		return closure.call(bucket)
	}
}
