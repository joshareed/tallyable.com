<!doctype html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Admin :: ${bucket.name}</title>
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
		<div class="span4 well" style="float: right">
			<p>Posts: ${stats.count}</p>
			<p>
				<g:link controller="bucket" action="show" params="[bucket: bucket.name]">${bucket.name}</g:link>
				<g:if test="${stats.keys}">
					<ul>
					<g:each in="${stats.keys}" var="key">
						<li><g:link controller="bucket" action="show" params="[bucket: bucket.name, key: key]">${key}</g:link></li>
					</g:each>
					</ul>
				</g:if>
			</p>
		</div>
		<div>
			<h3>Token</h3>
			<p>
				${bucket.token}
				<g:link controller="bucket" action="token" params="[bucket: bucket.name, secret: bucket.token]" class="btn small" style="margin-left: 20px">New Token</g:link>
			</p>
		</div>
		<div>
			<h3>
				Posting
				<small>
					&mdash;
					<g:link controller="bucket" action="activate" params="[bucket: bucket.name, secret: bucket.token]">Endpoint</g:link>
				</small>
			</h3>
			<p>
				<g:form action="post" params="[bucket: bucket.name, secret: bucket.token]">
					<input type="text" name="key" id="key" placeholder="key"/> :
					<input type="text" name="value" id="value" placeholder="value" class="span2"/>
					<input type="submit" value="Post!" placeholder="value" class="primary btn" style="margin-left: 10px">
				</g:form>
			</p>
		</div>
	</g:else>
</body>
</html>
