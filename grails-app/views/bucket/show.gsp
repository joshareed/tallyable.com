<!doctype html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Tallyable :: <g:render template="title" model="${feed}"/></title>
	<r:require modules="dashboard"/>
	<script type="text/javascript" charset="utf-8">
		$(function() {
			new Tallyable('#viz', ${widgets}, '<g:jsonLink src="${feed}"/>');
		});
	</script>
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
	<div id="viz"></div>
	<g:render template="table" model="[feed: feed]"/>
</body>
</html>
