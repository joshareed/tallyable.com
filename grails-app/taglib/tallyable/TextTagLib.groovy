package tallyable

class TextTagLib {

	def textify = { attrs, body ->
		def txt = body()
		txt?.eachLine { line ->
			if (line) {
				out << line.encodeAsHTML() << "\n"
			}
		}
	}
}
