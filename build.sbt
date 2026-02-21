import extras.scala.io.syntax.color.*

ThisBuild / scalaVersion := props.ProjectScalaVersion
ThisBuild / organization := "io.kevinlee"
ThisBuild / crossScalaVersions := props.CrossScalaVersions

ThisBuild / developers := List(
  Developer(
    props.GitHubUsername,
    "Kevin Lee",
    "kevin.code@kevinlee.io",
    url(s"https://github.com/${props.GitHubUsername}"),
  )
)
ThisBuild / licenses := props.licenses

lazy val docs = (project in file("docs-gen-tmp/docs"))
  .enablePlugins(MdocPlugin, DocusaurPlugin)
  .settings(
    scalaVersion := props.ProjectScalaVersion,
    name := props.RepoName,
    mdocIn := file("docs"),
    mdocOut := file("generated-docs/docs"),
    cleanFiles += file("generated-docs/docs"),
    scalacOptions ~= (ops => ops.filterNot(x => x == "-Wnonunit-statement")),
    libraryDependencies ++= {
      implicit val logger: Logger = sLog.value

      val latestVersion = DocsTools.getTheLatestTaggedVersion(props.GitHubUsername, props.CodeRepoName)(logger.error(_))

      List(
        "io.kevinlee" %%% "just-semver-core"   % latestVersion,
        "io.kevinlee" %%% "just-semver-decver" % latestVersion,
      )
    },
    docusaurDir := (ThisBuild / baseDirectory).value / "website",
    docusaurBuildDir := docusaurDir.value / "build",
    mdocVariables := {
      implicit val logger: Logger = sLog.value

      val latestVersion = DocsTools.getTheLatestTaggedVersion(props.GitHubUsername, props.CodeRepoName)(logger.error(_))
      DocsTools.createMdocVariables(latestVersion, props.CrossScalaVersions, props.CrossScalaVersions)
    },
    mdoc := {
      implicit val logger: Logger = sLog.value

      val latestVersion = DocsTools.getTheLatestTaggedVersion(props.GitHubUsername, props.CodeRepoName)(logger.error(_))

      val envVarCi = sys.env.get("CI")
      val ciResult = s"""sys.env.get("CI")=${envVarCi}"""
      envVarCi match {
        case Some("true") =>
          logger.info(
            s">> ${ciResult.yellow} so ${"run".green} `${"writeLatestVersion".blue}` and `${"writeVersionsArchived".blue}`."
          )
          val websiteDir = docusaurDir.value
          DocsTools.writeLatestVersion(websiteDir, latestVersion)
          DocsTools.writeVersionsArchived(props.GitHubUsername, props.CodeRepoName)(websiteDir, latestVersion)(logger)
        case Some(_) | None =>
          logger.info(
            s">> ${ciResult.yellow} so it will ${"not run".red} `${"writeLatestVersion".cyan}` and `${"writeVersionsArchived".cyan}`.\n" +
              s">> If you want to write these files locally, run sbt with ${"CI=true".yellow}.\n" +
              s">> e.g.) ${"CI=true".blue} ${"sbt".blue}"
          )
      }
      mdoc.evaluated
    },
  )
  .settings(noPublish)

lazy val props =
  new {

    private val GitHubRepo = findRepoOrgAndName

    val Org = "io.kevinlee"

    val GitHubUsername = GitHubRepo.fold("kevin-lee")(_.orgToString)
    val RepoName       = GitHubRepo.fold("just-semver-docs")(_.nameToString)

    val CodeRepoName = RepoName.stripSuffix("-docs")

    val licenses = List("MIT" -> url("http://opensource.org/licenses/MIT"))

    val removeDottyIncompatible: ModuleID => Boolean =
      m =>
//        m.name == "wartremover" ||
        m.name == "ammonite" ||
          m.name == "kind-projector" ||
          m.name == "better-monadic-for" ||
          m.name == "mdoc"

    val isWartRemover: ModuleID => Boolean =
      m => m.name == "wartremover"

//    val ProjectScalaVersion: String      = "2.12.18"
//    val ProjectScalaVersion: String      = "3.1.3"
//    val ProjectScalaVersion: String      = "3.3.1"
    val ProjectScalaVersion: String      = "2.13.16"
    val CrossScalaVersions: List[String] =
      (
        if (isGhaPublishing) {
          // Publish version and the project version are the same so this logic is no longer required.
          //          (_: List[String]).diff(List(ProjectScalaVersion))
          identity[List[String]] _
        } else {
          identity[List[String]] _
        }
      ) (
        List(
          "2.12.18",
          "2.13.16",
          "3.3.5",
          ProjectScalaVersion
        ).distinct
      )

    val IncludeTest = "compile->compile;test->test"

    val HedgehogVersion = "0.13.0"

    val ScalaCollectionCompatVersion = "2.13.0"

  }

lazy val libs =
  new {

    lazy val tests = new {

      lazy val hedgehogLibs = Def.setting {
        val scalaV          = scalaVersion.value
        val hedgehogVersion = props.HedgehogVersion

        List(
          "qa.hedgehog" %%% "hedgehog-core"   % hedgehogVersion % Test,
          "qa.hedgehog" %%% "hedgehog-runner" % hedgehogVersion % Test,
          "qa.hedgehog" %%% "hedgehog-sbt"    % hedgehogVersion % Test
        )
      }

      lazy val scalaCollectionCompat =
        Def.setting("org.scala-lang.modules" %%% "scala-collection-compat" % props.ScalaCollectionCompatVersion % Test)
    }
  }

def isGhaPublishing: Boolean = sys.env.get("GHA_IS_PUBLISHING").fold(false)(_.toBoolean)

def isScala3(scalaVersion: String): Boolean = scalaVersion.startsWith("3.")

// scalafmt: off
def prefixedProjectName(name: String) = s"${props.RepoName}${if (name.isEmpty) "" else s"-$name"}"
// scalafmt: on
