package linkedin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *  LinkedIn coding challenge: find shortest path from a "seed" word to a
 *  "target" word by "morphing" the word one character at a time, through
 *  valid words found in a dictionary.
 *  <p />
 *  A "brute-force", recursive approach is taken.
 */
public class FindMorphPath {

    private final List<String> dictionary;

    // test cases
    public static void main(String[] args) {
        tryCase("lead", "gold", List.of("lead", "load", "goad", "gold", "lend", "lond", "gond"));
        tryCase("aa", "cc", List.of("aa", "ab", "bb", "bc", "cc"));
    }

    private static void tryCase(
        String seed,
        String target,
        List<String> dictionary
    ) {
        final List<List<String>> result = new ArrayList<>();
        new FindMorphPath(dictionary).find(target, List.of(seed), result);
        result.sort(Comparator.comparingInt(List::size));
        System.out.printf("seed(%s),target(%s),pathCount(%s):\n", seed, target, result.size());
        for (List<String> path : result) {
            System.out.printf("  %s: path(%s)\n", path.size(), path);
        }
    }

    // initialize with given dictionary of valid words
    FindMorphPath(List<String> dictionary) {
        this.dictionary = dictionary;
    }

    // finds path(s) from 'basePath' to 'target'
    void find(String target, List<String> basePath, List<List<String>> out) {
        String seed = basePath.get(basePath.size() - 1);
        for (int i = 0; i < seed.length(); i++) {
            for (String relatedWord : findRelatedWords(seed, i)) {
                if (basePath.contains(relatedWord)) {
                    continue;
                }
                List<String> relatedPath = new ArrayList<>(basePath.size() + 1);
                relatedPath.addAll(basePath);
                relatedPath.add(relatedWord);
                if  (relatedWord.equals(target)) {
                    out.add(relatedPath);
                    // "at least as good" as the next one we'd find in this loop
                    break;
                }
                find(target, relatedPath, out);
            }
        }
    }

    // finds similar words with position 'i' as wildcard
    Iterable<String> findRelatedWords(String word, int i) {
        String regex = word.substring(0, i) + "." + word.substring(i+1);
        Pattern pattern = Pattern.compile(regex);
        Collection<String> result = new LinkedList<>();
        for (final String dictWord : dictionary) {
            if (pattern.matcher(dictWord).matches()) {
                result.add(dictWord);
            }
        }
        return result;
    }

}
