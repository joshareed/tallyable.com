Tallyable.register({
	id: 'label',
	name: 'Label',
	description: 'A simple label',
	dependencies: [],
	render: function(elm, config, data) {
		$(elm).text(config.label).css('border', '1px solid black');
	}
});