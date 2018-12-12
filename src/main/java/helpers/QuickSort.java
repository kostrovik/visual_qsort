package helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class QuickSort<T extends Comparable<T>> {
    private ArrayList<T> values;
    private Queue<int[]> animationQueue;
    private boolean sorted;

    public QuickSort(List<T> values, Queue<int[]> animationQueue) {
        this.values = new ArrayList<>(values);
        this.animationQueue = animationQueue;
        this.animationQueue.clear();
        this.sorted = false;
    }

    public Queue<int[]> getAnimationQueue() {
        if (!sorted) {
            quickSort(0, values.size() - 1);
            sorted = true;
        }
        return animationQueue;
    }

    public List<T> sort() {
        if (!sorted) {
            quickSort(0, values.size() - 1);
            sorted = true;
        }
        return values;
    }

    private void quickSort(int left, int right) {
        if (values.size() > 1) {
            if (right - left < 2) {
                if (values.get(left).compareTo(values.get(right)) > 0) {
                    swap(left, right);
                }
            } else {
                sort(left, right);
            }
        }
    }

    private void sort(int left, int right) {
        int l = left;
        int r = right;
        int center = (left + right) / 2;
        while (l < r) {
            while (l <= right && values.get(l).compareTo(values.get(center)) < 0) {
                l++;
            }
            while (left <= r && values.get(r).compareTo(values.get(center)) > 0) {
                r--;
            }
            if (l < r) {
                swap(l, r);
                l++;
                r--;
            }
        }
        if (r > left) {
            quickSort(left, r);
        }
        if (l < right) {
            quickSort(l, right);
        }
    }

    private void swap(int left, int right) {
        Collections.swap(values, left, right);
        animationQueue.add(new int[]{left, right});
    }
}
