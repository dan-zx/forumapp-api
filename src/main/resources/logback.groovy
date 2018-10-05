appender('console', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = '%highlight(%-5level) %cyan(%logger).%boldCyan(%method\\(\\)) %red(-) %msg%n'
    }
}

logger('javax.management', WARN)
logger('org.apache.catalina', WARN)
logger('org.apache.coyote', WARN)
logger('org.apache.tomcat', WARN)
logger('org.hibernate.validator', WARN)
logger('org.jboss.logging', WARN)
logger('org.springframework', WARN)
logger("org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping", INFO)
logger("springfox.documentation", WARN)
logger('sun.rmi', WARN)

root(ALL, ['console'])
