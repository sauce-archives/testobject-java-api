require 'java'

def rel_file path
  File.expand_path File.join('..', '..', path), __FILE__
end

# Create this jar via: mvn clean compile assembly:single
require rel_file 'target/testobject-java-api-0.0.6-SNAPSHOT-jar-with-dependencies.jar'

java_import 'org.testobject.api.TestObjectClient'
java_import 'java.io.FileInputStream'

USER       = 'testobject'
PASSWORD   = 'ThinkPadT420s'
PROJECT    = 'zeppelin-ad-disposition'
TEST_SUITE = 17
client     = TestObjectClient::Factory.create 'https://app.testobject.com/api/rest'

test_file = rel_file 'src/test/resources/org/testobject/api/calculator-debug-test-unaligned.apk'
app_file  = rel_file 'src/test/resources/org/testobject/api/calculator-debug-unaligned.apk'

APP_APK             = FileInputStream.new app_file
INSTRUMENTATION_APK = FileInputStream.new test_file

client.login USER, PASSWORD

client.updateInstrumentationTestSuite USER, PROJECT, TEST_SUITE, APP_APK, INSTRUMENTATION_APK
testSuiteReport = client.startInstrumentationTestSuite USER, PROJECT, TEST_SUITE

client.waitForSuiteReport USER, PROJECT, testSuiteReport
client.close

# based on Java test
# https://github.com/testobject/testobject-java-api/blob/58a90a60b83d07b52f267dc72a58a656bbcb7713/src/test/java/org/testobject/api/TestObjectClientTest.java#L25