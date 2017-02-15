import ReleaseTransformations._
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

releaseUseGlobalVersion := false
releaseVersionBump := sbtrelease.Version.Bump.Next
releaseProcess := Seq[ReleaseStep](
  //checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  //runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)
