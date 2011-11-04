<!doctype html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Tallyable</title>
	<script type="text/javascript" charset="utf-8">
		$(function() {
			// validate our bucket name
			$('#bucket').focusout(function() {
				var $this = $(this);
				var $help = $(this).next('.help-inline');
				var $row = $this.parent().parent();
				var bucket = $.trim($this.val());
				if ('' == bucket) {
					$help.text('A bucket name is required');
					$row.removeClass('success').addClass('error');
				} else {
					$.getJSON('${createLink(controller: "bucket")}',
						{ "bucket": bucket },
						function(data) {
							if (data.exists == true) {
								$help.text('Sorry that bucket is already registered');
								$row.removeClass('success').addClass('error');
							} else {
								$help.text('');
								$row.removeClass('error').addClass('success');
							}
						}
					);
				}
			});

			// validate the email address
			$('#email').focusout(function() {
				var $this = $(this);
				var $help = $(this).next('.help-inline');
				var $row = $this.parent().parent();
				var email = $.trim($this.val()).toUpperCase();
				if ('' == email) {
					$help.text('An email address is required');
					$row.removeClass('success warning').addClass('error');
				} else if (!email.match(/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,6}$/)) {
					$help.text('This doesn\'t look quite right');
					$row.removeClass('success error').addClass('warning');
				} else {
					$help.text('');
					$row.removeClass('warning error').addClass('success');
				}
			});
		});
	</script>
</head>
<body>
	<g:if test="${flash.message}">
		<div class="alert-message success" role="status">
			<p>${flash.message}</p>
		</div>
	</g:if>
	<h1>Tallyable <small>A silly little site for counting things.</small></h1>
	<p style="margin-bottom: 20px">
		Get started by creating your own bucket to store all your tallies.	All you need is an email address!
	</p>
	<div>
		<g:form controller="bucket" method="post" useToken="false" style="margin-bottom: 0">
			<g:hiddenField name="check" value="${check}"/>
			<div class="clearfix ${errors?.bucket ? 'error' : ''}">
				<label for="bucket">Bucket</label>
				<div class="input">
					<g:textField name="bucket" value="${bucket}" tabindex="1" class="span6"/>
					<span class="help-inline">${errors?.bucket}</span>
				</div>
			</div>
			<div class="clearfix ${errors?.email ? 'error' : ''}">
				<label for="email">Email</label>
				<div class="input">
					<g:textField name="email" value="${email}" tabindex="2" class="span6"/>
					<span class="help-inline">${errors?.email}</span>
				</div>
			</div>
			<div class="clearfix">
				<div class="input">
					<input type="submit" value="Create!" class="primary btn" tabindex="3"/>
				</div>
			</div>
		</g:form>
	</div>
</body>
</html>
