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
				<g:render template="table" model="[feed: feed]"/>
			</div>
		</g:if>
	</g:else>
</body>
</html>
