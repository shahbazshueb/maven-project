import ReleaseTransformations._
import sbtrelease.Version
import sbtrelease.versionFormatError

val isFinal = Option(System.getProperty("final")).getOrElse(false)
name := "mvn-project"

organization := "org.tenpearls"

lazy val `mvn-project` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc ,  cache , javaWs )
libraryDependencies ++= Seq( "com.github.github"  % "site-maven-plugin" % "0.12")
publishMavenStyle := true
publishTo := Some(Resolver.file("file",  new File("release")))

resolvers += "maven-repo" at "https://repo.eclipse.org/content/groups/releases/"
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

releaseVersion := { ver => if(isFinal == "") {
  Version(ver).map(_.withoutQualifier.string).getOrElse(versionFormatError)
} else {
  Version(ver).map(_.asSnapshot.string).getOrElse(versionFormatError)
} }

releaseNextVersion := { ver => if(isFinal == "") {
  Version(ver).map(_.bump.string).getOrElse(versionFormatError)
} else {
  Version(ver).map(_.withoutQualifier.string).getOrElse(versionFormatError)
} }

releaseProcess := {
  if(isFinal == "") {
    Seq[ReleaseStep](
      inquireVersions,
      setReleaseVersion,                      // : ReleaseStep
      commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
      tagRelease,                             // : ReleaseStep
      publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
      setNextVersion,                         // : ReleaseStep
      commitNextVersion,                      // : ReleaseStep
      pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
    )
  } else {
    Seq[ReleaseStep](
      inquireVersions,
      setReleaseVersion,                      // : ReleaseStep
      commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
      publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
      setNextVersion,                         // : ReleaseStep
      commitNextVersion,                      // : ReleaseStep
      pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
    )
  }
}
