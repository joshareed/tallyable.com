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
			<span style="color: #BFBFBF; margin-left: 10px">(${feed.count})</span>
		</h1>
	</div>
	<g:render template="table" model="[feed: feed]"/>
</body>
</html>
