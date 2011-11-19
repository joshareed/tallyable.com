package tallyable

class BucketTagLib {

	def jsonLink = { attrs, body ->
		def bucket = attrs?.src?.bucket
		def key = attrs?.src?.key
		def fragment = attrs?.src?.fragment

		out << """<a href="${createLink(controller: 'bucket', action: 'show', params: [bucket: bucket])}/${key ?: ''}${fragment ? ':' + fragment : ''}.json">${body()}</a>"""
	}
}
