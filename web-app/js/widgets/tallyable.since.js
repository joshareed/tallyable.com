Tallyable.register({
	id: 'since',
	name: 'Time Since',
	description: 'Displays the time since the last event',
	dependencies: [],
	render: function(self, elm, config, data) {
		var settings = $.extend({ label: '' }, config);
		var units = 'hours';
		var value = 'N/A';

		if (data.posts.length > 0) {
			var since = (new Date().getTime()) - (new Date(data.posts[0].timestamp).getTime());
			if (since < self.consts.DAY) {
				units = 'hours';
				value = (since / self.consts.HOUR).toFixed(2);
			} else if (since < self.consts.WEEK) {
				units = 'days';
				value = (since / self.consts.DAY).toFixed(2);
			} else if (since < self.consts.MONTH) {
				units = 'weeks';
				value = (since / self.consts.WEEK).toFixed(2);
			} else if (since < self.consts.YEAR) {
				units = 'months';
				value = (since / self.consts.MONTH).toFixed(2);
			} else {
				units = 'years';
				value = (since / self.consts.YEAR).toFixed(2);
			}
		}

		$('<span></span>').html(value).addClass('value').appendTo(elm);
		$('<span></span>').html(units + ' since ' + settings.label).addClass('text').appendTo(elm);
	},
	consts: {
		HOUR: 1000 * 60 * 60,
		DAY: 1000 * 60 * 60 * 24,
		WEEK: 1000 * 60 * 60 * 24 * 7,
		MONTH: 1000 * 60 * 60 * 24 * 30,
		YEAR: 1000 * 60 * 60 * 24 * 365
	}
});