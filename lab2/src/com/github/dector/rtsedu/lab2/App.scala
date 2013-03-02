package com.github.dector.rtsedu.lab2

import swing.{TabbedPane, MainFrame, SimpleSwingApplication}
import java.awt.Dimension
import swing.TabbedPane.Page

import UI._

/**
 * @author dector
 */
object App extends SimpleSwingApplication {

	final val xFromTFunc = () => {
		4
	}

	final val yFromTFunc = () => {
		5
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
