Tallyable.register({
	id: 'label',
	name: 'Label',
	description: 'Displays a simple static label',
	dependencies: [],
	render: function(self, elm, config, data) {
		var settings = $.extend({ label: '' }, config);
		$('<span></span>').html(settings.label).addClass('text').appendTo($elm);
	}
});