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
				&mdash; <a href="${createLink(controller: 'bucket', action: 'show', params: [bucket: feed.bucket])}.json">JSON</a>
			</small>
		</h1>
	</div>
	<g:render template="table" model="[feed: feed]"/>
</body>
</html>
