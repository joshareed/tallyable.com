var Tallyable;

(function() {

	Tallyable = function(id, manifest, url) {
		var $elm = $('#' + id);
		$.each(manifest, function(widget, i) {
			var $w = $('<div></div>')
				.attr('id', 'widget' + i)
				.addClass('widget')
				.css('display', 'inline-block');
			if (widget.config.width != null) {
				$w.css('width', widget.config.width + 'px');
			}
			if (widget.config.height != null) {
				$w.css('width', widget.config.height + 'px');
			}
			w.appendTo($elm);
		});

		return this;
	};

	// Widget namespace for external widgets
	Tallyable.widgets = {};

	// Tallyable prototype
	Tallyable.prototype = {
		constructor: Tallyable,
		draw: function() {
			console.log()
		}
	};
}());