package linkedin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllPalindromicSubstr {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> strs = allPalindromicSubSequence("abaca");

		for (String st : strs) {
			System.out.println(st);
		}
		
		findPalindromicSubstrings("abaca");
	}


 // time is O(n^2), space is O(1)
    // Function to find all unique palindromic substrings of a given string
    public static void findPalindromicSubstrings(String str)
    {
        // base case
        if (str == null) {
            return;
        }
 
        // create an empty set to store all unique palindromic substrings
        Set<String> set = new HashSet<>();
 
        for (int i = 0; i < str.length(); i++)
        {
            // find all odd length palindrome with `str[i]` as a midpoint
            expand(str, i, i, set);
 
            // find all even length palindrome with `str[i]` and `str[i+1]`
            // as its midpoints
            expand(str, i, i + 1, set);
        }
 
        // print all unique palindromic substrings
        System.out.print(set);
    }
    
	 // Expand in both directions of `low` and `high` to find all palindromes
    public static void expand(String str, int low, int high, Set<String> set)
    {
        // run till `str[low.high]` is not a palindrome
        while (low >= 0 && high < str.length()
                && str.charAt(low) == str.charAt(high))
        {
            // push all palindromes into a set
            set.add(str.substring(low, high + 1));
 
            // Expand in both directions
            low--;
            high++;
        }
    }
 
    
    
	// solution two 
	
	public static List<String> allPalindromicSubSequence(String str) {
		List<String> ans = new ArrayList<>();
		int len = str.length();
		int[][] dp = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j <= i; j++) {
				dp[i][j] = 1;
			}
		}

		for (int i = 1; i < len; i++) {
			for (int j = 0; j < len; j++) {
				int left = j;
				int right = j + i;
				if (right < len && str.charAt(left) == str.charAt(right)) {
					dp[left][right] = dp[left + 1][right - 1];
				}
			}
		}
		Set<String> set = new HashSet<>();

		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if (dp[i][j] == 1 && i <= j) {
					set.add(str.substring(i, j + 1));
				}
			}
		}
		ans.addAll(set);

		return ans;
	}

}
