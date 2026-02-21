---
sidebar_position: 1
id: 'intro'
title: 'Just SemVer'
slug: '/'
---
# just-semver

[![Build Status](https://github.com/Kevin-Lee/just-semver/workflows/Build%20All/badge.svg)](https://github.com/Kevin-Lee/just-semver/actions?workflow=Build+All)
[![Release Status](https://github.com/Kevin-Lee/just-semver/workflows/Release/badge.svg)](https://github.com/Kevin-Lee/just-semver/actions?workflow=Release)
[![codecov](https://codecov.io/gh/kevin-lee/just-semver/graph/badge.svg?token=SO5LB2BWOL)](https://codecov.io/gh/kevin-lee/just-semver)

![Maven Central Version](https://img.shields.io/maven-central/v/io.kevinlee/just-semver-core_3)
[![Latest version](https://index.scala-lang.org/kevin-lee/just-semver/just-semver/latest.svg)](https://index.scala-lang.org/kevin-lee/just-semver/just-semver)

[![just-semver Scala version support](https://index.scala-lang.org/kevin-lee/just-semver/just-semver/latest-by-scala-version.svg?platform=jvm)](https://index.scala-lang.org/kevin-lee/just-semver/just-semver)



Semantic Versioning (`SemVer`) for Scala

:::info
Supported Scala Versions: @SUPPORTED_SCALA_VERSIONS@.<br/>
It also supports Scala.js and Scala Native.

[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-1.19.0.svg)](https://www.scala-js.org)
[![just-semver Scala version support](https://index.scala-lang.org/kevin-lee/just-semver/just-semver-core/latest-by-scala-version.svg?platform=native0.5)](https://index.scala-lang.org/kevin-lee/just-semver/just-semver)

Show [**all `just-semver` versions**](https://index.scala-lang.org/kevin-lee/just-semver/artifacts)
:::


## Get just-semver

### `@VERSION@`

#### Core
```scala
"io.kevinlee" %% "just-semver-core" % "@VERSION@"
```

```scala
"io.kevinlee" %%% "just-semver-core" % "@VERSION@"
```


e.g.)
```scala
libraryDependencies += "io.kevinlee" %% "just-semver-core" % "@VERSION@"
```
```scala
libraryDependencies += "io.kevinlee" %%% "just-semver-core" % "@VERSION@"
```


#### DecVer: Decimal version module
```scala
"io.kevinlee" %% "just-semver-decver" % "@VERSION@"
```

```scala
"io.kevinlee" %%% "just-semver-decver" % "@VERSION@"
```


e.g.)
```scala
libraryDependencies += "io.kevinlee" %% "just-semver-decver" % "@VERSION@"
```
```scala
libraryDependencies += "io.kevinlee" %%% "just-semver-decver" % "@VERSION@"
```

#### All modules

```scala
"io.kevinlee" %% "just-semver-core" % "@VERSION@",
"io.kevinlee" %% "just-semver-decver" % "@VERSION@",
```

```scala
"io.kevinlee" %%% "just-semver-core" % "@VERSION@",
"io.kevinlee" %%% "just-semver-decver" % "@VERSION@",
```

***

```scala
libraryDependencies ++= Seq(
  "io.kevinlee" %% "just-semver-core" % "@VERSION@",
  "io.kevinlee" %% "just-semver-decver" % "@VERSION@",
)
```
```scala
libraryDependencies ++= Seq(
  "io.kevinlee" %%% "just-semver-core" % "@VERSION@",
  "io.kevinlee" %%% "just-semver-decver" % "@VERSION@",
)
```
