

/** 
 * 
 * exactly similar to find 'x' to the power 'y'
 * except instead 'x' is now matrix.
 * 
 * | 1, 1 | ^ 1 =  | 1, 1 |
 * | 1, 0 |        | 1, 0 |
 *          
 * | 1, 1 | ^ 2 =  | 2, 1 |  
 * | 1, 0 |        | 1, 1 |       
 *             
 * | 1, 1 | ^ 3 =  | 3, 2 |
 * | 1, 0 |        | 2, 1 |
 *  
 * | 1, 1 | ^ 4 =  | 5, 3 |
 * | 1, 0 |        | 3, 2 |
 *          
 * | 1, 1 | ^ 5 =  | 8, 5 |
 * | 1, 0 |        | 5, 3 |
 *           
 *           
 * 
 * MATRIX TRAVERSALS: consider case 5:
 * -------------------------------------
 *           result         fibo
 * init:     | 1 0 |      | 1 1 |
 *           | 0 1 |      | 1 0 |
 *      
 * 5 % 2:  After multMatrix(result, fiboM):   | 1 1 |                  | 1 1 | ie x^1
 *                                            | 1 0 |                  | 1 0 |
 * 
 *         After multMatrix(fiboM, fiboM):    | 1 1 | ie x ^ 1         | 2 1 |  ie x^2
 *                                            | 1 0 |                  | 1 1 |
 * 
 * 2 % 2:  After multMatrix(fiboM, fiboM):    | 1 1 | ie x^ 1          | 5 3 |  ie x ^ 4
 *                                            | 1 0 |                  | 3 2 |
 *            
 * 1 % 2:  After multMatrix(result, fiboM):   | 8 5 | ie x^1 * x^4     | 5 3 |      
 *                                            | 5 3 |                  | 3 2 |
 *          
 *          
 * The way fibo matrix evovles should generate interest here, 
 * consider the fibo series: 0 1 1 2 3 5 8 
 * 
 * * fibo matrix, it moves in slots / window of 3 fibo elements.
 * | 1 1 | => here, imaging m[1][1] = 0, m[0][1] && m[1][0] = 1 (redudancy), m[0][0] = 1
 * | 1 0 | 
 * 
 * * move to the next element, aka slide the window: 1 1 2
 * | 2 1 | => here, imaging m[1][1] = 1, m[0][1] && m[1][0] = 1 (redudancy), m[0][0] = 2
 * | 1 1 | 
 * 
 * * move to the next element, aka slide the window: 1 2 3 
 * | 3 2 | => here, imaging m[1][1] = 1, m[0][1] && m[1][0] = 2 (redudancy), m[0][0] = 1        
 * | 2 1 |       
 *          
 * * move to the next element, aka slide the window: 2 3 5
 * | 5 3 |
 * | 3 2 |         
 * then:
 * | 8 5 |
 * | 5 3 |          
 * and so on.
 * 
 * Note, to move to the next slot, we need to simply multiply the | 8 5 |  * | 1 1 |
 *                                                                | 5 3 |    | 1 0 |
 *           
 * Complexity:
 * O(log-n)
 * 
 * BB:
 * 17
 * 
 */
public final class FiboAdvanced {
    
    private FiboAdvanced() { }
    
    public static int getNthfibo(int fiboArrayIndex) {
        if (fiboArrayIndex < 0) {
            throw new IllegalArgumentException("The fibo value cannot be negative");
        }

        if (fiboArrayIndex == 0) { 
            return 0;
        }
        
        // By definition, the first two numbers in the Fibonacci sequence are either 1 and 1, or 0 and 1,
        // depending on the chosen starting point of the sequence, and each subsequent number is the sum of the previous two.
        // n = n - 1; //(if you do this, than series will start from 0. ie, first number in sequence is 0. (when n = 1, return 0).
        // without this, first number in the series is 1, ie (when n  = 1, return 1), which is the most common use case.
        
        int[][] result = { {1, 0}, 
                           {0, 1} }; // identity matrix, which is same is 1, in arithmetic.
        
        // fiboM is a special matrix which stores 3 numbers in fibo sequence with 2 redundantly stored on the NE-SW diagonal
        // to move to the next window simply multiply current window with { {1, 1,} {1, 0} }.
        int[][] fiboM = { {1, 1}, 
                          {1, 0} };
        
        while (fiboArrayIndex > 0) {
            if (fiboArrayIndex % 2 == 1) {
                multMatrix(result, fiboM);
            }
           
            fiboArrayIndex = fiboArrayIndex / 2;

            if (fiboArrayIndex > 0) {
                multMatrix(fiboM, fiboM);
            }
        }
        
        return result[1][0];
    }
    
    private static void multMatrix(int[][] m, int [][] n) {
        int a = m[0][0] * n[0][0] +  m[0][1] * n[1][0];
        int b = m[0][0] * n[0][1] +  m[0][1] * n[1][1];
        int c = m[1][0] * n[0][0] +  m[1][1] * n[0][1];
        int d = m[1][0] * n[0][1] +  m[1][1] * n[1][1];
        
        m[0][0] = a;
        m[0][1] = b;
        m[1][0] = c;
        m[1][1] = d;
    }
    
    
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getNthfibo(i));
        }
    }
}
