# AnvilAPI [![CurseForge](http://cf.way2muchnoise.eu/full_634377_downloads.svg)](https://curseforge.com/minecraft/mc-mods/anvilapi) [![CurseForge](http://cf.way2muchnoise.eu/versions/For%20MC_634377_all.svg)](https://curseforge.com/minecraft/mc-mods/anvilapi)
[![image](https://imgur.com/LHUPcn3.png)](https://curseforge.com/minecraft/mc-mods/anvilapi "You can download this mod here!")\
<span style="color: #ffcc00;">**AnvilAPI**</span> is a mod that adds to the game the ability to create your own dream **anvil recipes**.

## Description

By uploading this mod, you gain the ability to add anvil recipes to the game using datapacks. In addition to the obvious customization of ingredients and results, and their amounts, you can also choose whether the input will be consumed, what it will return, and the amount of XP required.\
Items with durability will be able to consume it instead of the whole item if you choose to!

## Downloads

Download from [CurseForge](https://curseforge.com/minecraft/mc-mods/anvilapi "Then just click download ;)")\
Download from [Modrinth](https://modrinth.com/mod/anvilapi "Then just click download ;)")

## Wiki

Here is an example anvil recipe file:
```json
{
  "type": "minecraft:anvil",
  "inputs": [
    {
      "consume": false,
      "count": 3,
      "tag": "minecraft:coals"
    },
    {
      "count": 5,
      "item": "minecraft:iron_ingot"
    }
  ],
  "experience": 15,
  "result": {
    "count": 1,
    "item": "minecraft:diamond"
  }
}
```
\
**For more information**, see the full documentation [here](https://github.com/Hantonik/AnvilAPI/wiki "Here you will find answers to (almost) all your questions!").

**Yes, you can use this mod in your modpack! That's literally what it was made for!**

## Contribution

**Found a bug?** Report it [here](https://github.com/Hantonik/AnvilAPI/issues/new?labels=bug&amp;template=bug_report.yml "Enter all the necessary information!"), and I'll try to fix it!\
**Have an idea for a new feature?** Tell me more about it [here](https://github.com/Hantonik/AnvilAPI/issues/new?labels=enhancement&amp;template=feature_request.yml "I'm open to suggestions!")!

## Code

[Click me](https://github.com/Hantonik/AnvilAPI "All the magic is here!") for **source code**!

<em><span style="font-family: 'comic sans ms', sans-serif; color: #999999">"If you want to use my code, feel free, but remember to mention whose code it is!</span></em>
<em><p style="padding-left: 5px;"><span style="font-family: 'comic sans ms', sans-serif; color: #999999">Take care, author."</span></p></em>

## Support
<a title="Thanks!" href="https://www.buymeacoffee.com/hantonik"><img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&amp;emoji=&amp;slug=hantonik&amp;button_colour=FFDD00&amp;font_colour=000000&amp;font_family=Cookie&amp;outline_colour=000000&amp;coffee_colour=ffffff" /></a>

**I do it out of passion**, but if you want to appreciate my work, you can do it by [buying me a coffee](https://www.buymeacoffee.com/hantonik "Here!"), **thanks <3**!
