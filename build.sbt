sbtPlugin := true

name := "sbt-classfinder"

organization := "net.ruippeixotog"

version := "0.1.1-SNAPSHOT"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "org.clapper" %% "classutil" % "1.0.6"

publishTo <<= version { v =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

licenses := Seq("MIT License" -> url("http://www.opensource.org/licenses/mit-license.php"))

homepage := Some(url("https://github.com/ruippeixotog/sbt-classfinder"))

pomExtra :=
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
