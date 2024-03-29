<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>	   <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>	   <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>	   <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title><g:layoutTitle default="Grails"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
	<r:require modules="common, jquery"/>
	<r:layoutResources />
	<g:layoutHead/>
</head>
<body>
	<div class="container">
		<div class="content">
			<g:layoutBody/>
		</div>
	</div>
	<div class="container">
		<div class="footer">
			<g:render template="/git"/> &mdash; <g:meta name="app.version"/>
		</div>
	</div>
	<r:layoutResources />
</body>
</html>