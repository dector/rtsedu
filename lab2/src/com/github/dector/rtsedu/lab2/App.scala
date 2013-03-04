package com.github.dector.rtsedu.lab2

import swing.{TabbedPane, MainFrame, SimpleSwingApplication}
import java.awt.Dimension
import swing.TabbedPane.Page

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
		contents = new TabbedPane {
			pages += new Page(HeaderX, new GraphPanel(rndTFunc, 256, (0, 256), (-50, 50), (20, 10)))
//			pages += new Page(HeaderX, new GraphPanel(xFromTFunc))
//			pages += new Page(HeaderY, new GraphPanel(yFromTFunc))
		}
		size = new Dimension(FrameWidth, FrameHeight)
	}
}
