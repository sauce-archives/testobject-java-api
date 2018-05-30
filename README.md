# TestObject Java Api [![Build Status](https://travis-ci.org/saucelabs/testobject-java-api.svg?branch=master)](https://travis-ci.org/saucelabs/testobject-java-api)


## Installation

TestObject Java API is available from
[Maven Central](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.testobject%22%20AND%20a%3A%22testobject-java-api%22).

    <dependency>
      <groupId>org.testobject</groupId>
      <artifactId>testobject-java-api</artifactId>
      <version>select a version</version>
    </dependency>

## How to deploy

On every PR / merge to master Travis runs `mvn clean deploy`, which results in
binary on Sonatype (this is configured in `pom.xml`).

Steps:

- Go to [Sonatype OSS](https://oss.sonatype.org). Click on "Staging repositories" 
or use direct link: https://oss.sonatype.org/#stagingRepositories.
- Find your latest version and press "Close" on it, type nothing, press 
"Confirm". If you wonder this is a weird procedure which we have to follow to
make the whole thing succeed. 
- It takes a few minutes for Close action to succeed. Press refresh button,
if everything went fine you should now see "Release" button enabled.
- Press "Release", type nothing, press "Confirm".
- It takes around 6 hours for the package to appear on MvnRepository.

### Known issue: the newly deployed version does not appear

The issue manifests itself in a weird way: the new version is there physically 
(without a date on a folder though, like the normal versions have):
http://central.maven.org/maven2/org/testobject/testobject-java-api/ but it is 
not available. Examples of the versions that were affected by this: `0.1.6`,
`0.1.9`.

The solution is to bump a version again and deploy again.
