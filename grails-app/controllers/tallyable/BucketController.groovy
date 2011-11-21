package tallyable

import grails.converters.JSON

class BucketController {
	static allowedMethods = [post: 'POST', create: 'POST']

	def bucketService
	def notificationService

	def show() {
		def bucket = bucketService.get(params.bucket)
		if (bucket) {
			def (key, fragment) = parseKey(params)
			def feed = bucketService.getFeed(bucket.name, key, fragment)
			withFormat {
				html {
					def widgets = bucketService.getWidgets(bucket.name, key, fragment)
					[feed: feed, widgets: (widgets as JSON)]
				}
				json {
					render ((feed) as JSON)
				}
			}
		} else {
			response.sendError(404, 'Invalid bucket name')
		}
	}

	/* creation methods */
	def check() {
		def bucket = bucketService.get(params.bucket)
		render ([exists: (bucket != null || !bucketService.validateBucket(params.bucket))] as JSON)
	}

	def create() {
		// validate the request
		def errors = [:]
		if (!params.bucket) {
			errors.bucket = 'A bucket name is required'
		} else if (!bucketService.validateBucket(params.bucket)) {
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
			flash.message = "Your bucket has been created! Check your email for instructions on how to activate and start counting things"
			notificationService.bucketCreated(bucket)
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
			[bucket: bucket, feed: bucketService.getFeed(bucket.name, null, null, 25)]
		}
	}

	def token() {
		withBucket(params) { bucket ->
			bucket = bucketService.newToken(params.bucket)
			flash.message = 'New token generated!'
			notificationService.tokenReset(bucket)
			redirect controller: 'bucket', action: 'admin', params: [bucket: bucket.name, secret: bucket.token]
		}
	}

	def post() {
		// parse our key and fragment
		def (key, fragment) = parseKey(params)
		if (!key) {
			response.sendError(400, 'Key is required')
			return
		}
		if (!bucketService.validateKey(key)) {
			response.sendError(400, 'Invalid key. Only letters, numbers, and ._- allowed')
			return
		}
		if (fragment && !bucketService.validateKey(fragment)) {
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

	private parseKey(params) {
		def key = params.key
		def fragment = params.fragment
		if (key && key.contains(':')) {
			def split = key.split(':')
			key = split[0].trim()
			if (!fragment) {
				fragment = split[1].trim()
			}
		}
		[key, fragment]
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
