package com.github.dector.rtsedu.lab2

import swing.Panel
import java.awt.{Point, Rectangle, Graphics2D}

/**
 * @author dector
 */
class GraphPanel(val countFunction: () => Int) extends Panel {

	final val PaddingLeft = 10
	final val PaddingRight = 10
	final val PaddingTop = 10
	final val PaddingBottom = 10

	final val MinX = 0
	final val MaxX = 100
	final val MinY = -50
	final val MaxY = 50

	val padding = (PaddingTop, PaddingRight, PaddingBottom, PaddingLeft)

	val xValuesBounds = (MinX, MaxX)
	val yValuesBounds = (MinY, MaxY)

	override def paintComponent(g: Graphics2D) {
		// Draw axis
		val xAxisLength = size.width - padding._2 - padding._4
		val yAxisLength = size.height - padding._1 - padding._3
		val zeroPosition = new Point(
			-xValuesBounds._1 * xAxisLength / (xValuesBounds._2 - xValuesBounds._1),
			-yValuesBounds._1 * yAxisLength / (yValuesBounds._2 - yValuesBounds._1))
		drawLine(g, 0, zeroPosition.y, xAxisLength, zeroPosition.y)
		drawLine(g, zeroPosition.x, 0, zeroPosition.x, yAxisLength)
	}

	private def drawLine(g: Graphics2D, x0: Int, y0: Int, x1: Int, y1: Int) {
		g.drawLine(
			padding._4 + x0,
			size.height - padding._3 - y0 - 1,
			padding._4 + x1,
			size.height - padding._3 - y1 - 1
		)
	}
}
