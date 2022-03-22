# VanillaFree

VanillaFree is a datapack that reduces the memory footprint of unused vanilla resources.

## Background

Since [22w11a](https://www.minecraft.net/article/minecraft-snapshot-22w11a), resources can be filtered out by pack
filters. However, pack filters only control the visibility of resources; they are still allocated in memory. VanillaFree
reduces the total memory footprint of datapacks by overwriting all the vanilla resources with resources that have a
smaller memory footprint.
