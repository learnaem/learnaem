### Hi there 👋

The below command is used to create this project on AEM 6.5.5

mvn -B archetype:generate -D archetypeGroupId=com.adobe.aem -D archetypeArtifactId=aem-project-archetype -D archetypeVersion=26 -D appTitle="Learn AEM" -D appId="learnaem" -D groupId="com.learnaem" -DincludeExamples=y -DaemVersion=6.5.5 -DincludeDispatcherConfig=n -DfrontendModule=none -DincludeExamples=Y -DincludeErrorHandler=Y -DlanguageCountry="en_us" -DsingleCountry=y
