package me.betun.oneinthedodge.listeners

import me.betun.oneinthedodge.OneInTheDodge
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.damage.DamageType
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.scheduler.BukkitRunnable



class PlayerListener : Listener {

    val scoreboard = Bukkit.getScoreboardManager().mainScoreboard;
    var corner1Origin = OneInTheDodge.instance.config.get("corners.corner1").toString()
    var corner2Origin = OneInTheDodge.instance.config.get("corners.corner2").toString()

    @EventHandler
    fun onEntityDamage(e: EntityDamageByEntityEvent){

        var actualShrink: Int = OneInTheDodge.instance.config.getInt("shrink.start")
        val teamAttacker = scoreboard.getEntityTeam(e.damageSource.causingEntity as Player)
        val teamVictim = scoreboard.getEntityTeam(e.entity)
        if(e.damageSource.damageType == DamageType.ARROW && e.damageSource.causingEntity is Player && teamAttacker != teamVictim){

            val tps = OneInTheDodge.instance.config.getList("tps")

            if (tps != null) {
                val posStr = tps.random().toString()
                val posList = posStr.split(",")

                e.entity.teleport(Location(e.entity.world,posList[0].toDouble(),posList[1].toDouble(),posList[2].toDouble()))
                if(actualShrink < OneInTheDodge.instance.config.getInt("shrink.limit")) {
                    removeSides(e.entity.world, actualShrink)
                    actualShrink += 1
                    OneInTheDodge.instance.config.set("shrink.start", actualShrink)
                }
            }
        }
        e.damager.remove()
        e.isCancelled = true
        summonArrow(e.entity.world, e.damageSource.causingEntity as Player)
    }

    private fun removeSides(world: World,shrinkValue: Int){
        val actualShrink = shrinkValue

        if(corner1Origin != "" && corner2Origin != ""){
            var corner1 = corner1Origin.split(",")
            var corner2 = corner2Origin.split(",")

            val shrink = OneInTheDodge.instance.config.get("shrink.limit")
            var newCorner1 = ""
            var newCorner2 = ""

            if(corner1[0]>corner2[0]){
                if(corner1[0].toInt() > 0){
                    newCorner1 = "${corner1[0].toInt()-actualShrink},${corner1[1]}"
                }else{
                    newCorner1 = "${corner1[0].toInt()+actualShrink},${corner1[1]}"
                }

                if(corner2[0].toInt() > 0) {
                    newCorner2 = "${corner2[0].toInt() + actualShrink},${corner2[1]}"
                }else{
                    newCorner2 = "${corner2[0].toInt() - actualShrink},${corner2[1]}"
                }

            }else{
                if(corner1[0].toInt() > 0){
                    newCorner1 = "${corner1[0].toInt()+actualShrink},${corner1[1]}"
                }else{
                    newCorner1 = "${corner1[0].toInt()-actualShrink},${corner1[1]}"
                }

                if(corner2[0].toInt() > 0){
                    newCorner2 = "${corner2[0].toInt()-actualShrink},${corner2[1]}"
                }else{
                    newCorner2 = "${corner2[0].toInt()+actualShrink},${corner2[1]}"
                }
            }


            if(corner1[2]>corner2[2]){
                if(corner1[2].toInt()>0){
                    newCorner1 += ",${corner1[2].toInt() - actualShrink}"
                }else{
                    newCorner1 += ",${corner1[2].toInt() + actualShrink}"
                }

                if(corner2[2].toInt()>0){
                    newCorner2 += ",${corner2[2].toInt() + actualShrink}"
                }else{
                    newCorner2 += ",${corner2[2].toInt() - actualShrink}"
                }


            }else{
                if(corner1[2].toInt() > 0){
                    newCorner1 += ",${corner1[2].toInt() + actualShrink}"
                }else{
                    newCorner1 += ",${corner1[2].toInt() - actualShrink}"
                }

                if(corner2[2].toInt() > 0){
                    newCorner2 += ",${corner2[2].toInt() - actualShrink}"
                }else{
                    newCorner2 += ",${corner2[2].toInt() + actualShrink}"
                }


            }

            corner1 = newCorner1.split(",")
            corner2 = newCorner2.split(",")

            val corner3 = listOf(corner1[0],corner1[1],corner2[2])
            val corner4 = listOf(corner2[0],corner1[1],corner1[2])

            //Primer hilera X
            if(corner1[0].toDouble() > corner4[0].toDouble()){
                for (i in corner4[0].toInt()..corner1[0].toInt()){
                    world.getBlockAt(Location(world,i.toDouble(),corner4[1].toDouble(),corner4[2].toDouble())).type = Material.AIR
                }
            }else{
                for (i in corner1[0].toInt()..corner4[0].toInt()){
                    world.getBlockAt(Location(world,i.toDouble(),corner4[1].toDouble(),corner4[2].toDouble())).type = Material.AIR
                }
            }
            //Segunda hilera X
            if(corner3[0].toDouble() > corner2[0].toDouble()){
                for (i in corner2[0].toInt()..corner3[0].toInt()){
                    world.getBlockAt(Location(world,i.toDouble(),corner2[1].toDouble(),corner2[2].toDouble())).type = Material.AIR
                }
            }else{
                for (i in corner3[0].toInt()..corner2[0].toInt()){
                    world.getBlockAt(Location(world,i.toDouble(),corner2[1].toDouble(),corner2[2].toDouble())).type = Material.AIR
                }
            }
            //Primera hilera Z
            if(corner1[2].toDouble() > corner3[2].toDouble()){
                for (i in corner3[2].toInt()..corner1[2].toInt()){
                    world.getBlockAt(Location(world,corner1[0].toDouble(),corner1[1].toDouble(),i.toDouble())).type = Material.AIR
                }
            }else{
                for (i in corner1[2].toInt()..corner3[2].toInt()){
                    world.getBlockAt(Location(world,corner1[0].toDouble(),corner1[1].toDouble(),i.toDouble())).type = Material.AIR
                }
            }
            //Segunda hilera Z
            if(corner2[2].toDouble() > corner4[2].toDouble()){
                for (i in corner4[2].toInt()..corner2[2].toInt()){
                    world.getBlockAt(Location(world,corner4[0].toDouble(),corner4[1].toDouble(),i.toDouble())).type = Material.AIR
                }
            }else{
                for (i in corner2[2].toInt()..corner4[2].toInt()){
                    world.getBlockAt(Location(world,corner4[0].toDouble(),corner4[1].toDouble(),i.toDouble())).type = Material.AIR
                }
            }
        }

}

    private fun summonArrow(world: World,player: Player){
        val team = scoreboard.getEntityTeam(player)
        val nameTeam1 = OneInTheDodge.instance.config.getString("teams.1")
        val posTeam1 = OneInTheDodge.instance.config.getString("arrow-spawns.team1")?.split(",")
        val nameTeam2 = OneInTheDodge.instance.config.getString("teams.2")
        val posTeam2 = OneInTheDodge.instance.config.getString("arrow-spawns.team2")?.split(",")

        if(nameTeam1 == team?.name){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon arrow ${posTeam1?.get(0)?.toInt()} ${posTeam1?.get(1)?.toInt()} ${posTeam1?.get(2)?.toInt()} {pickup:1}");
        }else if(nameTeam2 == team?.name){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon arrow ${posTeam2?.get(0)?.toInt()} ${posTeam2?.get(1)?.toInt()} ${posTeam2?.get(2)?.toInt()} {pickup:1}")
        }
    }

    @EventHandler
    fun onArrowShoot(e: ProjectileLaunchEvent){
        if(e.entity.type == EntityType.ARROW){
            e.entity.isGlowing = true
        }
    }

}