package config

import java.nio.file.{Files, FileSystems, Path, Paths, StandardCopyOption}

class ConfigMap(configFile: Path, path: Path) {
// require(Files.isReadable(configFile), s"The config file cannot be read: $configFile")
// require(Files.isWritable(path),       s"The output path cannot be written to: $path")

  def install(): Unit = {
  	if (Files.exists(path)) {
  	  val newBackup = Resources.getBackupPath(path)
  	  Files.copy(path, newBackup)
  	} else {
  	  Files.createFile(path)
  	}
  	Files.copy(configFile, path, StandardCopyOption.REPLACE_EXISTING)
  }
}

class Resource(name: String, configs: Traversable[ConfigMap]) {
  def install(): Unit = configs.foreach(_.install)
}

object Resources {
  val pathSeparator = FileSystems.getDefault.getSeparator

  def getBackupPath(path: Path): Path = path.getParent.resolve(path.getFileName.toString() + ".backup." + System.currentTimeMillis)

  def getConfigFile(configFile: String): Path = Paths.get("resources/" + configFile)

  def toPath(path: String): Path = {
  	val purged = if (path.startsWith("~" + pathSeparator)) {
      System.getProperty("user.home") + path.substring(1);
	} else path

	Paths.get(purged);
  }
}