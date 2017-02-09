package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * LikeMedian demo class 1
 */
public class MedianDemo1 {

    /**
     * Entry point of the application
     *
     * @param args command line arguments -ignored
     */
    public static void main(String[] args) {
        LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();
        likeMedian.add(new Integer(10));
        likeMedian.add(new Integer(5));
        likeMedian.add(new Integer(3));
        Optional<Integer> result = likeMedian.get();

        //modification - as explained in the book
        if (result.isPresent())
            System.out.println(result.get());

        for (Integer elem : likeMedian) {
            System.out.println(elem);
        }
    }
}
