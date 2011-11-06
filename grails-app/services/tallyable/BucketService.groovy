package tallyable

import java.security.MessageDigest

class BucketService {
	private static final String BUCKETS = "buckets"
	private static final String POSTS = "posts"
	private static final RESERVED = ['bucket', 'index', 'dev', 'test', 'testing', 'tallyable']

	def mongoService

	def get(String name) {
		name ? buckets.find(name: name.toLowerCase()) : null
	}

	def validate(String name) {
		name && !(name.toLowerCase() in RESERVED) && (name.toLowerCase() ==~ /^[a-z0-9_\.-]+$/)
	}

	def activate(String name) {
		def bucket = get(name)
		if (bucket && !bucket.activated) {
			bucket.activated = true
			buckets.update(['_id': bucket._id], bucket)
		}
	}

	def create(String name, String email) {
		def bucket
		if (validate(name)) {
			// create our bucket
			def buckets = getBuckets()
			bucket = buckets.add(
				'name': name.toLowerCase(),
				'email': email,
				'token': createToken(name),
				'enabled': true,
				'activated': false,
				'public': true,
				'created': new Date()
			)

			// TODO: send the email
		}
		bucket
	}

	def post(String name, String key, String fragment, def value) {
		def post
		def bucket = get(name)
		if (bucket && validate(key) && (!fragment || validate(fragment))) {
			post = posts.add(
				'bucket': name.toLowerCase(),
				'key': key.toLowerCase(),
				'fragment': (fragment ? fragment.toLowerCase() : null),
				'value': value,
				'timestamp': new Date()
			)
		}
		post
	}

	def findPosts(String name, String key = null, String fragment = null) {
		def q = [bucket: name]
		if (key) { q.key = key }
		if (fragment) { q.fragment = fragment }
		posts.findAll(q).sort(timestamp: 1)
	}

	private getBuckets() {
		mongoService.getCollection(BUCKETS, true)
	}

	private getPosts() {
		mongoService.getCollection(POSTS, true)
	}

	private createToken(name) {
		MessageDigest md = MessageDigest.getInstance('SHA')
		md.update("${name}${new Date().time}".getBytes('UTF-8'))
		return md.digest().encodeAsSHA1()
	}
}