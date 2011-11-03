<!doctype html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Countable</title>
</head>
<body>
	<h1>A silly little site for counting things.</h1>
	<p>
		Get started counting by creating your own bucket.  All you need is an email address!
	</p>
	<div>
		<g:form controller="bucket" method="post" useToken="true">
			<g:hiddenField name="check" value="${check}"/>
			<div class="clearfix">
				<label for="bucket">Bucket</label>
				<div class="input">
					<g:textField name="bucket" value="${bucket}" tabindex="1"/>
					<span class="help-inline">${errors?.bucket}</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="email">Email</label>
				<div class="input">
					<g:textField name="email" value="${email}" tabindex="2"/>
					<span class="help-inline">${errors?.email}</span>
				</div>
			</div class="clearfix">
			<div class="clearfix">
				<div class="input">
					<input type="submit" value="Create!" class="primary btn" tabindex="3"/>
				</div>
			</div>
		</g:form>
	</div>
</body>
</html>
