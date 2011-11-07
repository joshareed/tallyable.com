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
		<div>
			<h3>Token</h3>
			<p>
				${bucket.token}
				<g:link controller="bucket" action="token" params="[bucket: bucket.name, secret: bucket.token]" class="btn small" style="margin-left: 20px">New Token</g:link>
			</p>
		</div>
	</g:else>
</body>
</html>
