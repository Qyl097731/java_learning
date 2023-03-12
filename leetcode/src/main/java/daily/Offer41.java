package daily;

import java.util.PriorityQueue;

/**
 * @author asus
 */
public class Offer41 {

}

class MedianFinder {

    PriorityQueue<Integer> smallerQueue, laggerQueue;
    int mid = -1;
    boolean exist = false;

    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
        laggerQueue = new PriorityQueue<> ();
        smallerQueue = new PriorityQueue<> ((i, j) -> j - i);
    }

    public void addNum(int num) {
        if (!exist) {
            mid = num;
            exist = true;
        } else {
            if (num > mid) {
                laggerQueue.offer (num);
                if (laggerQueue.size () - smallerQueue.size () >= 2) {
                    smallerQueue.offer (mid);
                    mid = laggerQueue.poll ();
                }
            } else {
                smallerQueue.offer (num);
                if (smallerQueue.size () - laggerQueue.size () >= 2) {
                    laggerQueue.offer (mid);
                    mid = smallerQueue.poll ();
                }
            }
        }
    }

    public double findMedian() {
        if (smallerQueue.size () == laggerQueue.size ()) {
            return mid;
        } else {
            if (smallerQueue.size () > laggerQueue.size ()) {
                return (mid + smallerQueue.peek ()) / 2.0;
            } else {
                return (mid + laggerQueue.peek ()) / 2.0;
            }
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
