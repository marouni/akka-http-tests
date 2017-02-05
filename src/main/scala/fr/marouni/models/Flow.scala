package fr.marouni.models

case class Flow(
               id : String,
               displayName: String,
               stages: Seq[Stage]
               )
