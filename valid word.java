Given a dictionary and a character array, print all valid words that are possible using characters from the array.

Examples:

Input : Dict - {"go","bat","me","eat","goal", 
                                "boy", "run"} 
        arr[] = {'e','o','b', 'a','m','g', 'l'} 
Output : go, me, goal. 
  //复杂度为 O(m)，m 为最长的字符串的长度
  // spaec is 0(26)
  //You can use either a boolean array or an integer arr for the hash 
  //(if you can use each character once and the given char arr can have duplicate.)
    
 public class PossibleValidWords {

    class TrieNode {
        TrieNode[] children;
        boolean isLeaf;
        String word;

        public TrieNode() {
            this.children = new TrieNode[26];
            this.isLeaf = false;
            this.word = null;
        }
    }

    public List<String> solution(char[] letters, String[] dict) {
        TrieNode root = new TrieNode();
        for (String d : dict)
            insertWord(root, d);
        List<String> res = new ArrayList<>();
        boolean[] hash = new boolean[26];
        for (char l : letters) {
            hash[l - 'a'] = true;
        }
        for (int i = 0; i < 26; i++) {
            if (hash[i] && root.children[i] != null)
                helper(res, hash, root.children[i]);
        }
        return res;
    }

    private void insertWord(TrieNode root, String w) {
        TrieNode cur = root;
        for (char c : w.toCharArray()) {
            int idx = c - 'a';
            if (cur.children[idx] == null)
                cur.children[idx] = new TrieNode();
            cur = cur.children[idx];
        }
        cur.isLeaf = true;
        cur.word = w;
    }

    private void helper(List<String> res, boolean[] hash, TrieNode node) {
        if (node.isLeaf) {
            res.add(node.word);
        }
        for (int i = 0; i < 26; i++) {
            if (hash[i] && node.children[i] != null) {
                helper(res, hash, node.children[i]);
            }
        }
    }
}
