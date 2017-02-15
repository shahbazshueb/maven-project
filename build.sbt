import ReleaseTransformations._
import sbtrelease.Version
import sbtrelease.versionFormatError
name := "mvn-project"

version <<= version in ThisBuild
organization := "org.tenpearls"

lazy val `mvn-project` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc ,  cache , javaWs )
libraryDependencies ++= Seq( "com.github.github"  % "site-maven-plugin" % "0.12")
publishMavenStyle := true
publishTo := Some(Resolver.file("file",  new File("mvn-repo")))

resolvers += "maven-repo" at "https://repo.eclipse.org/content/groups/releases/"
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

val isFinal = Option(System.getProperty("final")).getOrElse(false)

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


releaseUseGlobalVersion := false
releaseVersionBump := sbtrelease.Version.Bump.Next
releaseProcess := Seq[ReleaseStep](
  //checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  //runTest,                                // : ReleaseStep
  //setReleaseVersion,                      // : ReleaseStep
  //commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
//  tagRelease,                             // : ReleaseStep
  publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)
