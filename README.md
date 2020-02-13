# Redstone Transformer

Mojang changed how Minecraft handled Block State IDs over the network in 1.13. Along with that, they changed how block
data was stored and rendered. Luckily, they provide a data generator that will give us the valid values for each type of
block, along with the network IDs for each valid state combination. However, due to licensing concerns, this file
cannot be packaged in with Glowstone as a resource. So, I created this project to generate that file at build time, then
transforms annotated, extended Bukkit interfaces into implementations. It will also generate a BlockDataManager class to
handle instantiating the appropriate BlockData implementation for each class.