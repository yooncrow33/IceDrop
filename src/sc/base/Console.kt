package sc.base;

import sc.view.IGameModel
import sc.view.IPause
import java.awt.*
import java.util.*
import kotlin.math.max

class Console(var iGameModel: IGameModel) {
    var isOpen: Boolean = false
        private set
    private val buffer = StringBuilder()
    private val logs = ArrayList<String>()

    private val MAX_LINES = 10

    //val help = Help();

    fun toggle() {
        isOpen = !isOpen
    }

    fun inputKey(key: Char, code: Int) {
        if (!isOpen) return

        if (code == 10) { // Enter Key Code
            execute()
        } else if (code == 8) { // Backspace
            if (buffer.length > 0) {
                buffer.deleteCharAt(buffer.length - 1)
            }
        } else {
            if (key.code >= 32 && key.code <= 126) {
                buffer.append(key)
            }
        }
    }

    private fun execute() {
        val input = buffer.toString().trim()
        if (input.isEmpty()) return

        logs.add("[${String.format("%tT", System.currentTimeMillis())}] root: $input")

        val args = input.split(" ")
        val cmd = args[0].lowercase()

        when (cmd) {
            "clear" -> logs.clear()
            "close" -> isOpen = false;
            "save" -> iGameModel.fileManager.save(iGameModel.currentProfileId)
            "load" -> iGameModel.fileManager.load(iGameModel.currentProfileId)
            "exit" -> System.exit(0)
            "set" -> {
                if (args.size == 4) handleSet(args)
                else logs.add("[Console] Usage: set [type] [name] [value]")
            }
            else -> { logs.add("[Console] Error: Unknown command: $cmd"); logs.add("[Console] Try 'help' for more information") }
        }
        buffer.setLength(0)
    }

    private fun handleSet(args: List<String>) {
        val (_, type, target, valueStr) = args
        val value = valueStr.toIntOrNull() ?: 0

        if (type == "val") {
            if (target == "coin") {
                //val isValid = valueStr.toIntOrNull() in 0..200
                val isValid = true
                if (isValid) {
                    iGameModel.shopManager.loadCoin(valueStr.toIntOrNull()!!)
                    logs.add("[System] $target set to $value")
                } else {
                    logs.add("[Console] Error: Value $value is out of range for $target")
                }
            } else if (target == "xp") {
                val isValid = true
                if (isValid) {
                    iGameModel.skillManager.loadXp(valueStr.toIntOrNull()!!)
                    logs.add("[System] $target set to $value")
                } else {
                    logs.add("[Console] Error: Value $value is out of range for $target")
                }
            }
        } else if (type == "bool") {
            val valueBool = valueStr.toBooleanStrictOrNull()
            if (valueBool == null) { logs.add("[Console] Error: Not boolean value"); return}

            when (target) {
                "pause" -> { iGameModel.getiPause().setPause(valueBool); logs.add("[System] $target set to $valueBool") }
            }
        }
    }

    fun render(g: Graphics?) {
        if (!isOpen) return
        val g2 = g as Graphics2D

        // 1. 배경 (반투명 블랙)
        g2.setColor(Color(10, 10, 10, 240)) // 거의 불투명하게
        g2.fillRect(0, 0, 1920, 340) // 화면 절반 덮기

        // 2. 하단 구분선 (포인트 컬러: Scope Red)
        g2.setColor(Color(240, 240, 240))
        g2.setStroke(BasicStroke(3f))
        g2.drawLine(0, 340, 1920, 340)

        // -----------------------------------------------------
        // [Left Zone] 명령어 로그 창
        // -----------------------------------------------------
        g2.setFont(Font("Consolas", Font.PLAIN, 16)) // Consolas나 Monospaced 권장
        val lineHeight = 25
        val startY = 40

        // 최근 N개의 로그만 보여주기 (스크롤 효과)
        val startIndex = max(0, logs.size - MAX_LINES)
        var lineCount = 0

        for (i in startIndex..<logs.size) {
            val line = logs.get(i)

            // 컬러 코딩 로직 (간단하게)
            if (line.contains("Error")) g2.setColor(Color(180, 180, 180)) // Red
            else if (line.contains("root:")) g2.setColor(Color.WHITE) // Me
            else if (line.contains("[System]")) g2.setColor(Color.GREEN) // System
            else g2.setColor(Color.LIGHT_GRAY) // Others


            g2.drawString(line, 30, startY + (lineCount * lineHeight))
            lineCount++
        }
        g2.setColor(Color(240, 240, 240))
        g2.setFont(Font("Consolas", Font.BOLD, 18))
        val cursor = if (System.currentTimeMillis() % 1000 > 300) "_" else ""
        g2.drawString("root@sc:~$ " + buffer.toString() + cursor, 30, 320)

        g2.setColor(Color.DARK_GRAY)
        g2.drawLine(1500, 20, 1500, 320)

        g2.setFont(Font("Impact", Font.PLAIN, 24))
        g2.setColor(Color.WHITE)
        g2.drawString("SC GAME STATUS", 1520, 60)

        g2.setFont(Font("Consolas", Font.BOLD, 14))
        g2.setColor(Color.GRAY)

        g2.setColor(Color(40, 40, 40))
        g2.setFont(Font("Impact", Font.ITALIC, 40))
        g2.drawString("IceDrop", 1760, 300)
    }
}