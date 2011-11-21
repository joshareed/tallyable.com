<!doctype html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Admin :: ${bucket.name}</title>
	<r:require modules="admin"/>
	<script type="text/javascript" charset="utf-8">
		$(function() {
			prettyPrint();
			$('.js-show-help').click(function() {
				var $help = $('.js-posting-help');
				if ($help.is(':visible')) {
					$('.js-posting-help').slideUp();
				} else {
					$('.js-posting-help').slideDown();
				}
			});
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
					Post
					<small>
						&mdash; <a href="#" class="js-show-help">other posting options &raquo;</a>
					</small>
				</h3>
				<p>
					<g:form action="post" params="[bucket: bucket.name, secret: bucket.token]">
						<input type="text" name="key" id="key" placeholder="key" class="span3"/> =
						<input type="text" name="value" id="value" placeholder="value" class="span2"/>
						<input type="submit" value="Tally It!" class="primary btn" style="margin-left: 10px">
					</g:form>
				</p>
			</div>
			<div class="span8" style="display: inline-block">
				<h3>Token</h3>
				<p>
					${bucket.token}
					<g:link controller="bucket" action="token" params="[bucket: bucket.name, secret: bucket.token]" class="btn small danger" style="margin-left: 20px">New Token</g:link>
				</p>
			</div>
		</div>
		<div class="js-posting-help" style="display: ${feed.count == 0 ? 'block' : 'none'}">
			<h3>
				Posting
			</h3>
			<p>From the command line:</p>
<pre class="prettyprint">
curl -d "key=example&value=2" ${createLink(controller: "bucket", action: "post", params: [bucket: bucket.name, secret: bucket.token], absolute: true)}
</pre>

			<p>Embedded in a web page:</p>
<pre class="prettyprint linenums"><g:textify><g:form params="[bucket: bucket.name, secret: bucket.token]" absolute="true" action="post">
	<input type="hidden" name="key" id="key" value="the-key"/>
<!--
	Or allow a user-specified key:
	<input type="text" name="key" id="key"/>
-->
	<input type="number" name="value" id="value"/>
	<input type="submit" value="Post!"/>
</g:form></g:textify></pre>
		</div>

		<div>
			<h3>Keys</h3>
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<g:link controller="bucket" action="show" params="[bucket: bucket.name]">${bucket.name}</g:link>
						</td>
						<td style="text-align: right">
							<g:link action="widgets" params="[bucket: bucket.name, secret: bucket.token]" class="btn small">Manage widgets &raquo;</g:link>
						</td>
					</tr>
					<g:each in="${feed.keys}" var="key">
						<tr>
							<td>
								<li><g:link controller="bucket" action="show" params="[bucket: bucket.name, key:key]">${key}</g:link></li>
							</td>
							<td style="text-align: right">
								<g:link action="widgets" params="[bucket: bucket.name, secret: bucket.token]" class="btn small">Manage widgets &raquo;</g:link>
							</td>
						</tr>
					</g:each>
				</tbody>
			</table>
		</div>
	</g:else>
</body>
</html>
