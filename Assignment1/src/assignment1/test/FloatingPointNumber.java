package assignment1.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import assignment1.Gleitpunktzahl;

/**
 * Test class for floating point numbers.
 * 
 * @author tzwickl
 */
public class FloatingPointNumber {
	
	/**
	 * The random generator.
	 */
	private final static Random r = new Random();
	
	/**
	 * Epsilon range value.
	 */
	private static double epsilon = 0.001;
	
	@Before
	public void TestSetup() {
		Gleitpunktzahl.setSizeMantisse(20);
		Gleitpunktzahl.setSizeExponent(20);
	}

	@Test
	public void testSetDouble() {

		Gleitpunktzahl x;
		Gleitpunktzahl gleitref = new Gleitpunktzahl();

		System.out.println("Test setDouble");
		try {
			// Test: setDouble
			double testValue = getRandomDouble(1, 100);
			x = new Gleitpunktzahl(testValue);

			gleitref = new Gleitpunktzahl(testValue);

			assertFalse(x.compareAbsTo(gleitref) != 0 || x.vorzeichen != gleitref.vorzeichen);
			assertTrue(x.toDouble() == gleitref.toDouble());
			assertTrue(isEqualEpsilon(x.toDouble(), testValue, epsilon));
			printRes("" + x.toDouble(), "" + gleitref.toDouble());
			printRes("" + x.toDouble(), "" + testValue);

		} catch (Exception ex) {
			System.out.print("Exception during evaluation!!\n");
			ex.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAddition() {
		
		System.out.println("Test addition");
		
		try {
			double testValue1 = getRandomDouble(1, 100);
			double testValue2 = getRandomDouble(101, 200);
			
			validateOperation(testValue1, testValue2, Operation.ADD);
			validateOperation(-testValue1, testValue2, Operation.ADD);
			validateOperation(testValue1, -testValue2, Operation.ADD);
			validateOperation(-testValue1, -testValue2, Operation.ADD);
			
			validateOperation(testValue2, testValue1, Operation.ADD);
			validateOperation(-testValue2, testValue1, Operation.ADD);
			validateOperation(testValue2, -testValue1, Operation.ADD);
			validateOperation(-testValue2, -testValue1, Operation.ADD);
		} catch (Exception ex) {
			System.out.print("Exception during evaluation!!\n");
			ex.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSubtraction() {
		
		System.out.println("Test subtraction");

		try {
			double testValue1 = getRandomDouble(1, 100);
			double testValue2 = getRandomDouble(101, 200);
			
			validateOperation(testValue1, testValue2, Operation.SUB);
			validateOperation(-testValue1, testValue2, Operation.SUB);
			validateOperation(testValue1, -testValue2, Operation.SUB);
			validateOperation(-testValue1, -testValue2, Operation.SUB);
			
			validateOperation(testValue2, testValue1, Operation.SUB);
			validateOperation(-testValue2, testValue1, Operation.SUB);
			validateOperation(testValue2, -testValue1, Operation.SUB);
			validateOperation(-testValue2, -testValue1, Operation.SUB);
		}  catch (Exception ex) {
			System.out.print("Exception during evaluation!!\n");
			ex.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testBorderValues() {
		
		System.out.println("Test of the border values");

		try {
			
			double x, y, z, a;
			
			x = 0.0;
			y = 1.0 / 0.0;
			z = -1.0 / 0.0;
			a = getRandomDouble(1, 100);
			
			validateOperation(x, a, Operation.ADD);
			validateOperation(a, x, Operation.ADD);
			validateOperation(x, y, Operation.ADD);
			validateOperation(x, z, Operation.ADD);
			validateOperation(y, x, Operation.ADD);
			validateOperation(z, x, Operation.ADD);
			validateOperation(y, z, Operation.ADD);
			
			validateOperation(x, a, Operation.SUB);
			validateOperation(a, x, Operation.SUB);
			validateOperation(x, y, Operation.SUB);
			validateOperation(x, z, Operation.SUB);
			validateOperation(y, x, Operation.SUB);
			validateOperation(z, x, Operation.SUB);
			validateOperation(y, z, Operation.SUB);
			validateOperation(z, y, Operation.SUB);
			
			validateOperation(a, a, Operation.SUB);
			
			
		} catch (Exception ex) {
			System.out.print("Exception during evaluation!!\n");
			ex.printStackTrace();
			fail();
		}
		
	}
	
	/**
	 * Validates the given operation.
	 * 
	 * @param val1
	 * 		The first value.
	 * @param val2
	 * 		The second value.
	 * @param operation
	 */
	private static void validateOperation(double val1, double val2, Operation operation) {
		Gleitpunktzahl x, y;
		Gleitpunktzahl gleitref = new Gleitpunktzahl();
		Gleitpunktzahl gleiterg = new Gleitpunktzahl();
		
		x = new Gleitpunktzahl(val1);
		y = new Gleitpunktzahl(val2);

		// Calculation
		if (operation.equals(Operation.ADD)) {
			System.out.printf("      %.4f + %.4f\n", val1, val2);
			gleiterg = x.add(y);
			gleitref = new Gleitpunktzahl((val1 + val2));
		} else if (operation.equals(Operation.SUB)) {
			System.out.printf("      %.4f - %.4f\n", val1, val2);
			gleiterg = x.sub(y);
			gleitref = new Gleitpunktzahl((val1 - val2));
		} else {
			fail();
		}
		
		printRes("" + gleiterg.toDouble(), "" + gleitref.toDouble());
		assertTrue(isEqualEpsilon(gleiterg.toDouble(), gleitref.toDouble(), epsilon));
	}
	
	@Test
	public void testRounding() {
		Gleitpunktzahl x;

		/* Test von setDouble */
		System.out.println("Test of rounding");
		try {
			// Test: setDouble
			x = new Gleitpunktzahl();
			
			x.exponent = 524287;
			x.mantisse = (int) Math.pow(2, Gleitpunktzahl.getSizeMantisse()) + 1;
			x.normalisiere();
			
			printRes(x.toString(), "");
			
			assertTrue((x.mantisse & 1) == 1);
			
			x.exponent = 524287;
			x.mantisse = ~0;
			
			x.normalisiere();
			
			int result = (int) Math.pow(2, Gleitpunktzahl.getSizeMantisse() - 1);
			
			printRes(x.toString(), "");
			
			assertTrue(x.mantisse == result);

		} catch (Exception ex) {
			System.out.print("Exception during evaluation!!\n");
			ex.printStackTrace();
			fail();
		}
	}

	/**
	 * Prints the results on the console.
	 * 
	 * @param res
	 * 		The result to print
	 * @param checkref
	 * 		The reference to check.
	 */
	private static void printRes(String res, String checkref) {
		System.out.println("      The result is:           " + res
				+ "\n      The correct result is:   " + checkref + "\n");
	}
	
	/**
	 * Generates a random double value between min range and max range.
	 * 
	 * @param rangeMin
	 * 			The min range.
	 * @param rangeMax
	 * 			The max range.
	 * @return the random double variable.
	 */
	private static double getRandomDouble(double rangeMin, double rangeMax) {
		return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
	}

	/**
	 * Tests if the two double values are within the range of epsilon.
	 * 
	 * @param val1
	 * 			The first value to compare.
	 * @param val2
	 * 			The second value to compare.
	 * @param epsilon
	 * 			The valid range.
	 * @return true if they are within the range.
	 */
	private static boolean isEqualEpsilon(double val1, double val2, double epsilon) {
		if (Double.isInfinite(val1) && Double.isInfinite(val2)) {
			return true;
		}
		if (Double.isNaN(val1) && Double.isNaN(val2)) {
			return true;
		}
		if (Math.abs(val1 - val2) <= epsilon) {
			return true;
		}
		return false;
	}
	
	/**
	 * The defined operations.
	 * @author tzwickl
	 */
	private enum Operation {
		/**
		 * Addition operation.
		 */
		ADD,
		/**
		 * Subtraction operation.
		 */
		SUB;
	}
}
