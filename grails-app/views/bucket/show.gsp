<!doctype html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Tallyable :: <g:render template="title" model="${feed}"/></title>
	<r:require modules="dashboard"/>
	<script type="text/javascript" charset="utf-8">
		$(function() {
			var tallyable = new Tallyable(
				'dashboard',
				[
					{
						widget: 'label',
						config: {
							label: 'Count',
							aggregation: 'count'
						}
					}
				],
				'${createLink(controller: "bucket", action: "show", params: [bucket: feed.bucket])}.json'
			);
			tallyable.draw();
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
	<div id="dashboard"></div>
	<g:render template="table" model="[feed: feed]"/>
</body>
</html>
