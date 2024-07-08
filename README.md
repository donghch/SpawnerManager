# SpawnerManagerReloaded

![PluginIcon](https://pic.imgdb.cn/item/66744f6cd9c307b7e97cc072.png#pic_center)

SpawnerManager is a SpigotMC plugin empowering spawner management. 
Server owners can limit players' access of replacing mob spawner's mob type and 
charge player money for mob type replacement (Need to enable economy feature). 

## API Version
1.14

## Tested Versions
1.14 to 1.20.6

## Required Plugins
- Economy Feature(Optional): Vault & an economy plugin 

## Installation
Drop the plugin into the /plugins folder and restart the server.

## Commands
- /sm reload Reload plugin config files
- /sm set <mob egg name> <true|false> Set whether player is allowed to change spawner's mob type
to that mob.
- /sm egglist List all applicable mobs

## Permission Nodes
- spawnermanager.use Allow to use the spawner (Allowed by default)
- spawnermanager.reload Allow to use /sm reload command (OP only by default)
- spawnermanager.bypass Allow to bypass spawner rules (Banned by default)
- spawnermanager.set Allow to change spawner rules (OP by default)
