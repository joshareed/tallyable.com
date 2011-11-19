var Tallyable;

(function() {
	// constructor
	Tallyable = function(id, manifest, url) {
		var $elm = $('#' + id);
		var $this = this;
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

	// Tallyable prototype
	Tallyable.prototype = {
		constructor: Tallyable,
		_instances: [],
		draw: function(data) {
			$.each(this._instances, function(i, w) {
				var widget = Tallyable.widgets[w.widget];
				if (widget != null) {
					widget.render(w.elm, w.config, {});
				}
			});
		}
	};
}());