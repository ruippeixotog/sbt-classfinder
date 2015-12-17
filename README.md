# sbt-classfinder

sbt-classfinder is a SBT plugin for retrieving runtime information about the classes and traits in a project and searching for specific classes according to some criteria, such as their superclasses or annotations. It allows one to modify the behavior and dependencies of other post-compile SBT tasks according to the compiled code or the classpath.

## Quick Start

To use sbt-classfinder in an existing SBT project using SBT 0.13.5+, add the following dependency to your `project/plugins.sbt`:

```scala
addSbtPlugin("net.ruippeixotog" % "sbt-classfinder" % "0.1.1")
```

## Tasks

* `classFinderClasspath`: determines the classpath to be searched. The scope of the classpath depends on the value of the `classFinderScope` setting;
* `classFinder`: returns a `ClassFinder` instance containing several utility methods for listing and finding classes in the specified classpath;
* `allClassesInfo`: an utility task for retrieving a stream with the information of all classes.

## Settings

* `classFinderScope`: defines what should and should not be searched in the build classpath. One of:
  * `Config`: search the build products of the scoped SBT configuration, e.g. `Compile` classes for `compile:classFinderScope` and `Test` classes for `test:classFinderScope`;
  * `Build`: search the build products of the whole SBT build, including other projects in multi-project builds;
  * `BuildAndDeps`: search the full classpath, including external dependencies.

## Examples

Run the main class marked with the annotation `QuickRun`:

```scala
// build.sbt
lazy val markedMain = TaskKey[String]("markedMain")

lazy val runMarked = TaskKey[Unit]("runMarked")

markedMain := {
  val className = (classFinder in Compile).value.classesAnnotatedWith("QuickRun").head.name
  if (className.endsWith("$")) className.dropRight(1) else className
}

runMarked <<= std.FullInstance.flatten {
  markedMain.map { mMain => (runMain in Compile).toTask(" " + mMain) }
}
```

Make `test` run also subclasses of `MyTest` using a custom test executor:

```scala
// build.sbt
lazy val myTests = TaskKey[Seq[String]]("myTests")

def myTestExecutor(testClass: String) = ???

myTests := (classFinder in Test).value.subtypesOf("MyTest").map(_.name).toSeq

test <<= myTests.map { testSeq => testSeq.map(myTestExecutor) }
```

## Copyright

Copyright (c) 2015 Rui Gonçalves. See LICENSE for details.
