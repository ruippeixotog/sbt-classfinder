package net.ruippeixotog.sbt.classfinder

import org.clapper.classutil._
import sbt.Keys._
import sbt._

object ClassFinderPlugin extends AutoPlugin {

  object Imports extends Implicits {

    lazy val classFinderScope = SettingKey[ClassFinderScope]("classFinderScope",
      "Defines what should and should not be searched in the build classpath.")

    lazy val classFinderClasspath = TaskKey[Seq[File]]("classFinderClasspath",
      "The classpath to be searched.")

    lazy val classFinder = TaskKey[ClassFinder]("classFinder",
      "A 'ClassFinder' instance containing several utility methods for listing and finding classes in the specified classpath.")

    lazy val allClassesInfo = TaskKey[Stream[ClassInfo]]("allClassesInfo",
      "A stream with the information of all classes in the specified classpath.")

    lazy val baseClassFinderSettings: Seq[Def.Setting[_]] = Seq(
      classFinderScope := Build,
      classFinderClasspath := classFinderClasspathImpl.value,
      classFinder := classFinderImpl.value,
      allClassesInfo := allClassesInfoImpl.value)

    private[this] lazy val classFinderClasspathImpl = Def.task {
      (classFinderScope.value match {
        case Config => exportedProducts.value
        case Build => internalDependencyClasspath.value ++ exportedProducts.value
        case BuildAndDeps => fullClasspath.value
      }).map(_.data)
    }

    private[this] lazy val classFinderImpl = Def.task { ClassFinder(classFinderClasspath.value) }
    private[this] lazy val allClassesInfoImpl = Def.task { classFinder.value.getClasses() }
  }

  val autoImport = Imports
  import Imports._

  override def requires = sbt.plugins.JvmPlugin
  override def trigger = allRequirements

  override def projectSettings =
    inConfig(Compile)(baseClassFinderSettings) ++
      inConfig(Test)(baseClassFinderSettings)
}
