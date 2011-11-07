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
	<table>
		<thead>
			<tr>
				<th>Date</th>
				<th>Key</th>
				<th>Value</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${feed.posts}" var="post">
				<tr>
					<td>${post.timestamp}</td>
					<td>
						<g:link controller="bucket" action="show" params="[bucket: feed.bucket, key: post.key + (post.fragment ? ':' + post.fragment : '')]">
							${post.key}
							<g:if test="${post.fragment}">: ${post.fragment}</g:if>
						</g:link>
					</td>
					<td><g:formatNumber number="${post.value}"/></td>
				</tr>
			</g:each>
			<g:if test="${!feed.posts}">
				<tr>
					<td colspan="3" style="text-align: center">
						<em>no tallies for this key</em>
					</td>
				</tr>
			</g:if>
		</tbody>
	</table>
</body>
</html>
