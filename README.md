# ProjectEnv

![Build](https://github.com/BredoGen/ProjectEnv/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/17044-projectenv.svg)](https://plugins.jetbrains.com/plugin/17044)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/17044.svg)](https://plugins.jetbrains.com/plugin/17044)


<!-- Plugin description -->
ProjectEnv plugin provides settings to configure project-wide .env (dotenv) files. 
Environment variables will be applied to: 
* Terminal (tested on Linux, macOS)
* Python Run Configurations 
* Python Console
* Django Console

Also supported:  
* IDEA Java Run configurations
* GoLand Run configurations
* Terminal in IDEA-based products

## Settings
<kbd>Settings/Preferences</kbd> > <kbd>Build, Execution, Deployment</kbd> > <kbd>ProjectEnv Settings</kbd> > <kbd>Enable plugin</kbd> > <kbd>Select env files for the project</kbd>

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
