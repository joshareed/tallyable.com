Thanks for creating a bucket at Tallyable!

Your bucket '${bucket.name}' is just about ready to use.  All that's left is to activate it using the following link:
${createLink(controller: "bucket", action: "activate", params: [bucket: bucket.name, secret: bucket.token], absolute: true)}

Here are a few other handy links:
 - Your bucket's public URL: ${createLink(controller: "bucket", action: "show", params: [bucket: bucket.name], absolute: true)}
 - Your bucket's Admin console: ${createLink(controller: "bucket", action: "admin", params: [bucket: bucket.name, secret: bucket.token], absolute: true)}

Cheers,
The Tallyable Team