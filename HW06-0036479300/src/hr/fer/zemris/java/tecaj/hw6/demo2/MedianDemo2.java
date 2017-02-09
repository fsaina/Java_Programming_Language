package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;


/**
 * LikeMedian demo class 2
 */
public class MedianDemo2 {

    /**
     * Entry point of the application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        LikeMedian<String> likeMedian = new LikeMedian<String>();
        likeMedian.add("Joe");
        likeMedian.add("Jane");
        likeMedian.add("Adam");
        likeMedian.add("Zed");
        Optional<String> result = likeMedian.get();
        System.out.println(result); // Writes: Jane
    }
}
