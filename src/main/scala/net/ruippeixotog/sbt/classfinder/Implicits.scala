package net.ruippeixotog.sbt.classfinder

import org.clapper.classutil.{AnnotationInfo, ClassFinder}

trait Implicits {
  implicit class ClassFinderUtils(val finder: ClassFinder) {

    def subtypesOf(className: String) =
      ClassFinder.concreteSubclasses(className, finder.getClasses().toIterator)

    def classesAnnotatedWith(className: String) = {
      val encodedClassName = "L" + className.replaceAll("""\.""", "/") + ";"
      finder.getClasses().filter(_.annotations.find(_.descriptor == encodedClassName).nonEmpty)
    }
  }

  implicit class AnnotationInfoUtils(val annInfo: AnnotationInfo) {
    def className = annInfo.descriptor.drop(1).dropRight(1).replaceAll("/", ".")
  }
}

object Implicits extends Implicits
