Tallyable.register({
	id: 'text',
	name: 'Text Label',
	description: 'Displays a simple static text label',
	dependencies: [],
	render: function(self, elm, config, data) {
		var settings = $.extend({ label: '' }, config);
		$('<span></span>').html(settings.label).addClass('text').appendTo(elm);
	}
});