Given number of cuts, find the maximum number of possible pieces.
Examples: 
 

Input  : 2
Output : 4

Input  : 3
Output : 7
  
  // Function for finding maximum pieces
    // with n cuts.
    static int findMaximumPieces(int n)
    {
        return 1 + n * (n + 1) / 2;
    }
     
    // Driver Program to test above function
    public static void main(String arg[])
    {
         
        System.out.print(findMaximumPieces(3));
    }
}


Let f(n) denote the maximum number of pieces
that can be obtained by making n cuts.
Trivially,
f(0) = 1                                 
As there'd be only 1 piece without any cut.

Similarly,
f(1) = 2

Proceeding in similar fashion we can deduce 
the recursive nature of the function.
The function can be represented recursively as :
f(n) = n + f(n-1)

Hence a simple solution based on the above 
formula can run in O(n). 
  
  We now know ,
f(n) = n + f(n-1) 

Expanding f(n-1) and so on we have ,
// 上底加下底乘以高，处以二
f(n) = n + n-1 + n-2 + ...... + 1 + f(0)

which gives,
f(n) = (n*(n+1))/2 + 1
