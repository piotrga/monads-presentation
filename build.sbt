name := "MonadsPresentation"

version := "1.0"

//Add Repository Path
// resolvers += "db4o-repo" at "http://source.db4o.com/maven"
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

// Add a single dependency
libraryDependencies += "me.prettyprint" % "hector-core" % "1.0-1"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0"
