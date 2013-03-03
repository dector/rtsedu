package com.github.dector.rtsedu.lab2

import swing.{TabbedPane, MainFrame, SimpleSwingApplication}
import java.awt.Dimension
import swing.TabbedPane.Page

import UI._

/**
 * @author dector
 */
object App extends SimpleSwingApplication {

	final val xFromTFunc = (t: Float) => {
		4f
	}

	final val yFromTFunc = (t: Float) => {
		math.sin(t).toFloat//20f
	}

	def top = new MainFrame {
		title = FrameTitle
		contents = new TabbedPane {
			pages += new Page(HeaderX, new GraphPanel(xFromTFunc))
			pages += new Page(HeaderY, new GraphPanel(yFromTFunc))
		}
		size = new Dimension(FrameWidth, FrameHeight)
	}
}
