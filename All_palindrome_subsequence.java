package linkedin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PalindromeSubSequence {

	public static void main(String[] args) {
		String s = "abac";
		Set<String> res = new HashSet<>();
		dfs(s, 0, res, new ArrayList<>());
		System.out.println(res);
		
		System.out.println(countPalindromicSubsequences(s));
	}

	static void dfs(String s, int cur, Set<String> res, List<Character> tmp) {
		if (isPalindrome(tmp)) {
			StringBuilder sb = new StringBuilder();
			for (char c : tmp)
				sb.append(c);
			res.add(sb.toString());
		}
		for (int i = cur; i < s.length(); i++) {
			char c = s.charAt(i);
			tmp.add(c);
			dfs(s, i + 1, res, tmp);
			tmp.remove(tmp.size() - 1);
		}
	}

	private static boolean isPalindrome(List<Character> tmp) {
		if (tmp.size() == 0)
			return false;
		int l = 0, r = tmp.size() - 1;
		while (l < r) {
			if (tmp.get(l) != tmp.get(r))
				return false;
			l++;
			r--;
		}
		return true;
	}
 
	
	//solution two 
	// time is O(n^2)
	// space is o(1)
	public static Set<String> countPalindromicSubsequences(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		int len = s.length();

		Set[][] dp = new HashSet[len][len];
		for (int i = 0; i < len; i++) {
			Set<String> set = new HashSet<>();
			set.add(String.valueOf(s.charAt(i)));
			dp[i][i] = set;
		}

		for (int i = len - 1; i >= 0; i--) {
			for (int j = i + 1; j < len; j++) {
				if (s.charAt(i) == s.charAt(j)) {
					Set<String> curSet = new HashSet<>();

					String curString = String.valueOf(s.charAt(i));
					curSet.add(curString);
					curSet.add(curString + curString);

					Set<String> set = dp[i + 1][j - 1];
					if (set != null) {
						curSet.addAll(set);
						for (String ele : set) {
							curSet.add(curString + ele + curString);
						}
					}
					dp[i][j] = curSet;
				} else {
					Set<String> set1 = dp[i + 1][j];
					Set<String> set2 = dp[i][j - 1];
					Set<String> curSet = new HashSet<>();
					curSet.addAll(set1);
					curSet.addAll(set2);
					dp[i][j] = curSet;
				}
			}
		}
		return dp[0][len - 1];
	}

}
