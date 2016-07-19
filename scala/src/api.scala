package config

import java.nio.file.Paths

import scala.language.implicitConversions

object api {

  class ResourceBuilder(val name: String) extends AnyVal {
    def := (configs: Traversable[ConfigMap]) = new Resource(name, configs)
    def := (config: ConfigMap) = new Resource(name, Seq(config))
  }

  object ResourceBuilder {
  	def apply(name: String) = new ResourceBuilder(name)
  }

  implicit class ConfigMapBuilder(val configFile: String) extends AnyVal {
    def =>>(path: String) = new ConfigMap(Resources.getConfigFile(configFile), Resources.toPath(path))
  }
	
  // implicit def stringToConfigMapBuilder(str: String): ConfigMapBuilder = new ConfigMapBuilder(str)
}