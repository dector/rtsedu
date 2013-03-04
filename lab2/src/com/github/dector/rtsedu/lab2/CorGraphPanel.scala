package com.github.dector.rtsedu.lab2

import java.awt.{Color, Graphics2D}

/**
 * @author dector
 */
class CorGraphPanel(val xPage: GraphPanel, val yPage: GraphPanel) extends GraphPanel((_) => 0) {

	override def drawMxAndDValues(g: Graphics2D) {
		g.setColor(Color.BLACK)
		drawString(g, "f(0) = " + values(0), 100, 70)
	}

	override def init() {
		if (! xPage.initted) { xPage.init() }
		if (! yPage.initted) { yPage.init() }

//		val tStep = (xPage.xValuesBounds._2 - xPage.xValuesBounds._1).toFloat / (xPage.tCount + 1)

		for (i <- 0 to tCount) {
			values(i) = 0

			for (j <- 0 to tCount - i - 1) {
				values(i) += (xPage.values(j) - xPage.mx) * (yPage.values(j+i) - yPage.mx)
			}
			values(i) /= (tCount - 1)
		}

		initted = true
	}
}
