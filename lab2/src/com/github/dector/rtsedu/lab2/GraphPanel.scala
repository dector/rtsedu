package com.github.dector.rtsedu.lab2

import swing.Panel
import java.awt.{Point, Rectangle, Graphics2D}

/**
 * @author dector
 */
class GraphPanel(val countFunction: (Int) => Int) extends Panel {

	final val PaddingLeft = 10
	final val PaddingRight = 10
	final val PaddingTop = 10
	final val PaddingBottom = 10

	final val MinX = 0
	final val MaxX = 100
	final val MinY = -50
	final val MaxY = 50
	final val XValuesPerAxis = 20
	final val YValuesPerAxis = 20

	final val TCount = 100;

	final val XDigitsPosition = -1
	final val YDigitsPosition = -1

	final val AxisTaleLength = 15
	final val AxisTaleHeight = AxisTaleLength / 2

	final val DigitPointHalfLength = 3
	final val DigitMargin = 10

	val padding = (PaddingTop, PaddingRight, PaddingBottom, PaddingLeft)

	val xValuesBounds = (MinX, MaxX)
	val yValuesBounds = (MinY, MaxY)
	val valuesPerAxis = (XValuesPerAxis, YValuesPerAxis)

	val tCount = TCount;

	val digitPosition = (XDigitsPosition, YDigitsPosition) // -1/1 -> bottom/top | left/right

	override def paintComponent(g: Graphics2D) {
		// Draw axis
		val xAxisLength = size.width - padding._2 - padding._4 - 2*AxisTaleLength
		val yAxisLength = size.height - padding._1 - padding._3 - 2*AxisTaleLength
		val zeroPosition = new Point(
			-xValuesBounds._1 * xAxisLength / (xValuesBounds._2 - xValuesBounds._1) + AxisTaleLength,
			-yValuesBounds._1 * yAxisLength / (yValuesBounds._2 - yValuesBounds._1) + AxisTaleLength)
		// Axis X
		drawLine(g, 0, zeroPosition.y, xAxisLength + 2*AxisTaleLength, zeroPosition.y)
		drawLine(g, AxisTaleLength + xAxisLength, zeroPosition.y + AxisTaleHeight / 2, xAxisLength + 2*AxisTaleLength, zeroPosition.y)
		drawLine(g, AxisTaleLength + xAxisLength, zeroPosition.y - AxisTaleHeight / 2, xAxisLength + 2*AxisTaleLength, zeroPosition.y)

		val xStepSize = xAxisLength / valuesPerAxis._1
		val xStep = (xValuesBounds._2 - xValuesBounds._1) / valuesPerAxis._1
		for (i <- xValuesBounds._1 until (xValuesBounds._2, xStep)) {
			if (i != 0) {
				val pointX = ((i - xValuesBounds._1) / xStep ) * xStepSize + AxisTaleLength
				drawLine(g, pointX, zeroPosition.y + DigitPointHalfLength, pointX, zeroPosition.y - DigitPointHalfLength)
				drawString(g, i.toString, pointX, zeroPosition.y)
			}
		}

		// Axis Y
		drawLine(g, zeroPosition.x, 0, zeroPosition.x, yAxisLength + 2*AxisTaleLength)
		drawLine(g, zeroPosition.x + AxisTaleHeight / 2, AxisTaleLength + yAxisLength, zeroPosition.x, yAxisLength + 2*AxisTaleLength)
		drawLine(g, zeroPosition.x - AxisTaleHeight / 2, AxisTaleLength + yAxisLength, zeroPosition.x, yAxisLength + 2*AxisTaleLength)

		val yStepSize = yAxisLength / valuesPerAxis._2
		val yStep = (yValuesBounds._2 - yValuesBounds._1) / valuesPerAxis._2
		for (i <- yValuesBounds._1 until (yValuesBounds._2, yStep)) {
			if (i != 0) {
				val pointY = ((i - yValuesBounds._1) / yStep ) * yStepSize + AxisTaleLength
				drawLine(g, zeroPosition.x - DigitPointHalfLength, pointY, zeroPosition.x + DigitPointHalfLength, pointY)
				drawString(g, i.toString, zeroPosition.x, pointY)
			}
		}

		drawString(g, "0", zeroPosition.x/* + DigitMargin * digitPosition._2*/, zeroPosition.y/* + DigitMargin * digitPosition._1*/)

		val tStep = (xValuesBounds._2 - xValuesBounds._1) / tCount
		for (t <- xValuesBounds._1 until xValuesBounds._2) {
			val funcRes = countFunction(t)
			// TODO Plot it
		}

		// TODO Make graph self-scale by y
	}

	private def drawLine(g: Graphics2D, x0: Int, y0: Int, x1: Int, y1: Int) {
		g.drawLine(
			padding._4 + x0,
			size.height - padding._3 - y0 - 1,
			padding._4 + x1,
			size.height - padding._3 - y1 - 1
		)
	}

	private def drawString(g: Graphics2D, s: String, x: Int, y: Int) {
		g.drawString(s, padding._4 + x, size.height - padding._3 - y - 1)
	}
}
