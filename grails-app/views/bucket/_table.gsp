<g:if test="${feed}">
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
						<g:link controller="bucket" action="show" params="[bucket: feed.bucket, key: post.key]">
							${post.key}
						</g:link>
						<g:if test="${post.fragment}">
							: <g:link controller="bucket" action="show" params="[bucket: feed.bucket, key: post.key + (post.fragment ? ':' + post.fragment : '')]">
								${post.fragment}
							</g:link>
						</g:if>
					</td>
					<td><g:formatNumber number="${post.value}"/></td>
				</tr>
			</g:each>
			<g:if test="${!feed.posts}">
				<tr>
					<td colspan="3" style="text-align: center">
						<em>no tallies</em>
					</td>
				</tr>
			</g:if>
		</tbody>
	</table>
</g:if>