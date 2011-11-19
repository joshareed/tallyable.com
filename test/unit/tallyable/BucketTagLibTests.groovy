package tallyable

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(BucketTagLib)
class BucketTagLibTests {

	void testJsonLink() {
		assert tagLib.jsonLink(src: [bucket: 'test']).toString().contains('test/.json')
		assert tagLib.jsonLink(src: [bucket: 'test', key: 'key', fragment: 'frag']).toString().contains('test/key:frag.json')

		def link = tagLib.jsonLink(src: [bucket: 'test', key: 'key'], { -> 'JSON' }).toString()
		assert link.contains('test/key.json')
		assert link.contains('JSON')

		link = tagLib.jsonLink(src: [bucket: 'test', fragment: 'frag'], { -> 'JSON' }).toString()
		assert link.contains('test/:frag.json')
		assert link.contains('JSON')
	}
}
