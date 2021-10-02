package linkedin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllPalindromicSubsequences {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> strs = allPalindromicSubSequence("abaca");
		
		for (String st : strs){
			System.out.println(st);
		}
	}
	
	public static List<String> allPalindromicSubSequence(String str){
        List<String> ans = new ArrayList<>();
        int len = str.length();
        int[][] dp = new int[len][len];
        for(int i=0; i<len; i++){
            for(int j=0; j<=i; j++){
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

        for(int i=0; i<len; i++){
            for(int j=0; j<len; j++){
                if(dp[i][j]==1 && i<=j){
                    set.add(str.substring(i, j+1));
                }
            }
        }
        ans.addAll(set);

        return ans;
    }

}
