package com.github.dector.rtsedu.lab2

import swing._
import java.awt.Dimension
import swing.TabbedPane.Page
import swing.event.Key

import UI._

/**
 * @author dector
 */
object App extends SimpleSwingApplication {

	final val rndTFunc = (t: Float) => {
		val rnd = new util.Random

		Array.fill(12)(
			rnd.nextFloat * 10 * math.sin(rnd.nextFloat * 2 * math.Pi * t + rnd.nextFloat * 900)
		).sum.toFloat
	}

	final val xFromTFunc = (t: Float) => {
		4f
	}

	final val yFromTFunc = (t: Float) => {
		math.sin(t).toFloat//20f
	}

	def top = new MainFrame {
		title = FrameTitle
		val tabs = new TabbedPane {
			val xGraphPanel = new GraphPanel(rndTFunc)
			val yGraphPanel = new GraphPanel(rndTFunc)

			pages += new Page(HeaderX, xGraphPanel)
			pages += new Page(HeaderY, yGraphPanel)
			pages += new Page(HeaderRxx, new CorGraphPanel(xGraphPanel, xGraphPanel))
			pages += new Page(HeaderRxy, new CorGraphPanel(xGraphPanel, yGraphPanel))
		}

		contents = tabs
		size = new Dimension(FrameWidth, FrameHeight)

		menuBar = new MenuBar {
			contents += new Menu(UI.Actions) {
				contents += new MenuItem(Action(UI.Update) {
					val page = tabs.selection.page.content.asInstanceOf[GraphPanel]

					page.init()
					page.repaint()
				})

				contents += new MenuItem(Action(UI.ScaleUp) {
					val page = tabs.selection.page.content.asInstanceOf[GraphPanel]

					page.yValuesBounds = (page.yValuesBounds._1 / 2, page.yValuesBounds._2 / 2)
					page.repaint()
				})

				contents += new MenuItem(Action(UI.ScaleDown) {
					val page = tabs.selection.page.content.asInstanceOf[GraphPanel]

					page.yValuesBounds = (page.yValuesBounds._1 * 2, page.yValuesBounds._2 * 2)
					page.repaint()
				})
			}
		}
	}
}
