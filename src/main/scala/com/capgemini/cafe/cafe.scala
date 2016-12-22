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
			
			// Loop through all items on this order
			for (order_item <- item) {
				for (menu_item <- Menu.menu) {
				
					// Add up the cost of the goods purchased
					if (order_item == menu_item.code) {
						total += menu_item.cost
						
						// Calculate Service Charge percentage
						if ((menu_item.isFood==true) && (serviceCharge < 10)) {
							serviceCharge = 10
						}
						else if ((menu_item.isHot==true) && (menu_item.isFood==true) && (serviceCharge < 20)) {
							serviceCharge = 20
						}
					}					
				}
			}
			
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
