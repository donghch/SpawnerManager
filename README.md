# SpawnerManager
---
## 插件API版本
- 1.20.6

## 插件测试过的版本
- 1.20.4

## 前置插件
- 任意一款权限插件(本插件经过测试和GroupManager兼容)

## 插件安装
将插件放入plugins文件夹并启动服务器

## 插件指令
- /sm reload 重新加载插件配置
- /sm set <生物名字> <true | false> 设置是否允许将刷怪笼刷怪
- /sm moblist 列出所有适用的生物名

## 插件权限
- spawnermanager.use 允许玩家对刷怪笼使用生物蛋（根据插件规则)，玩家默认拥有
- spawnermanager.reload 允许使用/sm reload指令, 默认OP拥有
- spawnermanager.bypass 允许无视插件限制使用刷怪笼，默认所有玩家都不拥有
- spawnermanager.set 允许修改插件规则， 默认OP拥有

## 插件配置文件
插件相关生物名字由AI进行翻译，如有错误之处请指出。
```
#插件配置文件版本
config-version: 1
#插件语言
lang: "zh-cn"

# 允许的生物
allowed-mobs:
  # 悦灵
  allay: true
  # 蝾螈
  axolotl: true
  # 蝙蝠
  bat: true
  # 蜜蜂
  bee: true
  # 烈焰人
  blaze: true
  # 猫
  cat: true
  # 骆驼
  camel: true
  # 洞穴蜘蛛
  cave-spider: true
  # 鸡
  chicken: true
  # 鳕鱼
  cod: true
  # 牛
  cow: true
  # 爬行者
  creeper: true
  # 海豚
  dolphin: true
  # 驴
  donkey: true
  # 溺尸
  drowned: true
  # 远古守卫者
  elder-guardian: true
  # 末影龙
  ender-dragon: true
  # 末影人
  enderman: true
  # 末影螨
  endermite: true
  # 唤魔者
  evoker: true
  # 狐狸
  fox: true
  # 蛙
  frog: true
  # 恶魂
  ghast: true
  # 荧光鱿鱼
  glow-squid: true
  # 山羊
  goat: true
  # 守卫者
  guardian: true
  # 疣猪兽
  hoglins: true
  # 马
  horse: true
  # 干草怪
  husk: true
  # 铁傀儡
  iron-golem: true
  # 羊驼
  llama: true
  # 岩浆怪
  magma-cube: true
  # 哞菇
  mooshroom: true
  # 骡
  mule: true
  # 豹猫
  ocelot: true
  # 熊猫
  panda: true
  # 鹦鹉
  parrot: true
  # 幻翼
  phantom: true
  # 猪
  pig: true
  # 猪灵
  piglin: true
  # 猪灵蛮兵
  piglin-brute: true
  # 掠夺者
  pillager: true
  # 北极熊
  polar-bear: true
  # 河豚
  pufferfish: true
  # 兔子
  rabbit: true
  # 劫掠兽
  ravager: true
  # 鲑鱼
  salmon: true
  # 绵羊
  sheep: true
  # 潜影贝
  shulker: true
  # 银鱼
  silverfish: true
  # 骷髅
  skeleton: true
  # 骷髅马
  skeleton-horse: true
  # 史莱姆
  slime: true
  # 嗅探兽
  sniffer: true
  # 雪傀儡
  snow-golem: true
  # 蜘蛛
  spider: true
  # 鱿鱼
  squid: true
  # 流浪者
  stray: true
  # 炽足兽
  strider: true
  # 蝌蚪
  tadpole: true
  # 交易羊驼
  trader-llama: true
  # 热带鱼
  tropical-fish: true
  # 海龟
  turtle: true
  # 恼鬼
  vex: true
  # 村民
  villager: true
  # 卫道士
  vindicator: true
  # 流浪商人
  wandering-trader: true
  # 监守者
  warden: true
  # 女巫
  witch: true
  # 凋零
  wither: true
  # 凋零骷髅
  wither-skeleton: true
  # 狼
  wolf: true
  # 僵尸疣猪兽
  zoglin: true
  # 僵尸
  zombie: true
  # 僵尸马
  zombie-horse: true
  # 僵尸村民
  zombie-villager: true
  # 僵尸猪灵
  zombified-piglin: true
```