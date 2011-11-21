modules = {
	common {
		resource url: 'css/bootstrap.min.css', disposition: 'head'
		resource url: 'css/main.css', disposition: 'head'
	}
	admin {
		resource url: 'css/prettify.css', disposition: 'head'
		resource url: 'js/prettify.js'
	}
	dashboard {
		resource url: 'css/widget.css', disposition: 'head'
		resource url: 'js/tallyable.js'
		resource url: 'js/widgets/tallyable.text.js'
		resource url: 'js/widgets/tallyable.aggregate.js'
	}
}