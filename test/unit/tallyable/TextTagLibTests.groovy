package tallyable

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(TextTagLib)
class TextTagLibTests {

	void testTextify() {
		assert '&lt;html&gt;\n' == tagLib.textify() { '<html>' }.toString()
		assert 'Foo\n' == tagLib.textify() { 'Foo' }.toString()
	}
}
