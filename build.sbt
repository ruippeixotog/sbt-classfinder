name := "sbt-classfinder"
organization := "net.ruippeixotog"
version := "0.1.2-SNAPSHOT"

homepage := Some(url("https://github.com/ruippeixotog/sbt-classfinder"))
licenses := Seq("MIT License" -> url("http://www.opensource.org/licenses/mit-license.php"))

sbtPlugin := true
crossSbtVersions := Vector("0.13.16", "1.0.0")

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "org.clapper" %% "classutil" % "1.1.2"

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ => false }

pomExtra := {
  <scm>
    <url>https://github.com/ruippeixotog/sbt-classfinder</url>
    <connection>scm:git:https://github.com/ruippeixotog/sbt-classfinder.git</connection>
  </scm>
  <developers>
    <developer>
      <id>ruippeixotog</id>
      <name>Rui Gonçalves</name>
      <url>http://ruippeixotog.net</url>
    </developer>
  </developers>
}
