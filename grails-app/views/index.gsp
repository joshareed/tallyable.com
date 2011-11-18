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
	<style type="text/css" media="screen">
		.lead {
			font-size: 16px;
			line-height: 22px;
			margin-bottom: 30px;
		}
	</style>
</head>
<body>
	<g:if test="${flash.message}">
		<div class="alert-message success" role="status">
			<p>${flash.message}</p>
		</div>
	</g:if>
	<g:if test="${flash.error}">
		<div class="alert-message error" role="status">
			<p>${flash.error}</p>
		</div>
	</g:if>
	<div class="page-header">
		<h1>Tallyable <small>numbers are all around us, it's time to keep a tally</small></h1>
	</div>
	<p class="lead">
		If you can quantify it, we'll help you track and visualize it.	Get started by creating your own bucket to store all your tallies. All you need is an email address!
	</p>
	<div>
		<g:form controller="bucket" method="post" useToken="false" style="margin-bottom: 0">
			<g:hiddenField name="check" value="${check}"/>
			<div class="clearfix ${errors?.bucket ? 'error' : ''}">
				<label for="bucket">Bucket Name</label>
				<div class="input">
					<g:textField name="bucket" value="${bucket}" tabindex="1" class="span6"/>
					<span class="help-inline">
						<g:if test="${errors?.bucket}">
							${errors?.bucket}
						</g:if>
						<g:else>
							letters, numbers, underscores, and dashes
						</g:else>
					</span>
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
