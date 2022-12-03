<img alt="logo" src="https://www.objectionary.com/cactus.svg" height="100px" />

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/objectionary/eo)](http://www.rultor.com/p/objectionary/eo)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![PDD status](https://www.0pdd.com/svg?name=l3r8yJ/elegram-api)](https://www.0pdd.com/p?name=l3r8yJ/elegram-api)
[![codecov](https://codecov.io/gh/l3r8yJ/elegram-api/branch/master/graph/badge.svg?token=JTWR1LR9QX)](https://codecov.io/gh/l3r8yJ/elegram-api)

__Elegram-api__ it's a wrapper over the telegram bots api, but in the style of EO. More object-oriented, more maintainable.

## Concept

This is a concept, if you have ideas, feel free to create an issue!

```java
new Bot(
  "your token",
  new TxtCommand(
    "start",
    new RpPicture(
      "image.png",
      new RpText("Hi body!")
    )
  ),
  new TxtCommand(
    "help",
    new RpText("I'll help you!")
  )
).start();
```
## How to Contribute

Fork repository, make changes, send us a [pull request](https://www.yegor256.com/2014/04/15/github-guidelines.html).
We will review your changes and apply them to the `master` branch shortly,
provided they don't violate our quality standards. To avoid frustration,
before sending us your pull request please run full Maven build:

```bash
$ mvn clean install
```

You will need [Maven 3.3+](https://maven.apache.org) and Java 8+ installed.


## Reasons to reject your code

- [Empty lines.](https://www.yegor256.com/2014/11/03/empty-line-code-smell.html)

- [Compound names.](https://www.yegor256.com/2015/01/12/compound-name-is-code-smell.html)

- [Incorrect formatting.](https://www.yegor256.com/2014/10/23/paired-brackets-notation.html)

- [Mutability.](https://www.yegor256.com/2014/06/09/objects-should-be-immutable.html)

- [Getters/Setters.](https://www.yegor256.com/2014/09/16/getters-and-setters-are-evil.html)

- [Pull request with feature but without a unit-tests](https://www.yegor256.com/2022/08/04/code-and-tests-different-pull-requests.html)

This little list is to make you understand that **we aren't picking on you**, it's just a **level of quality below which we can't accept**. 

_Looking forward to your pull-request!_

