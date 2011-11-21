<%
	def WIDGETS = ['text', 'aggregate']
%>
<!doctype html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Widgets :: ${bucket.name}<g:if test="${key}"> / ${key}</g:if></title>
	<r:require modules="admin"/>
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
		<h1>
			Widgets: ${bucket.name}<g:if test="${key}"> / ${key}</g:if>
		</h1>
	</div>
	<p>
		Define your widgets below.	<span class="label notice">TODO</span> make this less sucky
	</p>
	<g:form name="widgets" action="updateWidgets" params="[bucket: bucket.name, key: key, secret: bucket.token]">
		<table>
			<thead>
				<tr>
					<th>widget</th>
					<th>config</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${widgets}" var="w" status="i">
				<tr>
					<td>
						<g:select from="${WIDGETS}" name="widget.$i" value="${w.widget}" class="span6" noSelection="['': '']"/>
					</td>
					<td><g:textField name="config.$i" value="${w.config ? w.config.toString()[1..-2] : ''}" class="span16"/></td>
				</tr>
				</g:each>
				<g:each in="${widgets.size()..10}" var="i">
				<tr>
					<td>
						<g:select from="${WIDGETS}" name="widget.$i" class="span6" noSelection="['': '']"/>
					</td>
					<td><g:textField name="config.$i" class="span16"/></td>
				</tr>
				</g:each>
			</tbody>
		</table>
		<input type="submit" value="Update" class="btn primary" style="float: right; margin-right: 45px"/>
	</g:form>
</body>
</html>
