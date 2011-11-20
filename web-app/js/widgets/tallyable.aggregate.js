Tallyable.register({
	id: 'aggregate',
	name: 'Aggregate',
	description: 'Aggregate values',
	dependencies: [],
	render: function(self, elm, config, data) {
		var settings = $.extend({ label: '', func: 'sum', filter: 'all' }, config);

		// filter
		var filter = self.filters[settings.filter];
		var list = data.posts;
		if (typeof(filter) == 'function') {
			list = filter(list);
		}

		// aggregate
		var func = self.aggregators[settings.func];
		var value = 0;
		if (typeof(func) == 'function') {
			value = func(list);
		}

		// add elements
		$('<span></span>').text(value).addClass('value').appendTo(elm);
		$('<span></span>').html(settings.label).addClass('text').appendTo(elm);
	},
	aggregators: {
		count: function(list) {
			return list.length;
		},
		sum: function(list) {
			var sum = 0;
			for (var i = 0; i < list.length; i++) {
				sum += list[i].value;
			}
			return sum;
		},
		avg: function(list) {
			if (list.length == 0) {
				return 0.0;
			}

			var sum = 0;
			for (var i = 0; i < list.length; i++) {
				sum += list[i].value;
			}
			return sum / list.length;
		},
		min: function(list) {
			var min = null;
			for (var i = 0; i < list.length; i++) {
				if (min == null || list[i].value < min) {
					min = list[i].value;
				}
			}
			return min;
		},
		max: function(list) {
			var max = null;
			for (var i = 0; i < list.length; i++) {
				if (max == null || list[i].value > max) {
					max = list[i].value;
				}
			}
			return max;
		}
	},
	filters: {
		all: function(list) { return list; },
		year: function(list) { return Tallyable.filter(list, 31556926000); },
		month: function(list) { return Tallyable.filter(list, 2592000000); },
		week: function(list) { return Tallyable.filter(list, 604800000); },
		day: function(list) { return Tallyable.filter(list, 86400000); },
		hour: function(list) { return Tallyable.filter(list, 3600000); }
	}
});