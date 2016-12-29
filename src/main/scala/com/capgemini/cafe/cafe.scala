//////////////////////////////////////////////////////////////////////////////
// File: 		cafe.scala
// Date: 		21/12/2106
// Author: 		I Ralphs
//
// Description: Cafe billing system
//
// History
// Date		Author		Change
// 1.0		21/12/16	Initial version
// 1.1		22/12/16	Step 2 - Added support for service charges
// 1.2		29/12/16	Non functional changes to add filters to Menu and
//						replace for loops in calculateBill with functional stmts
//
//////////////////////////////////////////////////////////////////////////////
package com.capgemini.cafe {

import scala.collection.mutable._
import scala.collection.immutable._


	// Define class for individual menu items
	case class MenuItem (
		val code: Int,			// item code for shorthand order taking
		val desc: String,		// item description
		val cost: Double,		// price of the individual item
		val isHot: Boolean,		// whether the item is Hot
		val isFood: Boolean		// whether the item is food
	)
	
	
	// Build the menu as a list of MenuItems
	// Singleton object - should only be one menu
	// Needs to be read in from somewhere in future release
	object Menu {
		val menu = List(MenuItem(1, "Cola", 0.5, false, false), 
						MenuItem(2, "Coffee", 1.0, true, false), 
						MenuItem(3, "Cheese Sandwich", 2.0, false, true), 
						MenuItem(4, "Steak Sandwich", 4.5, true, true))
						
		def costOfItem(code: Int): Double = menu.filter(x => x.code == code).head.cost
		def isItemFood(code: Int): Boolean = menu.filter(x => x.code == code).head.isFood
		def isItemHot(code: Int): Boolean = menu.filter(x => x.code == code).head.isHot
		def isItemHotFood(code: Int): Boolean = menu.filter(x => x.code == code).head.isHot && isItemFood(code)
	}
	
	// Order class to capture orders
	class Order (ordNo: Int)
	{
		val orderNo: Int = ordNo		// The number for this order (later revision needs to generate this)
		val item = ListBuffer[Int]()	// List of items selected on the order
		
		// Method to add items to order through description
		def addItem(newItems: List[String]) : Unit = {
			for (newItem <- newItems) {
				for (menu_item <- Menu.menu) {
					if (newItem.equalsIgnoreCase(menu_item.desc)) {
						item += menu_item.code
					}
				}
			}
		}

		// Method to add items to order through menu item code		
		def addItem(newItem: Int) : Unit = {
			item += newItem
		}

		// Method to calculate the bill against current state of order
		// Order items List can be changed
		def calculateBill() : Double = {	
			var total: Double = 0.0		// Local variable to hold total of order
			var serviceCharge: Int = 0	// Local variable to hold Service Charge percentage
			
			// Calculate the cost of the items on this order
			total = item.map(Menu.costOfItem(_)).sum
			
			// Calculate Service Charge
			// If any item is food the set Service charge to 10%
			if (item.map(Menu.isItemFood(_)).contains(true)) serviceCharge = 10

			// If any item is hot food override Service charge to 20%
			if (item.map(Menu.isItemHotFood(_)).contains(true)) serviceCharge = 20
			
			// Add service charge to the total for this order subject to maximum of £20
			if (total*serviceCharge/100 > 20) {
				total += 20
			}
			else {
				total += (total*serviceCharge/100)
			}
			
			// Return total rounded to 2 decimal places
			return BigDecimal(total).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
		}
	}
	
} // package
