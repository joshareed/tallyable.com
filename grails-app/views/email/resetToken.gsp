The security token on your bucket '${bucket.name}' at Tallyable has been reset. The new token is:

	${bucket.token}

Your bucket's Admin console is avaliable at: ${createLink(controller: "bucket", action: "admin", params: [bucket: bucket.name, secret: bucket.token], absolute: true)}

Cheers,
The Tallyable Team