# ProjectEnv

**THIS PLUGIN IS NOT MAINTAINED ANYMORE. I BELEIVE THE ENV FILE SUPPORT MUST BE IN THE IDEA CORE.**

**PLEASE VOTE FOR THE TICKET: https://youtrack.jetbrains.com/issue/PY-5543**

![Build](https://github.com/BredoGen/ProjectEnv/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/17044-projectenv.svg)](https://plugins.jetbrains.com/plugin/17044)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/17044.svg)](https://plugins.jetbrains.com/plugin/17044)


<!-- Plugin description -->
ProjectEnv plugin provides settings to configure project-wide .env (dotenv)/json/yaml files.
Environment variables will be applied to:
* Terminal in all IDEA-based products (tested on Linux, macOS)

Run Configuration support:
* Python Run Configurations, Python / Django Console
* Java Run Configurations (IDEA)
* Go Run Configurations (GoLand)

[GitHub](https://github.com/BredoGen/ProjectEnv)

## Settings
<kbd>Env Files</kbd> tool window > <kbd>Add your .env/json/yaml files</kbd>

For JSON/Yaml files only String:String maps are currently supported.

You can also toggle plugin features: <kbd>Env Files</kbd> tool window > <kbd>⚙️</kbd>:
* Enable in Terminal (requires terminal restart)
* Enable in Run Configurations
* Also Enable in Test Run Configurations (special thanks to [lirikooda](https://github.com/lirikooda))

## Credits
Source code mostly based on [FileEnv](https://github.com/ashald/EnvFile) plugin by Borys Pierov. Special thanks for his great work.
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "ProjectEnv"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/BredoGen/ProjectEnv/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
**WARNING:** I'm not a Java/Kotlin developer. The plugin purpose is to solve my own inconvenience while working with 12factor apps in PyCharm.

Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
