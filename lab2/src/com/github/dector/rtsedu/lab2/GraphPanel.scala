package com.github.dector.rtsedu.lab2

import swing.Panel
import java.awt.{Color, Point, Rectangle, Graphics2D}
import javax.swing.text.JTextComponent
import swing.event.{Key, KeyPressed, KeyEvent}

/**
 * @author dector
 */
class GraphPanel(
					val countFunction: (Float) => Float,
					val tCount: Int = 256,
					val xValuesBounds: (Int, Int) = (0, 256),
					var yValuesBounds: (Int, Int) = (-50, 50),
					val valuesPerAxis: (Int, Int) = (20, 10)) extends Panel {

	final val PaddingLeft = 10
	final val PaddingRight = 10
	final val PaddingTop = 10
	final val PaddingBottom = 10

	final val MinX = 0
	final val MaxX = 100
	final val MinY = -10
	final val MaxY = 10
	final val XValuesPerAxis = 20
	final val YValuesPerAxis = 20

	final val TCount = 100;

	final val XDigitsPosition = -1
	final val YDigitsPosition = -1

	final val AxisTaleLength = 15
	final val AxisTaleHeight = AxisTaleLength / 2

	final val DigitPointHalfLength = 3
	final val DigitMargin = 10

	final val AxisColor = Color.BLACK
	final val GraphColor = Color.RED

	val padding = (PaddingTop, PaddingRight, PaddingBottom, PaddingLeft)

//	val xValuesBounds = xValues //(MinX, MaxX)
//	val yValuesBounds = yValues //(MinY, MaxY)
//	val valuesPerAxis = (XValuesPerAxis, YValuesPerAxis)

//	val tCount = length;

	val digitPosition = (XDigitsPosition, YDigitsPosition) // -1/1 -> bottom/top | left/right

	val values: Array[Float] = new Array[Float](tCount + 2)
	var mx = 0f
	var d = 0f
	var initted = false

	listenTo(keys)
	reactions += {
		case key: KeyEvent => if (key.peer == java.awt.event.KeyEvent.VK_F5) {
			init()
			repaint()
			println("test")
		}
	}
	focusable = true
	requestFocus()

	override def paintComponent(g: Graphics2D) {
		g.setColor(AxisColor)

		// Draw axis
		val xAxisLength = size.width - padding._2 - padding._4 - 2*AxisTaleLength
		val yAxisLength = size.height - padding._1 - padding._3 - 2*AxisTaleLength
		val zeroPosition = new Point(
			(-xValuesBounds._1 * xAxisLength.toFloat / (xValuesBounds._2 - xValuesBounds._1) + AxisTaleLength).toInt,
			(-yValuesBounds._1 * yAxisLength.toFloat / (yValuesBounds._2 - yValuesBounds._1) + AxisTaleLength).toInt)
		// Axis X
		drawLine(g, 0, zeroPosition.y, xAxisLength + 2*AxisTaleLength, zeroPosition.y)
		drawLine(g, AxisTaleLength + xAxisLength, (zeroPosition.y + AxisTaleHeight.toFloat / 2).toInt, xAxisLength + 2*AxisTaleLength, zeroPosition.y)
		drawLine(g, AxisTaleLength + xAxisLength, (zeroPosition.y - AxisTaleHeight.toFloat / 2).toInt, xAxisLength + 2*AxisTaleLength, zeroPosition.y)

		val xStepSize = xAxisLength.toFloat / valuesPerAxis._1
		val xStep = (xValuesBounds._2 - xValuesBounds._1).toFloat / valuesPerAxis._1
		for (i <- xValuesBounds._1 until (xValuesBounds._2, xStep.toInt)) {
			if (i != 0) {
				val pointX = ((i - xValuesBounds._1).toFloat / xStep ) * xStepSize + AxisTaleLength
				drawLine(g, pointX.toInt, zeroPosition.y + DigitPointHalfLength, pointX.toInt, zeroPosition.y - DigitPointHalfLength)
				drawString(g, i.toString, pointX.toInt, zeroPosition.y)
			}
		}

		// Axis Y
		drawLine(g, zeroPosition.x, 0, zeroPosition.x, yAxisLength + 2*AxisTaleLength)
		drawLine(g, (zeroPosition.x + AxisTaleHeight.toFloat / 2).toInt, AxisTaleLength + yAxisLength, zeroPosition.x, yAxisLength + 2*AxisTaleLength)
		drawLine(g, (zeroPosition.x - AxisTaleHeight.toFloat / 2).toInt, AxisTaleLength + yAxisLength, zeroPosition.x, yAxisLength + 2*AxisTaleLength)

		val yStepSize = yAxisLength.toFloat / valuesPerAxis._2
		val yStep = (yValuesBounds._2 - yValuesBounds._1).toFloat / valuesPerAxis._2
		for (i <- yValuesBounds._1 until (yValuesBounds._2, yStep.toInt)) {
			if (i != 0) {
				val pointY = ((i - yValuesBounds._1).toFloat / yStep ) * yStepSize + AxisTaleLength
				drawLine(g, zeroPosition.x - DigitPointHalfLength, pointY.toInt, zeroPosition.x + DigitPointHalfLength, pointY.toInt)
				drawString(g, i.toString, zeroPosition.x, pointY.toInt)
			}
		}

		drawString(g, "0", zeroPosition.x/* + DigitMargin * digitPosition._2*/, zeroPosition.y/* + DigitMargin * digitPosition._1*/)

		g.setColor(GraphColor)

		val positiveResScale = yValuesBounds._2.toFloat / (yValuesBounds._2 - yValuesBounds._1)
		val negativeResScale = yValuesBounds._1.toFloat / (yValuesBounds._2 - yValuesBounds._1)
		val positiveResLength = yAxisLength * positiveResScale
		val negativeResLength = yAxisLength * negativeResScale
		val tStepSize = xAxisLength.toFloat / (tCount + 1)
		val tStep = (xValuesBounds._2 - xValuesBounds._1).toFloat / (tCount + 1)

		if (! initted) {
			init()
		}

		val graphX = (t: Float) => { (((t - xValuesBounds._1).toFloat / tStep) * tStepSize + AxisTaleLength).toInt }
		val graphY = (funcRes: Float) => {
			(zeroPosition.y + funcRes.toFloat *	(if (funcRes > 0)
				positiveResLength.toFloat / yValuesBounds._2 else negativeResLength.toFloat / yValuesBounds._1)).toInt
		}

		val prevPoint = new Point(graphX(xValuesBounds._1), graphY(values(xValuesBounds._1)))

		var i = 0
		for (t <- xValuesBounds._1 + tStep until (xValuesBounds._2, tStep)) {
			val x = graphX(t)
			val y = graphY(values(i))
			drawLine(g, prevPoint.x, prevPoint.y, x, y)
			prevPoint.move(x, y)

			i += 1
		}

		drawMxAndDValues(g)
	}

	protected def drawMxAndDValues(g: Graphics2D) {
		g.setColor(Color.black)

		val mxStr = mx.formatted("mx = %.3f")
		drawString(g, mxStr, 100, 70)
		val dStr = d.formatted("D = %.3f")
		drawString(g, dStr, 100, 50)
	}

	protected final def drawLine(g: Graphics2D, x0: Int, y0: Int, x1: Int, y1: Int) {
		g.drawLine(
			padding._4 + x0,
			size.height - padding._3 - y0 - 1,
			padding._4 + x1,
			size.height - padding._3 - y1 - 1
		)
	}

	protected final def drawString(g: Graphics2D, s: String, x: Int, y: Int) {
		g.drawString(s, padding._4 + x, size.height - padding._3 - y - 1)
	}

	private def countMx(values: Array[Float], t0: Int, t1: Int): Float = {
		var mx = values(t0)
		for (i <- t0 + 1 to t1) {
			mx += values(i)
		}
		mx / (t1 - t0)
	}

	private def countD(values: Array[Float], t0: Int, t1: Int, mx: Float): Float = {
		var d = 0f
		for (i <- t0 to t1) {
			d += math.pow(values(i) - mx, 2).toFloat
		}
		d / (t1 - t0)
	}

	def init() {
		val tStep = (xValuesBounds._2 - xValuesBounds._1).toFloat / (tCount + 1)

		var i = 0;
		for (t <- xValuesBounds._1.toFloat until (xValuesBounds._2, tStep)) {
			values(i) = countFunction(t)
			i += 1
		}

		mx = countMx(values, 0, tCount)
		d = countD(values, 0, tCount, mx)

		initted = true
	}
}
