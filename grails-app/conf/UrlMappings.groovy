class UrlMappings {

	static mappings = {
		"/"(view:"/index")
		"500"(view:'/error')

		"/bucket"(controller: 'bucket') {
			action = [GET: 'check', POST: 'create']
		}
		"/$bucket/$key?"(controller: 'bucket', action: 'show')
		"/$bucket/$secret/$action/$key?"(controller: 'bucket')
	}
}
