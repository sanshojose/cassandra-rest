name := "cassandra-rest"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)

resolvers += "Kundera missing" at "http://kundera.googlecode.com/svn/maven2/maven-missing-resources"

libraryDependencies += "com.impetus.core" % "kundera-core" % "2.8.1"

libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "2.0.0-rc2"

play.Project.playJavaSettings
