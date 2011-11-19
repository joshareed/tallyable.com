<!doctype html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Tallyable :: <g:render template="title" model="${feed}"/></title>
</head>
<body>
	<div class="page-header">
		<h1>
			<g:render template="title" model="${feed}"/>
			<small>
				&mdash; <g:jsonLink src="${feed}">JSON</g:jsonLink>
			</small>
		</h1>
	</div>
	<g:render template="table" model="[feed: feed]"/>
</body>
</html>
