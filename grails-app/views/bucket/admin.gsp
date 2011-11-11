<!doctype html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Admin :: ${bucket.name}</title>
	<r:require modules="admin"/>
	<script type="text/javascript" charset="utf-8">
		$(function() {
			prettyPrint();
		});
	</script>
	<style type="text/css" media="screen">
		.prettyprint.linenums ol.linenums li {
			list-style: decimal;
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
		<h1>Admin: ${bucket.name}</h1>
	</div>
	<g:if test="${!bucket.enabled}">
		<div class="alert-message error">
			<p>This bucket is disabled.</p>
		</div>
	</g:if>
	<g:elseif test="${!bucket.activated}">
		<div>
			This bucket has not been activated.
			<g:link controller="bucket" action="activate" params="[bucket: bucket.name, secret: bucket.token]" class="btn small">Activate Now</g:link>
		</div>
	</g:elseif>
	<g:else>
		<div class="row">
			<div class="span8" style="display: inline-block">
				<h3>
					Quick Post
				</h3>
				<p>
					<g:form action="post" params="[bucket: bucket.name, secret: bucket.token]">
						<input type="text" name="key" id="key" placeholder="key" class="span3"/> :
						<input type="text" name="value" id="value" placeholder="value" class="span2"/>
						<input type="submit" value="Post!" class="primary btn" style="margin-left: 10px">
					</g:form>
				</p>
			</div>
			<div class="span8" style="display: inline-block">
				<h3>Token</h3>
				<p>
					${bucket.token}
					<g:link controller="bucket" action="token" params="[bucket: bucket.name, secret: bucket.token]" class="btn small" style="margin-left: 20px">New Token</g:link>
				</p>
			</div>
		</div>
		<div style="margin-top: 20px">
			<h3>
				Embedding
				<small>
					&mdash;
					<g:link controller="bucket" action="post" params="[bucket: bucket.name, secret: bucket.token]" absolute="true">Endpoint</g:link>
				</small>
			</h3>
<pre class="prettyprint linenums"><g:textify><g:form params="[bucket: bucket.name, secret: bucket.token]" absolute="true" action="post">
	<input type="hidden" name="key" id="key" value="the-key"/>
<!--
	Or allow a user-specified key:
	<input type="text" name="key" id="key"/>
-->
	<input type="number" name="value" id="value"/>
	<input type="submit" value="Post!">
</g:form></g:textify></pre>
		</div>

		<g:if test="${feed}">
		<div style="margin-top: 30px">
			<h3>
				Recent Posts
				<g:if test="${feed.posts.size() < feed.count}">
					<small>
						&mdash; latest ${feed.posts.size()} of ${feed.count} posts
					</small>
				</g:if>
			</h3>
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
		</div>
		</g:if>
	</g:else>
</body>
</html>
