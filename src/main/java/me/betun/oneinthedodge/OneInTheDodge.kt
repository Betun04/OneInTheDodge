package me.betun.oneinthedodge

import me.betun.oneinthedodge.commands.Recargar
import me.betun.oneinthedodge.commands.Reset
import me.betun.oneinthedodge.listeners.PlayerListener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class OneInTheDodge : JavaPlugin() {

    companion object{
        lateinit var instance : OneInTheDodge
    }

    init {
        instance = this
    }

    override fun onEnable() {
        // Plugin startup logic
        logger.info("OneInTheDodge cargado correctamente!")

        registerCommands()
        registerListeners()

        saveDefaultConfig()

        val corner1 = config.get("corners.corner1")
        val corner2 = config.get("corners.corner2")

        if (corner1 != null && corner2 != null) {
            logger.info(corner1::class.simpleName)
            logger.info(corner2::class.simpleName)

        }
    }

    private fun registerListeners(){
        server.pluginManager.registerEvents(PlayerListener(),this)
    }

    private fun registerCommands(){
        getCommand("recargar")?.setExecutor(Recargar)
        getCommand("reset")?.setExecutor(Reset)
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("OneInTheDodge desactivado correctamente :(")
    }
}
