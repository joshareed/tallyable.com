grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.source.level = 1.6
grails.project.target.level = 1.6
grails.project.war.file = "target/${appName}##${appVersion}.war"

grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits("global") {
		// uncomment to disable ehcache
		excludes 'ehcache'
	}
	log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	checksums true // Whether to verify checksums on resolve

	repositories {
		inherits true // Whether to inherit repository definitions from plugins
		grailsPlugins()
		grailsHome()
		grailsCentral()
		mavenCentral()
	}

	dependencies {
		compile 'org.mongodb:mongo-java-driver:2.7.0-rc1'
	}

	plugins {
		compile ":hibernate:$grailsVersion"
		compile ":resources:1.1.1"
		compile ":quartz:1.0-RC2"
		compile ":mail:1.0"
		build ":tomcat:$grailsVersion"
		test ":code-coverage:1.2.5"
		runtime ":jquery:1.7.1"
	}
}
