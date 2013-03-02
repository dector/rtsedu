package com.github.dector.rtsedu.lab2

import swing.{TabbedPane, MainFrame, SimpleSwingApplication}
import java.awt.Dimension

/**
 * @author dector
 */
object App extends SimpleSwingApplication {

	final val FrameTitle = "RTSEdu.Lab2"
	final val FrameWidth = 800
	final val FrameHeight = 600

	def top = new MainFrame {
		title = FrameTitle
		contents = new TabbedPane {

		}
		size = new Dimension(FrameWidth, FrameHeight)
	}
}
