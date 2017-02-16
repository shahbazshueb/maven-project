
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/shahbaz/projects2/maven-project/conf/routes
// @DATE:Wed Feb 15 21:54:39 PKT 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
