name := "mvn-project"

version := "1.9"
organization := "org.tenpearls"

lazy val `mvn-project` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc ,  cache , javaWs )
libraryDependencies ++= Seq( "com.github.github"  % "site-maven-plugin" % "0.12")
publishMavenStyle := true
publishTo := Some(Resolver.file("file",  new File("mvn-repo")))

resolvers += "maven-repo" at "https://repo.eclipse.org/content/groups/releases/"
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

pomExtra := (
  <url>https://github.com/shahbazshueb/maven-project</url>
    <licenses>
      <license>
        <name>GNU Affero General Public License Version 3</name>
        <distribution>repo</distribution>
        <url>http://www.gnu.org/licenses/agpl-3.0.html</url>
      </license>
    </licenses>
    <scm>
      <url>https://github.com/shahbazshueb/maven-project</url>
      <connection>scm:git:git://github.com/shahbazshueb/maven-project.git</connection>
      <developerConnection>scm:git:git@github.com:shahbazshueb/maven-project.git</developerConnection>
    </scm>
    <developers>
      <developer>
        <id>shahbazshueb</id>
        <name>Shahbaz Ali Shueb</name>
      </developer>
    </developers>

    <build>
      <plugins>
        <plugin>
          <groupId>com.github.github</groupId>
          <artifactId>site-maven-plugin</artifactId>
          <version>0.11</version>
          <configuration>
            <message>Maven artifacts for ${version}</message>  <!-- git commit message -->
            <noJekyll>true</noJekyll>                                  <!-- disable webpage processing -->
            <outputDirectory>mvn-repo</outputDirectory> <!-- matches distribution management repository url above -->
            <branch>refs/heads/mvn-repo</branch>                       <!-- remote branch name -->
            <includes><include>**/*</include></includes>
            <repositoryName>maven-project</repositoryName>      <!-- github repo name -->
            <repositoryOwner>shahbazshueb</repositoryOwner>    <!-- github username  -->
          </configuration>
          <executions>
            <!-- run site-maven-plugin's 'site' target as part of the build's normal 'deploy' phase -->
            <execution>
              <goals>
                <goal>site</goal>
              </goals>
              <phase>deploy</phase>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </build>
  )
