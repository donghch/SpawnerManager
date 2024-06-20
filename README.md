# SpawnerManagerReloaded

![PluginIcon](https://pic.imgdb.cn/item/66744f6cd9c307b7e97cc072.png#pic_center)

SpawnerManagerReloaded is a SpigotMC plugin empowering spawner management. 
Server owners can limit players' access of replacing mob spawner's mob type and 
charge player for replacement.

## API Version
- 1.20

## Tested Versions
- 1.20
- 1.20.4
- 1.20.6

## Required Plugins
- Vault or VaultUnlocked(Not tested)
- Any permission plugin & economy plugin

## Installation
Drop the plugin into the /plugins folder and restart the server.

## Commands
- /sm reload Reload plugin config files
- /sm set <Mob Name> <true|false> Set whether player is allowed to change spawner's mob type
to that mob.
- /sm moblist List all applicable mobs

## Permission Nodes
- spawnermanager.use Allow to use the spawner (Allowed by default)
- spawnermanager.reload Allow to use /sm reload command (OP only by default)
- spawnermanager.bypass Allow to bypass spawner rules (Banned by default)
- spawnermanager.set Allow to change spawner rules (OP by default)
