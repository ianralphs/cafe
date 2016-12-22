//////////////////////////////////////////////////////////////////////////////
// File: 		CafeTestSpec.scala
// Date: 		21/12/2106
// Author: 		I Ralphs
//
// Description: Test Harness for Cafe billing system
//
// History
// Date		Author		Change
// 1.0		21/12/16	Initial version
// 1.1		22/12/16	Step 2 - Added support for service charges
//
//////////////////////////////////////////////////////////////////////////////
package com.capgemini.cafe {

import org.scalatest._


	// Define a base class for future test specs to extend
	abstract class UnitSpec extends FlatSpec with Matchers with
	  OptionValues with Inside with Inspectors

	// Class for testing cafe project
	class CafeTestSpec extends UnitSpec {

		// Test Menu pre-population
		"The Menu" should "be pre-populated with items Cola, Coffee, Cheese Sandwich and Steak Sandwich" in {
			assert(Menu.menu(0).desc == "Cola")
			assert(Menu.menu(1).desc == "Coffee")
			assert(Menu.menu(2).desc == "Cheese Sandwich")
			assert(Menu.menu(3).desc == "Steak Sandwich")
		}

		// Test Order creation
		"An order" should "be created with an Order reference number but without items" in {
			var myOrder = new Order(1)
			assert(myOrder.item.isEmpty)
		}

		// Test addition of items to order and calculation of bill
		// Corrected for step 2 service charge
		"An order for Cola, Coffee and Cheese Sandwich" should "cost 3.85" in {
			var myOrder = new Order(100)
			myOrder.addItem("Cola")
			myOrder.addItem("Coffee")
			myOrder.addItem("Cheese sandwich")
			assert(myOrder.calculateBill == 3.85)
		}

		// Test addition of items to order and calculation of bill with no service charge
		"An order for 2 x Cola and Coffee" should "cost 2.00" in {
			var myOrder = new Order(101)
			myOrder.addItem("Cola")
			myOrder.addItem("Cola")
			myOrder.addItem("Coffee")
			assert(myOrder.calculateBill == 2.00)
		}

		// Test addition of items to order and calculation of bill with 10% service charge
		"An order for 2 x Cola, Cheese Sandwich and Coffee" should "cost 4.40" in {
			var myOrder = new Order(102)
			myOrder.addItem("Cola")
			myOrder.addItem("Cola")
			myOrder.addItem("Cheese Sandwich")
			myOrder.addItem("Coffee")
			assert(myOrder.calculateBill == 4.40)
		}

		// Test addition of items to order and calculation of bill with 20% service charge
		"An order for 2 x Cola, Cheese Sandwich, Steak Sandwich and Coffee" should "cost 10.20" in {
			var myOrder = new Order(103)
			myOrder.addItem("Cola")
			myOrder.addItem("Cola")
			myOrder.addItem("Cheese Sandwich")
			myOrder.addItem("Steak Sandwich")
			myOrder.addItem("Coffee")
			assert(myOrder.calculateBill == 10.20)
		}

		// Test addition of items to order and calculation of bill with 20% service charge capped at £20
		"An order for 100 x Steak Sandwich" should "cost 470.00" in {
			var myOrder = new Order(104)
			for (x <- 1 to 100) {
				myOrder.addItem("Steak Sandwich")
			}
			assert(myOrder.calculateBill == 470.00)
		}

	}

}