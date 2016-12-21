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
		"An order for Cola, Coffee and Cheese Sandwich" should "cost 3.50" in {
			var myOrder = new Order(100)
			myOrder.addItem("Cola")
			myOrder.addItem("Coffee")
			myOrder.addItem("Cheese sandwich")
			assert(myOrder.calculateBill == 3.50)
		}

	}

}