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
		assert tagLib.jsonLink(src: [bucket: 'test', key: 'key']).toString().contains('test/key.json')
		assert tagLib.jsonLink(src: [bucket: 'test', key: 'key', fragment: 'frag']).toString().contains('test/key:frag.json')
		assert tagLib.jsonLink(src: [bucket: 'test', fragment: 'frag']).toString().contains('test/:frag.json')
	}
}
