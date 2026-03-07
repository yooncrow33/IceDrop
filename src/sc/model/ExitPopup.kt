package sc.model;

import sc.view.IExit
import sc.view.IGameModel
import sc.view.IMouse
import java.awt.*

class ExitPopup(var iExit: IExit, var iGameModel: IGameModel) {
    var isVisible: Boolean = false
    var select: Int = 0

    fun setVisible() {
        select = 0
        this.isVisible = true
    }

    fun click() {
        if (isVisible) {
            if (select == 1) {
                iExit.exitApplication()
            } else if (select == 2) {
                this.isVisible = false
            }
        }
    }

    fun update() {
        val pWidth = 500
        val pHeight = 250
        val px = (1920 - pWidth) / 2
        val py = (1080 - pHeight) / 2
        val btnHeight = 50
        val btnY = py + 160

        select = 0
        if (iGameModel.getiMouse().virtualMouseX >= px + 40 && iGameModel.getiMouse().virtualMouseX <= px + 220 && iGameModel.getiMouse().virtualMouseY >= btnY && iGameModel.getiMouse().virtualMouseY <= btnY + btnHeight) {
            select = 1
        } else if (iGameModel.getiMouse().virtualMouseX >= px + 280 && iGameModel.getiMouse().virtualMouseX <= px + 460 && iGameModel.getiMouse().virtualMouseY >= btnY && iGameModel.getiMouse().virtualMouseY <= btnY + btnHeight)  {
            select = 2
        }
    }
    var gray: Color = Color(50, 60, 75)
    var death : Color = Color(20,30,43)
    var deathT : Color = Color(80,83,94)
    var black: Color = Color(20, 25, 35)
    var green: Color = Color(80, 220, 160)
    var blue: Color = Color(0, 200, 255)
    var yellow: Color = Color(255, 200, 90)
    var white: Color = Color(200, 215, 235)


    fun render(g: Graphics) {
        if (!this.isVisible) return
        val g2 = g as Graphics2D
        g2.setStroke(BasicStroke(2f))

        // 1. 백그라운드 딤(Dimming) 효과 - 약간 푸른빛이 도는 어둠
        g.setColor(Color(10, 10, 10, 200))
        g.fillRect(-2000, -2000, 60000, 6000)

        val pWidth = 500
        val pHeight = 250
        val px = (1920 - pWidth) / 2
        val py = (1080 - pHeight) / 2

        g.setColor(black)
        g.fillRect(px, py, pWidth, pHeight)

        g.setColor(white)
        g.fillRect(px, py, pWidth, 5)

        g.setColor(white)
        g.drawRect(px, py, pWidth, pHeight)

        g.setColor(white) // 타이틀: 밝은 아이스 블루
        g.setFont(Font("Impact", Font.BOLD, 32))
        g.drawString("Exit the game", px + 30, py + 50)

        g.setColor(white) // 본문: 아주 연한 푸른 흰색
        g.setFont(Font("Dialog", Font.BOLD, 18))
        g.drawString("Are you sure?", px + 35, py + 100)

        // 4. 버튼 영역
        val btnWidth = 180
        val btnHeight = 50
        val btnY = py + 160

        // [ABORT] 버튼 (Yes 역할)
        if (select == 1) {
            g.setColor(blue) // 활성화 시 진한 하늘색
            g.fillRect(px + 40, btnY, btnWidth, btnHeight)
            g.setColor(Color.WHITE)
        } else {
            g.setColor(death)
            g.drawRect(px + 40, btnY, btnWidth, btnHeight)
        }
        g.setFont(Font("Impact", Font.PLAIN, 24))
        g.drawString("ABORT", px + 97, btnY + 33)

        // [CONTINUE] 버튼 (No 역할)
        if (select == 2) {
            g.setColor(white) // 활성화 시 밝은 아이스 블루
            g.fillRect(px + 280, btnY, btnWidth, btnHeight)
            g.setColor(black) // 텍스트는 어둡게 반전
        } else {
            g.setColor(death)
            g.drawRect(px + 280, btnY, btnWidth, btnHeight)
        }
        g.setFont(Font("Impact", Font.PLAIN, 24))
        g.drawString("CONTINUE", px + 322, btnY + 33)
    }
}