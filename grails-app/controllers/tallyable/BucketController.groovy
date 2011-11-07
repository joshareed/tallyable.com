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
			[bucket: bucket, stats: bucketService.getStats(bucket.name)]
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
		// parse our key and fragment
		def key = params.key
		def fragment = null
		if (!key) {
			response.sendError(400, 'Key is required')
			return
		}
		if (key.contains(':')) {
			(key, fragment) = key.split(':').collect { it.trim() }
		}
		if (!bucketService.validate(key)) {
			response.sendError(400, 'Invalid key. Only letters, numbers, and ._- allowed')
			return
		}
		if (fragment && !bucketService.validate(fragment)) {
			response.sendError(400, 'Invalid fragment. Only letters, numbers, and ._- allowed')
			return
		}

		// parse the value
		if (!params.value) {
			response.sendError(400, 'Value is required')
			return
		}
		if (!params.value.isNumber()) {
			response.sendError(400, 'Value is limited to decimal numers')
			return
		}

		// post
		withBucket(params) { bucket ->
			def post = bucketService.post(bucket.name, key, fragment, params.value as double)
			flash.message = 'Got it!'
			if (request.isXhr()) {
				render (post as JSON)
			} else if (request.getHeader('referer')) {
				redirect url: request.getHeader('referer')
			} else {
				redirect controller: 'bucket', action: 'show', params: [bucket: bucket.name]
			}
		}
	}

	private withBucket(Map params, Closure closure) {
		def bucket = bucketService.get(params.bucket)
		if (!bucket) {
			response.sendError(404, 'Invalid bucket')
			return
		}

		if (bucket.token != params.secret) {
			response.sendError(401, 'Invalid token')
			return
		}

		return closure.call(bucket)
	}
}
