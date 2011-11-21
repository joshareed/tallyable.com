var Tallyable;

(function() {
	// constructor
	Tallyable = function(selector, manifest, url) {
		var $this = this;
		var $elm = $(selector);
		$.each(manifest, function(i, widget) {
			var $w = $('<div></div>')
				.attr('id', 'widget' + i)
				.addClass('widget');
			if (widget.config.width != null) {
				var w = widget.config.width;
				if (typeof(w) == 'number') { w = w + 'px'; }
				$w.css('width', w);
			}
			if (widget.config.height != null) {
				var h = widget.config.height;
				if (typeof(h) == 'number') { h = h + 'px'; }
				$w.css('height', h);
			}
			$w.appendTo($elm);
			widget.elm = $w[0];
			$this._instances.push(widget);
		});

		// fetch our data
		$.getJSON(url, function(data) {
			$this.draw(data);
		});
		return this;
	};

	// Widget namespace for external widgets
	Tallyable.widgets = {};
	Tallyable.register = function(widget) {
		var id = widget.id;
		Tallyable.widgets[id] = widget;
	}
	Tallyable.filter = function(list, dur) {
		var filtered = [];
		var since = new Date().getTime() - dur;
		for (var i = 0; i < list.length; i++) {
			var d = new Date(list[i].timestamp);
			if (d.getTime() >= since) {
				filtered.push(list[i]);
			}
		}
		return filtered;
	}

	// Tallyable prototype
	Tallyable.prototype = {
		constructor: Tallyable,
		_instances: [],
		draw: function(data) {
			$.each(this._instances, function(i, w) {
				var widget = Tallyable.widgets[w.widget];
				if (widget != null) {
					$(w.elm).addClass(widget.id);
					widget.render(widget, w.elm, w.config, data);
				}
			});
		}
	};
}());