# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.3/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.3/gradle-plugin/packaging-oci-image.html)
* [CycloneDX SBOM support](https://docs.spring.io/spring-boot/reference/actuator/endpoints.html#actuator.endpoints.sbom)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.3/reference/htmlsingle/index.html#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Scan Software Bill of Materials for Vulnerabilities with osv-scanner

First install the osv-scanner using brew.

```bash
brew install osv-scanner
```

Then perform a gradle build of the project. Ensure to clean if any dependencies change on the project. The cyclonedx 
plugin does not trigger rerunning with the dependencies of the project change. More investigation needed. 

```bash
./gradlew clean build
```

A new report should be included in the build directory containing the cyclonedx SBOM.

```bash
cat build/reports/application.cdx.json
```

To scan the `application.cdx.json` for vulnerabilities execute.

```bash
osv-scanner scan --sbom build/reports/application.cdx.json
```

The `osv-scanner` will produce output similar to the following:

```text
╭─────────────────────────────────────┬──────┬───────────┬───────────────────────────────────────────┬──────────┬────────────────────────────────────╮
│ OSV URL                             │ CVSS │ ECOSYSTEM │ PACKAGE                                   │ VERSION  │ SOURCE                             │
├─────────────────────────────────────┼──────┼───────────┼───────────────────────────────────────────┼──────────┼────────────────────────────────────┤
│ https://osv.dev/GHSA-5mg8-w23w-74h3 │ 3.3  │ Maven     │ com.google.guava:guava                    │ 30.1-jre │ build/reports/application.cdx.json │
│ https://osv.dev/GHSA-7g45-4rm6-3mm3 │ 5.5  │ Maven     │ com.google.guava:guava                    │ 30.1-jre │ build/reports/application.cdx.json │
│ https://osv.dev/GHSA-wm9w-rjj3-j356 │ 8.7  │ Maven     │ org.apache.tomcat.embed:tomcat-embed-core │ 10.1.24  │ build/reports/application.cdx.json │
│ https://osv.dev/GHSA-chfm-68vv-pvw5 │      │ Maven     │ org.xmlunit:xmlunit-core                  │ 2.9.1    │ build/reports/application.cdx.json │
│ https://osv.dev/GHSA-2cww-fgmg-4jqc │ 8.1  │ Maven     │ org.keycloak:keycloak-services            │ 24.0.4   │ build/reports/application.cdx.json │
│ https://osv.dev/GHSA-69fp-7c8p-crjr │ 7.5  │ Maven     │ org.keycloak:keycloak-services            │ 24.0.4   │ build/reports/application.cdx.json │
╰─────────────────────────────────────┴──────┴───────────┴───────────────────────────────────────────┴──────────┴────────────────────────────────────╯
```

This is an example of a project that has dependencies declared that have vulnerabilities. The developer has the ability
to discover these vulnerabilities by scanning the [application.cdx.json](build/reports/application.cdx.json) report 
generated by the gradle build.