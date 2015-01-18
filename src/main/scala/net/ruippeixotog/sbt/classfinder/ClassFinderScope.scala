package net.ruippeixotog.sbt.classfinder

sealed trait ClassFinderScope
case object Config extends ClassFinderScope
case object Build extends ClassFinderScope
case object BuildAndDeps extends ClassFinderScope
