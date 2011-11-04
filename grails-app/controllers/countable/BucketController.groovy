package countable

import grails.converters.JSON

import java.security.MessageDigest

class BucketController {
	private static final String COLLECTION = "buckets"
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
		// validate the request
		def errors = [:]
		if (!params.bucket) { errors.bucket = 'A bucket name is required' }
		if (!params.email) { errors.email = 'An email address is required' }

		def bucket = getBucket(params.bucket)
		if (bucket) { errors.bucket = 'That bucket is already registered' }

		if (errors) {
			render view: '/index', model: [bucket: params.bucket, email: params.email, errors: errors]
			return
		}

		// create the bucket
		bucket = [
			'name': params.bucket,
			'email': params.email,
			'token': createToken(params.bucket),
			'enabled': true,
			'activated': false,
			'public': false,
			'created': new Date()
		]
		buckets.add(bucket)

		// TODO: send the email

		flash.message = "Your bucket has been registered! Check your email for instructions on how to activate and start counting things"
		redirect uri:"/"
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

	private getBuckets() {
		mongoService.getCollection(COLLECTION, true)
	}

	private getBucket(name) {
		name ? buckets.find(name: name) : null
	}

	private createToken(bucket) {
		MessageDigest md = MessageDigest.getInstance('SHA')
		md.update("${bucket}${new Date().time}".getBytes('UTF-8'))
		return md.digest().encodeAsSHA1()
	}
}
