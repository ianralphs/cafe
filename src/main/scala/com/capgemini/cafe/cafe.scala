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
//
//////////////////////////////////////////////////////////////////////////////
package com.capgemini.cafe {

import scala.collection.mutable._
import scala.collection.immutable._


	// Define class for individual menu items
	case class MenuItem (
		val code: Int,			// item code for shorthand order taking
		val desc: String,		// item description
		val cost: Double		// price of the individual item
	)
	
	
	// Build the menu as a list of MenuItems
	// Singleton object - should only be one menu
	// Needs to be read in from somewhere in future release
	object Menu {
		val menu = List(MenuItem(1, "Cola", 0.5), MenuItem(2, "Coffee", 1.0), MenuItem(3, "Cheese Sandwich", 2.0), MenuItem(4, "Steak Sandwich", 4.5))
	}
	
	// Order class to capture orders
	class Order (ordNo: Int)
	{
		val orderNo: Int = ordNo		// The number for this order (later revision needs to generate this)
		val item = ListBuffer[Int]()	// List of items selected on the order
		
		// Method to add items to order through description
		def addItem(newItem: String) : Unit = {
			for (menu_item <- Menu.menu) {
				if (newItem.equalsIgnoreCase(menu_item.desc)) {
					item += menu_item.code
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
			
			for (order_item <- item) {
				for (menu_item <- Menu.menu) {
					if( order_item == menu_item.code) {
						total += menu_item.cost
					}
				}
			}
			
			// Return total rounded to 2 decimal places
			return BigDecimal(total).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
		}
	}
	
} // package