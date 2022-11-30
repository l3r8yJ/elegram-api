# elegram-api
It's a wrapper over the telegram bots api, but in the style of EO.

## Example

This is a concept, if you have ideas, feel free to create an issue!

```
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
