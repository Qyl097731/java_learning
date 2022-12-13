package daily;

/**
 * @description
 * @date:2022/12/13 16:04
 * @author: qyl
 */
public class Solution208 {
}

class Trie {
    Trie[] child;
    boolean isEnd;

    public Trie() {
        child = new Trie[26];
        isEnd = false;
    }

    public void insert(String word) {
        Trie node = this;
        for (int i = 0; i < word.length ( ); i++) {
            int c = word.charAt (i) - 'a';
            if (node.child[c] == null) {
                Trie trie = new Trie ( );
                node.child[c] = trie;
            }
            node = node.child[c];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        Trie node = searchPrefix (word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        Trie node = searchPrefix (prefix);
        return node != null;
    }

    public Trie searchPrefix(String word) {
        Trie node = this;
        for (int i = 0; i < word.length ( ); i++) {
            int c = word.charAt (i) - 'a';
            if (node.child[c] == null) {
                return null;
            }
            node = node.child[c];
        }
        return node;
    }
}
