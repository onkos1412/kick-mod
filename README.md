# Kick! / Пинок

Пинайте мобов и игроков ногой. Мод-шутка для Minecraft Java Edition.
Kick mobs and players with your foot. A just-for-fun Minecraft Java mod.

## Русский

**Как играть:** наведитесь на моба или игрока и нажмите **V**. Нога выстреливает вперёд, раздаётся смешной «бум», а цель теряет одно сердце и улетает. Клавишу можно переназначить в «Управление → Пинок».

- Пинок снимает **одно сердце** (2 HP). Броня, «Защита» и сопротивление уменьшают урон как обычно.
- Цель отлетает намного сильнее, чем от меча, и подлетает вверх. Сопротивление отбрасыванию (например, незеритовая броня) это уменьшает.
- Анимация ноги видна и вам (от первого лица), и всем вокруг.
- Между пинками есть задержка в 1 секунду. Дальность — 3 блока, сквозь стены пинать нельзя.
- Если на сервере выключено PvP (или вы в одной команде без огня по своим), игроков пнуть нельзя, только мобов.
- Если цель погибнет: «Игрок был запинан Пинающий».
- Моды защиты территорий и PvP-переключатели могут запретить пинок так же, как обычный удар (пинок проходит через событие атаки загрузчика).
- Мод нужен **и на сервере, и у игроков**. Сетевой канал мода необязательный, поэтому игроки без мода должны заходить на сервер с модом (просто не смогут пинать), а с модом — на сервер без мода (клавиша ничего не делает). *Это заложено в коде, но вживую ещё не проверялось.* Игроки без мода увидят в сообщении о смерти ключ `death.attack.kick` вместо текста.

**Настройки** — файл `config/kick.json` (создаётся при первом запуске, решают настройки сервера):

| Ключ | По умолчанию | Что делает |
|---|---|---|
| `damage` | 2.0 | урон в HP (2.0 = одно сердце), 0–100 |
| `knockbackMultiplier` | 1.5 | сила отлёта (множитель ванильного отбрасывания), 0–10 |
| `knockbackUp` | 0.4 | насколько цель подлетает вверх, 0–2 |
| `cooldownTicks` | 20 | задержка между пинками в тиках (20 = 1 с), 0–1200 |
| `reach` | 3.0 | дальность в блоках, 1–6 |
| `allowKickPlayers` | true | можно ли пинать игроков вообще (PvP всё равно проверяется) |

Изменения применяются после перезапуска. Ошибка в файле не ломает игру: неверные значения заменяются значениями по умолчанию.

## English

**How to play:** look at a mob or player and press **V**. Your leg shoots out, there is a funny thump, and the target loses one heart and goes flying. Rebind it under Controls → Kick!.

- A kick deals **one heart** (2 HP); armor, Protection and Resistance reduce it as usual.
- Big knockback with a hop; knockback resistance reduces it.
- The leg swing is visible to you (first person) and to everyone around.
- 1 second cooldown, 3 block reach, no kicking through walls.
- With PvP off (or friendly fire off in a team) only mobs can be kicked.
- Death message: "Player was kicked to death by Kicker".
- Land-claim and PvP-toggle mods can block a kick just like a melee hit (kicks go through the loader's attack event).
- Install on **both server and clients**. The mod's network channel is optional, so players without the mod should be able to join a server that has it (they just can't kick), and a modded client a server without it (the key does nothing). *Designed for, not yet tested live.* Players without the mod see the raw key `death.attack.kick` in kick death messages.

**Config** — `config/kick.json`, same keys as the table above (the server's values apply). Restart to apply changes; invalid values fall back to defaults.

## Supported versions / Поддерживаемые версии

One jar per Minecraft line and loader. Quilt: use the Fabric jar. Fabric also needs Fabric API.
Один jar на линию версий и загрузчик. Quilt: берите jar для Fabric. Для Fabric нужен Fabric API.

| Minecraft | Fabric | NeoForge | Forge |
|---|---|---|---|
| 1.20 – 1.20.1 | ✔ | — (use Forge) | ✔ |
| 1.21 – 1.21.1 | ✔ | ✔ | ✔ |
| 1.21.4 | ✔ | ✔ | ✔ |
| 1.21.11 | ✔ | ✔ | ✔ |
| 26.1 – 26.1.2 | ✔ | ✔ | ✔ |
| 26.2 | ✔ | ✔ | ✔ |
| 26.3 | ✔ | ✔ | ✔ |

Every node in this table passed the in-game self-test in the development runtime (key → packet → server, kick damage, knockback, cooldowns, reach, third- and first-person leg); armor reduction was checked on 1.20 Forge, 1.21 Fabric and 26.3 NeoForge. The release jars themselves have not yet been run in a regular launcher.
Not covered yet / пока не поддерживаются: 1.20.2–1.20.6, 1.21.2–1.21.3, 1.21.5–1.21.10.

## Building / Сборка

Requires JDK 21+ (Gradle downloads JDK 17/25 toolchains as needed).

```
./gradlew buildAndCollect
```

Jars are written to `output/<version>/`. To work on a few nodes only: `KICK_NODES=1.21-fabric,26.3-neoforge ./gradlew build`.

The build is based on [Mat0u5's Stonecutter multi-loader template](https://github.com/Mat0u5/MinecraftModTemplate) (MIT, see `LICENSE-TEMPLATE`).

**Developer self-test:** set `KICK_SELFTEST=1` and run a node's `runClient` with a world called `kickst` in `run/saves`: the client presses the kick key itself and logs `[kick-selftest] PASS/FAIL` checks (damage, knockback, animations, cooldowns, reach), then quits. It is inert in normal play.
